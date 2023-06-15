package edu.agh.jabeda.server.adapters.out.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import edu.agh.jabeda.server.application.port.out.ImageStoragePort;
import edu.agh.jabeda.server.domain.ReportedProblemId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@AllArgsConstructor
@Service
public class FileStoreAdapter implements ImageStoragePort {
    private final AmazonS3 amazonS3;
    private static final String S3_FOLDER = "images/";

    @Override
    public String uploadImage(byte[] imageBytes, ReportedProblemId reportedProblemId) {
        final var key = S3_FOLDER + reportedProblemId.id();
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                BucketName.IMAGE_BUCKET.getBucketName(),
                key, new ByteArrayInputStream(imageBytes),
                null).withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(putObjectRequest);
        return amazonS3.getUrl(BucketName.IMAGE_BUCKET.getBucketName(), key).toString();
    }
}