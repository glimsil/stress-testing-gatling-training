package br.com.training.user.repository;

import br.com.training.user.model.User;
import br.com.training.user.model.UserPetLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface UserPetLinkRepository extends JpaRepository<UserPetLink, Serializable> {
    public List<UserPetLink> findByUserId(Integer userId);
}
