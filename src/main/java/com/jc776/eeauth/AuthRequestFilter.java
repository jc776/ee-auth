package com.jc776.eeauth;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.picketlink.Identity;


/**
 * Intercept responses from Google/etc to continue processing authentication.
 * @author Anil Saldhana
 */
@ApplicationScoped
@WebFilter("/*")
public class AuthRequestFilter implements Filter{
	private final Logger log = Logger.getLogger(getClass());
    @Inject
    private Instance<Identity> identityInstance;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	log.info("init!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            Identity identity = identityInstance.get();
            log.info(Boolean.toString(identity.isLoggedIn()));
            if(identity.isLoggedIn() == false){
                ThreadLocalUtils.currentRequest.set((HttpServletRequest) request);
                ThreadLocalUtils.currentResponse.set((HttpServletResponse) response);

                identity.login();
            }
            chain.doFilter(request,response);
        }finally{
            ThreadLocalUtils.currentRequest.set(null);
            ThreadLocalUtils.currentResponse.set(null);
        }
    }

    @Override
    public void destroy() {
    }
}
