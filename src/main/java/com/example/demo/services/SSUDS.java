package com.example.demo.services;
import com.example.demo.model.AppUser;
import com.example.demo.model.AppUserRepository;
import com.example.demo.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUDS implements UserDetailsService {
    private AppUserRepository userRepo;

    public SSUDS(AppUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            AppUser user = userRepo.findByUsername(username);
            if (user == null)
                throw new UsernameNotFoundException("Invalid username or password");
            else {
                System.out.println("picked " + user.getUsername() + "from the database");
                return new User(user.getUsername(), user.getPassword(), getAuthorities(user));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    private Set<GrantedAuthority> getAuthorities(AppUser user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserRole role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
            System.out.println("User authority: " + role.getRole());
        }

        return authorities;
    }
}
