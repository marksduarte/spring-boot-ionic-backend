package io.github.marksduarte.api.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
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

	@Value("${s3.bucket}")
	private String bucketName;

	@Bean
	public AmazonS3 s3client() {
		AmazonS3Client client;
		try {
			AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
			ClientConfiguration configuration =
					new ClientConfiguration().withSocketTimeout(longSocketTimeout)
						.withSignerOverride("S3SignerType");

			client = new AmazonS3Client(credentials, configuration);
			client.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
			client.setEndpoint(s3Endpoint);
			//client.setBucketPolicy(bucketName, getPublicReadPolicy());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Política inválida");
		}
		return client;
	}

	// Sets a public read policy on the bucket.
	// Only if you're using aws service.
	private String getPublicReadPolicy() {
		Policy bucketPolicy = new Policy().withStatements(
				new Statement(Statement.Effect.Allow)
						.withPrincipals(Principal.AllUsers)
						.withActions(S3Actions.GetObject)
						.withResources(new Resource("arn:aws:s3:::" + bucketName + "/*")));

		return bucketPolicy.toJson();
	}

}
