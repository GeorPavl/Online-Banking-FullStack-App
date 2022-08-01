package com._config._security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureHandlerCustom extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String error = exception.getMessage();
        System.out.println("A failed login attempt with username: " + username + ". Reason: " + error);
        super.setDefaultFailureUrl("/login?error=" + error);
        super.onAuthenticationFailure(request, response, exception);
    }
}
