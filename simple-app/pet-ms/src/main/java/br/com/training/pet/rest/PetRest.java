package br.com.training.pet.rest;

import br.com.training.pet.model.Pet;
import br.com.training.pet.rest.dto.PetDTO;
import br.com.training.pet.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
public class PetRest {
	private static final Logger log = LoggerFactory.getLogger(PetRest.class);

	@Autowired
	PetService petService;

	@GetMapping
	public ResponseEntity<Page<Pet>> get(Pageable pageable, @RequestParam(value = "search", required = false) String searchString) {
		log.info("Getting all pets");
		return ResponseEntity.ok(petService.find(pageable, searchString));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pet> get(@PathVariable("id") Integer id) {
		log.info("Getting pets " + id);
		return ResponseEntity.ok(petService.find(id));
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody PetDTO dto) {
		log.info("Creating new Pet" + dto.getName() + " " + dto.getAnimal());
		return ResponseEntity.ok(petService.create(dto.getName(), dto.getAnimal(), dto.getRace()));
	}

}
