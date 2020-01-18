package br.com.tests.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.tests.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{

    User findByLogin(String login);
}
