package com.xws.users.util.security.auth;

import com.xws.users.util.security.TokenUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// A filter that will intercept every client request to the server
// Except for the paths specified in WebSecurityConfig.configure (WebSecurity web)
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;

    private UserDetailsService userDetailsService;

    public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
        this.tokenUtils = tokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String username;
        String authToken = tokenUtils.getToken(request);

        if (authToken != null) {
            // take email from token
            username = tokenUtils.getUsernameFromToken(authToken);

            if (username != null) {
                // get the user based on the email
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // check if the forwarded token is valid
                if (tokenUtils.validateToken(authToken, userDetails)) {
                    // create authentication
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // forward the request further to the next filter
        chain.doFilter(request, response);
    }

}