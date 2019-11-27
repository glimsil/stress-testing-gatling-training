package br.com.training.pet.repository;

import br.com.training.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface PetRepository extends JpaRepository<Pet, Serializable> {

}
