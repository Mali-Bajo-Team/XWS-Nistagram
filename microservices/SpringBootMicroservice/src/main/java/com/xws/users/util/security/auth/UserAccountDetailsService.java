package com.xws.users.util.security.auth;

import com.xws.users.repository.IUserRepository;
import com.xws.users.users.model.roles.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAccountDetailsService implements UserDetailsService {
	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount user = userRepository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", username));
		} else {
			return user;
		}
	}

}
