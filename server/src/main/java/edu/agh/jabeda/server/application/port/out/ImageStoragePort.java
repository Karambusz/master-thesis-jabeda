package edu.agh.jabeda.server.application.port.out;

import edu.agh.jabeda.server.domain.ReportedProblemId;

public interface ImageStoragePort {

    String uploadImage(byte[] imageBytes, ReportedProblemId reportedProblemId);
}
