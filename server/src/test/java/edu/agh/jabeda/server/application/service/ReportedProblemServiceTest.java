package edu.agh.jabeda.server.application.service;


import com.google.maps.model.LatLng;
import edu.agh.jabeda.server.adapters.out.persistence.entity.ReportedProblemEntity;
import edu.agh.jabeda.server.adapters.out.persistence.entity.UserDeviceEntity;
import edu.agh.jabeda.server.application.port.in.model.request.ReportProblemRequest;
import edu.agh.jabeda.server.application.port.out.ImageStoragePort;
import edu.agh.jabeda.server.application.port.out.ReportedProblemPort;
import edu.agh.jabeda.server.application.service.mapper.ReportedProblemMapper;
import edu.agh.jabeda.server.domain.ReportedProblem;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import edu.agh.jabeda.server.domain.UserDevice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportedProblemServiceTest {

    @Mock
    private ReportedProblemPort reportedProblemPort;

    @Mock
    private ImageStoragePort imageStoragePort;

    @Mock
    private GeocodingHelper geocodingHelper;

    @Mock
    private ReportedProblemMapper reportedProblemMapper;

    @InjectMocks
    private ReportedProblemService reportedProblemService;

    @Test
    public void testReportProblem_WithImage() {
        ReportProblemRequest request = new ReportProblemRequest();
        request.setAddress("Test Address");
        request.setImageBase64("iVBORw0KGgoAAAANSUhEUgAAABgAAAA");

        LatLng latLng = new LatLng(42.12345, -73.98765);
        when(geocodingHelper.getLocation(request.getAddress())).thenReturn(latLng);

        ReportedProblemId reportedProblemId = new ReportedProblemId(123);
        when(reportedProblemPort.reportProblem(eq(request), any(), any())).thenReturn(reportedProblemId);

        String imageUrl = "http://example.com/image.jpg";
        when(imageStoragePort.uploadImage(any(), eq(reportedProblemId))).thenReturn(imageUrl);

        ReportedProblemId result = reportedProblemService.reportProblem(request);

        assertEquals(reportedProblemId, result);
        verify(reportedProblemPort).updateProblemWithImageUrl(eq(imageUrl), eq(reportedProblemId));
    }

    @Test
    public void testReportProblem_WithoutImage() {
        ReportProblemRequest request = new ReportProblemRequest();
        request.setAddress("Test Address");

        LatLng latLng = new LatLng(42.12345, -73.98765);
        when(geocodingHelper.getLocation(request.getAddress())).thenReturn(latLng);

        ReportedProblemId reportedProblemId = new ReportedProblemId(123);
        when(reportedProblemPort.reportProblem(eq(request), any(), any())).thenReturn(reportedProblemId);

        ReportedProblemId result = reportedProblemService.reportProblem(request);

        assertEquals(reportedProblemId, result);
        verify(reportedProblemPort, never()).updateProblemWithImageUrl(any(), any());
    }

    @Test
    public void testGetNewReportedProblemsByCategories_WithCategories() {
        List<String> categories = List.of("Category1", "Category2");
        Integer subscriberId = 1;

        final var userDeviceEntity = new UserDeviceEntity();
        userDeviceEntity.setDeviceId("device123");
        userDeviceEntity.setLastTimeReported(LocalDateTime.now());

        final var reportedProblemEntity =  new ReportedProblemEntity();
        reportedProblemEntity.setUserDevice(userDeviceEntity);

        List<ReportedProblemEntity> pendingProblems = List.of(
                reportedProblemEntity,
                reportedProblemEntity
        );

        when(reportedProblemPort.getNewReportedProblemsByCategories(categories, subscriberId))
                .thenReturn(pendingProblems);


        final var userDevice = new UserDevice();
        userDevice.setDeviceId("device123");
        userDevice.setLastTimeReported(LocalDateTime.now());

        final var reportedProblem =  new ReportedProblem();
        reportedProblem.setUserDevice(userDevice);
        List<ReportedProblem> mappedProblems = List.of(
                reportedProblem,reportedProblem
        );

        when(reportedProblemMapper.toReportedProblems(pendingProblems)).thenReturn(mappedProblems);

        Collection<ReportedProblem> result = reportedProblemService.getNewReportedProblemsByCategories(categories, subscriberId);

        assertEquals(mappedProblems, result);

        verify(reportedProblemPort, times(mappedProblems.size()))
                .getRejectedProblemsCount(anyString());
    }

    @Test
    public void testGetNewReportedProblemsByCategories_EmptyCategories() {
        List<String> categories = Collections.emptyList();
        Integer subscriberId = 1;

        Collection<ReportedProblem> result = reportedProblemService.getNewReportedProblemsByCategories(categories, subscriberId);

        assertTrue(result.isEmpty());
        verify(reportedProblemPort, never()).getNewReportedProblemsByCategories(any(), anyInt());
    }
}
