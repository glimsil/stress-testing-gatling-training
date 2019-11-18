package br.com.training.user.repository;

import br.com.training.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;

public interface UserRepository extends MongoRepository<User, Serializable>{

}
