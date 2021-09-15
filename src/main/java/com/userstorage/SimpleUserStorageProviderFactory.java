package com.userstorage;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class SimpleUserStorageProviderFactory implements UserStorageProviderFactory<SimpleUserStorageProvider> {
	
	private final String PROVIDER_NAME= "simple-user-provider";
	
	@Override
	public SimpleUserStorageProvider create(KeycloakSession session, ComponentModel model) {	
	    return new SimpleUserStorageProvider(session, model);
	}
	
	@Override
	public String getId() {
	    return PROVIDER_NAME;
	}
}