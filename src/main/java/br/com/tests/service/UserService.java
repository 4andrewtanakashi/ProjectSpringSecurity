package br.com.tests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.tests.model.User;
import br.com.tests.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository usrRepository;
    
    public boolean saveUser (User usr) {
        if (!usrRepository.existsById(usr.getLogin())) {
            usr.setPassword(new BCryptPasswordEncoder().encode("0000"));
            usr.setAutho("0000");
            usrRepository.save(usr);
            return true;
        }
        return false;
    }
    
    
    public User findUser (String login) {
        return usrRepository.findByLogin(login);
    }
}
