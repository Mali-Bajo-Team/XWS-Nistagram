package com.xws.users.service.impl;

import com.xws.users.dto.UserTokenState;
import com.xws.users.repository.IUserRepository;
import com.xws.users.service.IUserService;
import com.xws.users.users.model.Authority;
import com.xws.users.users.model.roles.UserAccount;

import com.xws.users.util.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {


    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    public UserAccount findOne(Long id) { return userRepository.findById(id).orElseGet(null); }

    @Override
    public UserAccount findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserAccount> findAll() {
        return userRepository.findAll();
    }

    @Override
    public String getUserRole(UserAccount user) {
        List<Authority> auth = (List<Authority>) user.getAuthorities();
        Authority userAuthority = auth.get(0); // take only first one or make some logic to choose needed one
        String userRole = userAuthority.getAuthority(); // we have now for example "ROLE_ADMIN"
        userRole = userRole.substring(5, userRole.length()); // to take "ADMIN" only, we substring "ROLE_"
        return userRole;
    }



    @Override
    public UserAccount changePassword(String oldPassword, String newPassword) {
        return null;
    }

    public UserAccount save(UserAccount user) { return userRepository.save(user); }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserTokenState logIn(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));

        // Insert the user into the current security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create a token for that user
        UserAccount user = (UserAccount) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        return new UserTokenState(jwt, expiresIn);
    }

}
