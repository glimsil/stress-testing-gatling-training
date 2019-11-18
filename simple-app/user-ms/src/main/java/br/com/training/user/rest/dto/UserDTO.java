package br.com.training.user.rest.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter @Setter
public class UserDTO {
    String firstName;
    String lastName;
    String email;
    String gender;
}
