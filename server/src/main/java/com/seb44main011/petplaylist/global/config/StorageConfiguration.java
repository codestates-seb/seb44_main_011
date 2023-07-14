package com.seb44main011.petplaylist.global.config;

import com.seb44main011.petplaylist.domain.member.service.FileSystemStorageService;
import com.seb44main011.petplaylist.domain.member.service.S3StorageService;
import com.seb44main011.petplaylist.domain.member.service.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfiguration {
    private static final String REGION = "ap-northeast-2";

    @Bean
    public StorageService fileSystemStorageService() {
        return new FileSystemStorageService();
    }

    @Primary
    @Bean
    public StorageService s3StorageService() {
        S3Client s3Client =
                S3Client.builder()
                        .region(Region.of(REGION))
                        .credentialsProvider(DefaultCredentialsProvider.create())
                        .build();
        return new S3StorageService(s3Client);
    }
}
