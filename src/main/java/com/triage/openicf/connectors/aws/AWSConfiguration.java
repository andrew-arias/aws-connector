/*
 * DO NOT REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 ForgeRock AS. All rights reserved.
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://opensource.org/licenses/CDDL-1.0
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at http://opensource.org/licenses/CDDL-1.0
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 */
package com.triage.openicf.connectors.aws;

import org.identityconnectors.common.StringUtil;
import org.identityconnectors.framework.spi.AbstractConfiguration;
import org.identityconnectors.framework.spi.ConfigurationProperty;

/**
 * Extends the {@link AbstractConfiguration} class to provide all the necessary
 * parameters to initialize the AWS Connector.
 *
 */
public class AWSConfiguration extends AbstractConfiguration {

	// Exposed configuration properties.
	private String clientID;
	private String clientSecret;
	private String region;

	/**
	 * Constructor.
	 */
	public AWSConfiguration() {
	}
	
	@ConfigurationProperty(order = 1, displayMessageKey = "clientID.display", groupMessageKey = "basic.group", helpMessageKey = "clientID.help", required = true, confidential = false)
	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
	@ConfigurationProperty(order = 2, displayMessageKey = "clientSecret.display", groupMessageKey = "basic.group", helpMessageKey = "clientSecret.help", required = true, confidential = true)
	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	@ConfigurationProperty(order = 3, displayMessageKey = "awsRegion.display", groupMessageKey = "basic.group", helpMessageKey = "awsRegion.help", required = true, confidential = false)
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate() {
		if (StringUtil.isBlank(clientID)) {
			throw new IllegalArgumentException("Client ID cannot be null or empty.");
		}

		if (StringUtil.isBlank(clientSecret)) {
			throw new IllegalArgumentException("Client Secret cannot be null or empty.");
		}
		
		if (StringUtil.isBlank(region)) {
			throw new IllegalArgumentException("AWS Region cannot be null or empty.");
		}
	}

}
