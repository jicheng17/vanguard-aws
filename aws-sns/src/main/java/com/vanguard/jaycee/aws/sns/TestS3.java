package com.vanguard.jaycee.aws.sns;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

public class TestS3 {
    private static S3Client s3;

    public static void main(String [] args)throws IOException {
        Region region = Region.AP_SOUTHEAST_1;

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "AKIAYIUADBXLJ7UKYMOM",
                "plQRHudqCA6fsEdUY7WlyJ1Uwqw4YFArGNGW57");

        s3 = S3Client.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();


        String bucket = "bucket" + System.currentTimeMillis();
        String key = "key";

        createBucket(s3,bucket, region);

// Put Object
        s3.putObject(PutObjectRequest.builder().bucket(bucket).key(key)
                        .build(),
                RequestBody.fromByteBuffer(getRandomByteBuffer(10_000)));
    }

    public static void createBucket( S3Client s3Client, String bucketName, Region region) {

        try{
            s3Client.createBucket(CreateBucketRequest
                    .builder()
                    .bucket(bucketName)
                    .createBucketConfiguration(
                            CreateBucketConfiguration.builder()
                                    .locationConstraint(region.id())
                                    .build())
                    .build());

            System.out.println(bucketName);
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    private static ByteBuffer getRandomByteBuffer(int size) throws IOException {
        byte[] b = new byte[size];
        new Random().nextBytes(b);
        return ByteBuffer.wrap(b);
    }
}
