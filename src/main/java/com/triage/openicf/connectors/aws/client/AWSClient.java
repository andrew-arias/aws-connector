package com.triage.openicf.connectors.aws.client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.triage.openicf.connectors.aws.AWSConfiguration;

public class AWSClient {
	private static AmazonIdentityManagement aws;
	private static AWSConfiguration config;

	public AWSClient(final AWSConfiguration config) {
		this.config = config;
	}

	private void login() {
		BasicAWSCredentials cred = new BasicAWSCredentials(config.getClientID(), config.getClientSecret());
		aws = AmazonIdentityManagementClientBuilder.standard().withRegion(config.getRegion())
				.withCredentials(new AWSStaticCredentialsProvider(cred)).build();
	}

	public void testConnection() {
		login();
	}

	public static AmazonIdentityManagement getAWS() {
		return aws;
	}
	
}
