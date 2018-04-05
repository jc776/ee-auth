package com.jc776.eeauth;

import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.model.basic.User;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * A simple PicketLink authenticator that will accept a hard coded username/password value. The
 * @PicketLink annotation is required to indicate to PicketLink that this is the default Authenticator
 * to be used.
 * 
 * @author Shane Bryzak
 *
 */
@PicketLink
public class Authenticator extends BaseAuthenticator {

    @Inject 
    private DefaultLoginCredentials credentials;

    @Inject
    private FacesContext facesContext;

    @Override
    public void authenticate() {
        if ("jsmith".equals(credentials.getUserId()) &&
                "abc123".equals(credentials.getPassword())) {
            setStatus(AuthenticationStatus.SUCCESS);
            setAccount(new User("jsmith"));
        } else {
            setStatus(AuthenticationStatus.FAILURE);
            facesContext.addMessage(null, new FacesMessage(
                    "Authentication Failure - The username or password you provided were invalid."));
        }
    }



}
