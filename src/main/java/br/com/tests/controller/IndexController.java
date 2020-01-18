package br.com.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.tests.model.User;

import br.com.tests.service.UserService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "localhost:4200"} )
public class IndexController {

    @Autowired
    private UserService usrService;
    
//    @Autowired
//    AuthenticationManager authenticationManager;

    
    @GetMapping("/")
    public String index () {
        System.out.print("AQUISDKNALKDSN");
        return "test/index.html";
    }
    
    @PostMapping("/{uhuhu}/test")
    public String test (@PathVariable String uhuhu) {
        System.out.print(uhuhu);
        return uhuhu;
    }
    
    @GetMapping("/test2")
    public boolean test2 () {
        System.out.println("AQUI !!!! Test 2");
        return true;
    }
    
    @PostMapping("/register")
    public User registerSaveUser (@RequestBody User usr) {
        usrService.saveUser(usr);
        System.out.print(usrService.findUser(usr.getLogin()));
        return usrService.findUser(usr.getLogin());
    }
    

}
