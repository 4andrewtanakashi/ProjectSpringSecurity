package br.com.tests.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tests.service.domain.UserCredentialsPOJO;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    
    
    public LoginFilter(String url, AuthenticationManager authManager) {
        
        super(new AntPathRequestMatcher(url));  
        setAuthenticationManager(authManager);
        System.out.println("PASSOU Auth0: ");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        
        UserCredentialsPOJO userCredentials = new ObjectMapper()  
                .readValue(request.getInputStream(), UserCredentialsPOJO.class);
        
        System.out.println("Auth1: " + getAuthenticationManager().authenticate(  
                new UsernamePasswordAuthenticationToken(  
                        userCredentials.getLogin(),  
                        userCredentials.getPassword(),  
                        Collections.emptyList()  
                )
        ) + "\n\n");
        
        return getAuthenticationManager().authenticate(  
                new UsernamePasswordAuthenticationToken(  
                        userCredentials.getLogin(),  
                        userCredentials.getPassword(),  
                        Collections.emptyList()  
                )
        );
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        System.out.println("PASSOU Auth2: ");
        AuthenticationService.addJWTToken(response, authResult.getName()); 
    }
    

}
