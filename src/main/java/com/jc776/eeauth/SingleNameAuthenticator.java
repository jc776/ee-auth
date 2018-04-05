package com.jc776.eeauth;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.model.basic.User;
import org.picketlink.internal.DefaultIdentity;

public class SingleNameAuthenticator extends BaseAuthenticator {
	private final Logger log = Logger.getLogger(getClass());

    @Inject 
    private DefaultLoginCredentials credentials;

    @Inject
    private FacesContext facesContext;
    
    // this is what we do, is it correct?
    @Inject
    private DefaultIdentity identity;

    @Override
    public void authenticate() {
    	if(credentials == null) {
    		log.info("no credentials yet");
    	} else {
    		log.info("credentials");
    		if ("jsmith".equals(credentials.getUserId()) &&
                    "abc123".equals(credentials.getPassword())) {
            	((CustomIdentity)this.identity).postLoginSetup();
                setStatus(AuthenticationStatus.SUCCESS);
                setAccount(new User("jsmith"));
            } else {
                setStatus(AuthenticationStatus.FAILURE);
                facesContext.addMessage(null, new FacesMessage(
                        "Authentication Failure - The username or password you provided were invalid."));
            }
    	}
        
    }



}
