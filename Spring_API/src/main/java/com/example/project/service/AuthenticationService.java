package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import com.example.project.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.example.project.entity.User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Email or Password wrong"));

//		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
//		user.getRoles().forEach(r -> {
//			SimpleGrantedAuthority au = new SimpleGrantedAuthority(r.getShortName());
//			authorities.add(au);
//		});

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		return new User(user.getEmail(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, user.getGrantedAuthorities());
	}

}
