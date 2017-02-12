package com.triage.openicf.connectors.aws.client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.triage.openicf.connectors.aws.AWSConfiguration;

public class AWSClient {

	private AWSConfiguration config;

	public AWSClient(final AWSConfiguration config) {
		this.config = config;
	}
	
	public void testConnection() {
		BasicAWSCredentials cred = new BasicAWSCredentials(config.getClientID(), config.getClientSecret());
		AmazonIdentityManagement aws = AmazonIdentityManagementClientBuilder.standard().withRegion(config.getRegion()).withCredentials(new AWSStaticCredentialsProvider(cred)).build();
	}
}
