package com.vanguard.jaycee.aws.sns;

import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

public class TestSNS {
    public static void main(String[] args) {
        String message = "sk que shao le Jc";
        String phoneNumber = "+6593880617";

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "AKIAYIUADBXLJ7UKYMOM",
                "plQRHudqCA6fsEdUY7WlyJ1Uwqw4YFArGNGW57");

        SnsClient snsClient = SnsClient.builder()
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        pubTextSMS(snsClient, message, phoneNumber);
    }

    public static void pubTextSMS(SnsClient snsClient, String message, String phoneNumber) {
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .phoneNumber(phoneNumber)
                    .build();

            PublishResponse result = snsClient.publish(request);

            System.out.println(result.messageId() + " Message sent. Status was " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {

            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
