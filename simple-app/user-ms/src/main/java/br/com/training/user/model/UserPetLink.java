package br.com.training.user.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_pet_link")
public class UserPetLink implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	Integer id;
	@Column(name = "userId")
	Integer userId;
	@Column(name = "petId")
	Integer petId;
}
