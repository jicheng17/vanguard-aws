package com.vanguard.jaycee.aws.s3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest
public class S3ServiceTester {
    @Autowired
    S3Service s3Service;

    @Value("${aws.services.bucket}")
    private String bucketName;


    @Test
    public void contextLoads() {
        MultipartFile testFile = new MockMultipartFile("fileThatDoesNotExists.txt",
                "fileThatDoesNotExists.txt",
                "text/plain",
                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));

        if ( s3Service != null )
            s3Service.uploadFile("test.txt", testFile, bucketName, true);
    }
}
