package com.triage.openicf.connectors.aws.operations;

import org.identityconnectors.framework.common.objects.Uid;

import com.amazonaws.services.identitymanagement.model.AddUserToGroupRequest;
import com.amazonaws.services.identitymanagement.model.AttachUserPolicyRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.DeleteUserRequest;
import com.triage.openicf.connectors.aws.AWSConnector;

public class AWSUserOps {

	private AWSConnector connector = null;

	public AWSUserOps(AWSConnector connector) {
		this.connector = connector;
	}

	public Uid createUser(String userName, String policy) {
		Uid uid = null;
		try {
			new CreateUserRequest(userName);
			new AddUserToGroupRequest("admin", userName);
			new AttachUserPolicyRequest().withUserName(userName).setPolicyArn(policy);
			return uid;
		} catch (Exception e) {
			e.printStackTrace();
			return uid;
		}
	}

	public void deleteUser(String userName) {
		try {
			DeleteUserRequest request = new DeleteUserRequest(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkForUser() {
		return true;
	}

	public void upgradeUser() {

	}
}
