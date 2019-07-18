package com.marksduarte.cursomc.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

	@Value("${s3.region}")
	private String region;
	
	@Value("${aws.access-key-id}")
	private String accessKey;
	
	@Value("${aws.secret-access-key}")
	private String secretKey;

	@Value("${aws.long-socket-timeout}")
	private Integer longSocketTimeout;

	@Value("${aws.s3-endpoint}")
	private String s3Endpoint;

	@Bean
	public AmazonS3 s3client() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		ClientConfiguration configuration = new ClientConfiguration().withSocketTimeout(longSocketTimeout);
		configuration = configuration.withSignerOverride("S3SignerType");
		AmazonS3Client client = new AmazonS3Client(credentials, configuration);

		client.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
		client.setEndpoint(s3Endpoint);

		return client;
	}
}
