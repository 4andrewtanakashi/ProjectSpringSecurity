package br.com.tests.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import br.com.tests.model.User;
import br.com.tests.repository.UserRepository;

@Repository
public class ImplementsUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository usrRepo;
    
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        
        User usr = usrRepo.findByLogin(login);
        
        if (usr == null)
            throw new UsernameNotFoundException("User not found !");

        return usr;
    }

}
