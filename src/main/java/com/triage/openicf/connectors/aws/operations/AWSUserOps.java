package com.triage.openicf.connectors.aws.operations;

import java.util.List;

import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.ConnectorObjectBuilder;
import org.identityconnectors.framework.common.objects.OperationOptions;
import org.identityconnectors.framework.common.objects.ResultsHandler;
import org.identityconnectors.framework.common.objects.Uid;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AddUserToGroupRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserRequest;
import com.amazonaws.services.identitymanagement.model.CreateUserResult;
import com.amazonaws.services.identitymanagement.model.DeleteUserRequest;
import com.amazonaws.services.identitymanagement.model.GetUserRequest;
import com.amazonaws.services.identitymanagement.model.GetUserResult;
import com.amazonaws.services.identitymanagement.model.ListUsersRequest;
import com.amazonaws.services.identitymanagement.model.RemoveUserFromGroupRequest;
import com.amazonaws.services.identitymanagement.model.RemoveUserFromGroupResult;
import com.amazonaws.services.identitymanagement.model.UpdateUserRequest;
import com.amazonaws.services.identitymanagement.model.UpdateUserResult;
import com.amazonaws.services.identitymanagement.model.User;
import com.triage.openicf.connectors.aws.AWSConnector;
import com.triage.openicf.connectors.aws.client.AWSClient;

public class AWSUserOps {
	private static final Log LOGGER = Log.getLog(AWSUserOps.class);

	@SuppressWarnings("unused")
	private AWSConnector connector = null;

	public AWSUserOps(AWSConnector connector) {
		this.connector = connector;
	}

	public Uid createUser(String userName, String policy) {
		Uid uid = null;
		CreateUserResult result = null;
		AmazonIdentityManagement aws = AWSClient.getAWS();
		try {
			LOGGER.info("Attempting to create user " + userName + " in AWS.");
			result = aws.createUser(new CreateUserRequest(userName));
			aws.addUserToGroup(new AddUserToGroupRequest("Admins", userName));
			uid = new Uid(result.getUser().getUserName());
			LOGGER.info("User " + userName + "created in AWS.");
			return uid;
		} catch (Exception e) {
			e.printStackTrace();
			if (result != null) {
				DeleteUserRequest request = new DeleteUserRequest(userName);
				aws.deleteUser(request);
			}
			return uid;
		}
	}

	public void deleteUser(String userName) {
		AmazonIdentityManagement aws = AWSClient.getAWS();
		try {
			RemoveUserFromGroupRequest groupRequest = new RemoveUserFromGroupRequest("Admins", userName);
			aws.removeUserFromGroup(groupRequest);
			DeleteUserRequest request = new DeleteUserRequest(userName);
			aws.deleteUser(request);
			LOGGER.info("User " + userName + "deleted from AWS.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void queryUser(String query, ResultsHandler resultsHandler, OperationOptions options) {
		AmazonIdentityManagement aws = AWSClient.getAWS();
		if (query == null) {
			try {
				LOGGER.info("Querying all users in AWS");
				ListUsersRequest request = new ListUsersRequest();
				List<User> users = aws.listUsers(request).getUsers();
				for (User user : users) {
					ConnectorObject connectorObj = createConnectorObject(user);
					if (connectorObj != null) {
						resultsHandler.handle(connectorObj);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				LOGGER.info("Querying for " + query + " in AWS");
				GetUserRequest request = new GetUserRequest().withUserName(query);
				GetUserResult user = aws.getUser(request);
				ConnectorObject connectorObj = createConnectorObject(user.getUser());
				if (connectorObj != null) {
					resultsHandler.handle(connectorObj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void upgradeUser(String userName) {
		AmazonIdentityManagement aws = AWSClient.getAWS();
		try {
			LOGGER.info("Upgrading " + userName + "'s account in AWS");
			UpdateUserRequest request = new UpdateUserRequest(userName);
			UpdateUserResult result = aws.updateUser(request);
			if(result != null)
				LOGGER.info(userName + "'s account successfully upgraded in AWS");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ConnectorObject createConnectorObject(User user) {
		ConnectorObject obj = null;
		if (user == null) {
			return null;
		}
		try {
			ConnectorObjectBuilder cob = new ConnectorObjectBuilder();
			Uid uid = new Uid(user.getUserName());
			String userPrincipalName = user.getUserName();
			cob.setUid(uid);
			cob.setName(userPrincipalName);
			obj = cob.build();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return obj;
	}
}
