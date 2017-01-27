package com.triage.openicf.connectorsaws.client;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.model.CreateAccessKeyRequest;
import com.triage.openicf.connectors.aws.AWSConfiguration;

public class AWSClient {

	private AWSConfiguration config;

	public AWSClient(final AWSConfiguration config) {
		this.config = config;
	}

	public void testConnection() {
		CreateAccessKeyRequest key = new CreateAccessKeyRequest();
		BasicAWSCredentials cred = new BasicAWSCredentials(config.getKey(), config.getSecret());
		AWSCredentialsProviderChain provider = new AWSCredentialsProviderChain(cred);
		key.withUserName(user.getUserName());
		key.setRequestCredentialsProvider(credentialsProvider);	
	}
}
