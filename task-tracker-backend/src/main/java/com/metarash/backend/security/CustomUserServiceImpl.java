package com.metarash.backend.security;

import com.metarash.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
