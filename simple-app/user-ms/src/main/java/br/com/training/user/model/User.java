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
@Entity(name = "test_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	Integer id;
	@Column(name="firstName")
	String firstName;
	@Column(name="lastName")
	String lastName;
	@Column(name="email")
	String email;
	@Column(name="gender")
	String gender;
	@Column(name="ipAddress")
	String ipAddress;
}
