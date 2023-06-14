package edu.agh.jabeda.server.adapters.out.aws;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    IMAGE_BUCKET("jabeda-image-bucket");
    private final String bucketName;
}