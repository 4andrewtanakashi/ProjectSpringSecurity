package br.com.tests.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

    @Id
    private String nameRole;
    
    @ManyToMany
    private List<User> users;
    
    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }



    @Override
    public String getAuthority() {
        
        return this.nameRole;
    }

}
