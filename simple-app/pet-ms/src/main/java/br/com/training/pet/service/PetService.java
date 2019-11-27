package br.com.training.pet.service;

import br.com.training.pet.model.Pet;
import br.com.training.pet.repository.PetRepository;
import br.com.training.pet.utils.PetSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PetService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PetRepository petRepository;

	private PetSort petSort = new PetSort();
	
	public Page<Pet> find(Pageable pageable) {
		return find(pageable, null);
	}

	public Page<Pet> find(Pageable pageable, String searchString) {
		log.info("finding pet search = " + searchString );
		if(null != searchString) {
			List<Pet> pets = new ArrayList<>();
			for (Pet u : petRepository.findAll()) {
				if(u.getName() != null && u.getName().toLowerCase().contains(searchString.toLowerCase())) {
					if(!pets.contains(u))
						pets.add(u);
				}
				if(u.getAnimal() != null && u.getAnimal().toLowerCase().contains(searchString.toLowerCase())) {
					if(!pets.contains(u))
						pets.add(u);
				}
				if(u.getRace() != null && u.getRace().toLowerCase().contains(searchString.toLowerCase())) {
					if(!pets.contains(u))
						pets.add(u);
				}
			}
			petSort.sort(pets);
			return createPage(pets, pageable);
		} else {
			log.info("null search string");
			return petRepository.findAll(pageable);
		}
	}

	private Page<Pet> createPage(List<Pet> pets, Pageable pageable) {
		if(pageable.getPageNumber() * pageable.getPageSize() >= pets.size())
			return  new PageImpl(Arrays.asList(), pageable, pets.size());
		return new PageImpl(pets.subList(pageable.getPageNumber() * pageable.getPageSize(),
				(pageable.getPageNumber() * pageable.getPageSize()) + pageable.getPageSize() >= pets.size()
						? pets.size() - 1 : (pageable.getPageNumber() *
						pageable.getPageSize()) + pageable.getPageSize()), pageable, pets.size());
	}
	public Pet find(Integer id) {
		return petRepository.findOne(id);
	}
	
	public Pet create(String name, String animal, String race) {
		Pet pet = Pet.builder()
				.name(name)
				.animal(animal)
				.race(race)
				.build();
		return petRepository.save(pet);
	}
	
	public Pet update(Pet pet) {
		return petRepository.save(pet);
	}
	
	public void delete(String id) {
		petRepository.delete(id);
	}

}
