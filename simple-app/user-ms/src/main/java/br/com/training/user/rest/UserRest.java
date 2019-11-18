package br.com.training.user.rest;

import br.com.training.user.exception.UserAlreadyExistsException;
import br.com.training.user.model.User;
import br.com.training.user.rest.dto.ErrorDTO;
import br.com.training.user.rest.dto.UserDTO;
import br.com.training.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRest {
	private static final Logger log = LoggerFactory.getLogger(UserRest.class);

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<Page<User>> get(Pageable pageable, @RequestParam(value = "search", required = false) String searchString) {
		log.info("Getting all users");
		return ResponseEntity.ok(userService.find(pageable, searchString));
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable("id") String id) {
		log.info("Getting users " + id);
		return ResponseEntity.ok(userService.find(id));
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody UserDTO dto) {
		log.info("Creating new User" + dto.getFirstName() + " " + dto.getLastName());
		try {
			return ResponseEntity.ok(userService.create(dto.getFirstName(), dto.getLastName(), dto.getEmail(),
					dto.getGender()));
		} catch (UserAlreadyExistsException e) {
			return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
		}
	}

}
