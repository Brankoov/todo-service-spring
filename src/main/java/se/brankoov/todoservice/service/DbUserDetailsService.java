package se.brankoov.todoservice.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import se.brankoov.todoservice.repository.UserRepository;

@Service
public class DbUserDetailsService implements UserDetailsService {
    private final UserRepository repo;

    public DbUserDetailsService(UserRepository repo) { this.repo = repo; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return User.withUsername(u.getUsername())
                .password(u.getPassword()) // redan BCRYPT-hashad
                .roles(u.getRoles().toArray(String[]::new))
                .build();
    }
}
