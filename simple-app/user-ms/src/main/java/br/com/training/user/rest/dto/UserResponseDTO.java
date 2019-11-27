package br.com.training.user.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserResponseDTO {
    Integer id;
    String firstName;
    String lastName;
    String email;
    String gender;
    List<PetDTO> pets;
}
