package br.com.training.pet.model;

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
@Entity(name = "pet")
public class Pet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	Integer id;
	@Column(name="name")
	String name;
	@Column(name="animal")
	String animal;
	@Column(name="race")
	String race;
}
