package com.userstorage;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.userstorage.vo.SimpleCustomUser;

public class SimpleUserStorageProvider implements UserStorageProvider, UserLookupProvider, UserQueryProvider, CredentialInputValidator {

    private KeycloakSession session;
    private ComponentModel model;
    private SimpleCustomUserRepository repository = new SimpleCustomUserRepository();

    public SimpleUserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) { 
    	if (credentialType.equals("password")) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
    	return false; 
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credInput) {
    	if (!(credInput instanceof UserCredentialModel)) {
    	      return false;
    	}
    	
    	String userId = StorageId.externalId(user.getId());
        
        return repository.validateCredentials(userId, credInput.getChallengeResponse());
    }

    @Override
    public void close() {
    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
    	String userId = StorageId.externalId(id);
        return new SimpleUserAdapter(session, realm, model, repository.findUserById(userId));
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
    	SimpleCustomUser user = repository.findUserByUserName(username);
        if (user != null) {
            return new SimpleUserAdapter(session, realm, model, user);
        }
        else {
        	return null;
        }
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
    	SimpleCustomUser user = repository.findUserByEmailAddr(email);
        if (user != null) {
            return new SimpleUserAdapter(session, realm, model, user);
        }
        else {
        	return null;
        }
    }

    @Override
    public int getUsersCount(RealmModel realm) {
    	return repository.getUsersCount();
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm) {    	
    	    	
    	List<UserModel> userModels = new ArrayList<>();    	
    	
    		repository.getAllUsers().stream().forEach(user -> {    			
    			
    			userModels.add(new SimpleUserAdapter(session, realm, model, user));
    		
    			//System.out.println("!gerUsers cnt --> " + userModels.size());    		
    		
    		});
    	
    	return userModels;
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
    	return getUsers(realm);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm) {
        return repository.findUsers(search).stream()
                .map(user -> new SimpleUserAdapter(session, realm, model, user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
    	return getUsers(realm);
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
    	return getUsers(realm);
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult, int maxResults) {
    	return getUsers(realm);
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
        return Collections.emptyList();
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
        return Collections.emptyList();
    }

    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
        return Collections.emptyList();
    }
}
