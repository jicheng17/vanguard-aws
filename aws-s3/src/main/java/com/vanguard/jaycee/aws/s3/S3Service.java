package com.vanguard.jaycee.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class S3Service {
    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${aws.services.bucket}")
    private String bucketName;

    public void uploadFile(final String fileName, final MultipartFile file,
                           final String bucketName, final boolean allowPublicAccess) {

        final ObjectMetadata data = new ObjectMetadata();
        data.setContentType(file.getContentType());
        data.setContentLength(file.getSize());

        try {
            final PutObjectRequest putObjectRequest;
            if(allowPublicAccess){
                putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(), data)
                        .withCannedAcl(CannedAccessControlList.PublicRead);
            }else {
                putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(), data);
            }

            amazonS3Client.putObject(putObjectRequest);
        } catch (IOException e) {

        }
    }

}
