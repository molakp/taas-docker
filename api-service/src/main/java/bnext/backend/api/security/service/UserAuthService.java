package bnext.backend.api.security.service;


import bnext.backend.api.user.User;
import bnext.backend.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<String> roles = List.of(user.getRoles().split(","));
        for (String temp : roles)
            grantedAuthorities.add(new SimpleGrantedAuthority(temp));

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
    }

}
