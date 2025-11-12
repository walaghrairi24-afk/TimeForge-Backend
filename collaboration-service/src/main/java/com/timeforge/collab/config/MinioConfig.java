package com.timeforge.collab.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(
            @Value("${collab.attachments.s3.endpoint}") String endpoint,
            @Value("${collab.attachments.s3.access-key}") String accessKey,
            @Value("${collab.attachments.s3.secret-key}") String secretKey) {

        return MinioClient.builder()
                .endpoint(endpoint) // ex: http://localhost:9000
                .credentials(accessKey, secretKey)
                .build();
    }
}
