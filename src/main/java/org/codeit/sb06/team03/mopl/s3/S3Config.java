package org.codeit.sb06.team03.mopl.s3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client(S3Properties properties) {
        final Region region = Region.of(properties.region());
        final String accessKey = properties.accessKey();
        final String secretKey = properties.secretKey();

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner(S3Properties properties) {
        final Region region = Region.of(properties.region());
        final String accessKey = properties.accessKey();
        final String secretKey = properties.secretKey();

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Presigner.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
