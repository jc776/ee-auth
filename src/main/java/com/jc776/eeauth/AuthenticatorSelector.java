package com.jc776.eeauth;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.Authenticator;
import org.picketlink.social.auth.GoogleAuthenticator;
import org.picketlink.social.auth.conf.GoogleConfiguration;



@Named
public class AuthenticatorSelector {
	private final Logger log = Logger.getLogger(getClass());
	private GoogleConfiguration googleConfig = new GoogleConfiguration() {

		@Override
		public String getClientID() {
			return "870751557548-j1ueat9q2ds8mim5ojj5d4383sfkm4bp.apps.googleusercontent.com";
		}

		@Override
		public String getClientSecret() {
			return "brRDO8gJywOjNEnc2X7qlU1o";
		}

		@Override
		public String getReturnURL() {
			return "http://localhost:8080/auth/google/return";
		}

		@Override
		public String getScope() {
			return null; // default
		}

		@Override
		public String getAccessType() {
			return null; // default?
		}

		@Override
		public String getApplicationName() {
			return "sso test app";
		}

		@Override
		public String getRandomAlgorithm() {
			return null; // default
		}
		
	};
	
	@PicketLink
	@Produces
	@RequestScoped
	public Authenticator chooseAuthenticator() {
		log.info("chooseAuthenticator()");
		HttpServletRequest httpServletRequest = (HttpServletRequest) ThreadLocalUtils.currentRequest.get();
        HttpServletResponse httpServletResponse = (HttpServletResponse) ThreadLocalUtils.currentResponse.get();

        String login = httpServletRequest.getParameter("login");
        Authenticator authenticator = null;

        if(login == null) {
        	log.info("single");
        	authenticator = new SingleNameAuthenticator();
        } else if(login.equals("google")){
        	log.info("google");
            GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
            googleAuthenticator.setHttpServletRequest(httpServletRequest);
            googleAuthenticator.setHttpServletResponse(httpServletResponse);
            googleAuthenticator.setConfiguration(googleConfig);
            authenticator = googleAuthenticator;
        }
        return authenticator;
	}
}
