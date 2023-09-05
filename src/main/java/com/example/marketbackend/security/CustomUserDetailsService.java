package com.example.marketbackend.security;

import com.example.marketbackend.entity.User;
import com.example.marketbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(userId)).orElse(null);

        if(user == null)
            throw new UsernameNotFoundException("User " + userId + "not found");

        CustomUserDetails userDetails = new CustomUserDetails(user);

        return userDetails;
    }
}
