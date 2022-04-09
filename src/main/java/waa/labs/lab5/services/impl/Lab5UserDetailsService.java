package waa.labs.lab5.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import waa.labs.lab5.repositories.IUserRepo;

import javax.transaction.Transactional;

@Service
@Transactional
public class Lab5UserDetailsService implements UserDetailsService {
    IUserRepo userRepo;

    public Lab5UserDetailsService(IUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepo.findByEmail(email);

        return new Lab5UserDetails(user);
    }
}
