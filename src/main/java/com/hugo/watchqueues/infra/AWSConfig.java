package com.hugo.watchqueues.infra;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    @Bean("sqsClient")
    public AmazonSQS amazonSQS() {
        final DefaultAWSCredentialsProviderChain credentials = new DefaultAWSCredentialsProviderChain();
        return AmazonSQSClientBuilder
                .standard().withRegion(Regions.US_EAST_1)
                .withCredentials(credentials).build();
    }

}
