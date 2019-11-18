package br.com.training.user.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "User")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	String id;
	String firstName;
	String lastName;
	String email;
	String gender;
	String ipAddress;
}
