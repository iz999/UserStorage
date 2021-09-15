package com.userstorage;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import com.userstorage.vo.SimpleCustomUser;

public class SimpleUserAdapter extends AbstractUserAdapterFederatedStorage {
	
    private final SimpleCustomUser user;
    
    public SimpleUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, SimpleCustomUser user) {
        super(session, realm, model);
        this.storageId = new StorageId(storageProviderModel.getId(), user.getUserId());
        this.user = user;
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmailAddr());
        this.setEnabled(true);        
                
        this.setSingleAttribute("USER_INFO_MAP", user.getUserDetailedInfo());
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public void setUsername(String username) {
    	//none
    }    
}