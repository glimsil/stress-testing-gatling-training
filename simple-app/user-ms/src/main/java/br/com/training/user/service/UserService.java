package br.com.training.user.service;

import br.com.training.user.exception.UserAlreadyExistsException;
import br.com.training.user.exception.UserDoesNotExistsException;
import br.com.training.user.model.User;
import br.com.training.user.model.UserPetLink;
import br.com.training.user.repository.UserPetLinkRepository;
import br.com.training.user.repository.UserRepository;
import br.com.training.user.rest.dto.PetDTO;
import br.com.training.user.rest.dto.UserResponseDTO;
import br.com.training.user.utils.UserSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserPetLinkRepository userPetLinkRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${pet.resource}")
	private String petResourceUrl;

	private UserSort userSort = new UserSort();
	
	public Page<UserResponseDTO> find(Pageable pageable) {
		return find(pageable, null);
	}

	public Page<UserResponseDTO> find(Pageable pageable, String searchString) {
		log.info("finding users search = " + searchString );
		if(null != searchString) {
			List<UserResponseDTO> users = new ArrayList<>();
			for (User u : userRepository.findAll()) {
				UserResponseDTO userResponseDTO = new UserResponseDTO();
				userResponseDTO.setId(u.getId());
				userResponseDTO.setEmail(u.getEmail());
				userResponseDTO.setFirstName(u.getFirstName());
				userResponseDTO.setLastName(u.getLastName());
				userResponseDTO.setGender(u.getGender());
				userResponseDTO.setPets(getPets(u.getId()));
				if(u.getFirstName() != null && u.getFirstName().toLowerCase().contains(searchString.toLowerCase())) {
					if(!users.contains(u))
						users.add(userResponseDTO);
				}
				if(u.getLastName() != null && u.getLastName().toLowerCase().contains(searchString.toLowerCase())) {
					if(!users.contains(u))
						users.add(userResponseDTO);
				}
				if(u.getEmail() != null && u.getEmail().toLowerCase().contains(searchString.toLowerCase())) {
					if(!users.contains(u))
						users.add(userResponseDTO);
				}
				if(u.getGender() != null && u.getGender().toLowerCase().contains(searchString.toLowerCase())) {
					if(!users.contains(u))
						users.add(userResponseDTO);
				}
				if(u.getIpAddress() != null && u.getIpAddress().toLowerCase().contains(searchString.toLowerCase())) {
					if(!users.contains(u))
						users.add(userResponseDTO);
				}
			}
			userSort.sortUsers(users);
			return createPage(users, pageable);
		} else {
			log.info("null search string");
			Page<User> page = userRepository.findAll(pageable);
			List<User> users = page.getContent();
			List<UserResponseDTO> dtos = new ArrayList<>();
			for (User u : users) {
				UserResponseDTO userResponseDTO = new UserResponseDTO();
				userResponseDTO.setId(u.getId());
				userResponseDTO.setEmail(u.getEmail());
				userResponseDTO.setFirstName(u.getFirstName());
				userResponseDTO.setLastName(u.getLastName());
				userResponseDTO.setGender(u.getGender());
				userResponseDTO.setPets(getPets(u.getId()));
				dtos.add(userResponseDTO);
			}
			return createPage(dtos, pageable);
		}
	}

	private Page<UserResponseDTO> createPage(List<UserResponseDTO> users, Pageable pageable) {
		if(pageable.getPageNumber() * pageable.getPageSize() >= users.size())
			return  new PageImpl(Arrays.asList(), pageable, users.size());
		return new PageImpl(users.subList(pageable.getPageNumber() * pageable.getPageSize(),
				(pageable.getPageNumber() * pageable.getPageSize()) + pageable.getPageSize() >= users.size()
						? users.size() - 1 : (pageable.getPageNumber() *
						pageable.getPageSize()) + pageable.getPageSize()), pageable, users.size());
	}
	public UserResponseDTO find(Integer id) throws UserDoesNotExistsException {
		User u = userRepository.findOne(id);
		if(null == u)
			throw new UserDoesNotExistsException("User with id "+ u.getId() + " does not exists.");
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setId(u.getId());
		userResponseDTO.setEmail(u.getEmail());
		userResponseDTO.setFirstName(u.getFirstName());
		userResponseDTO.setLastName(u.getLastName());
		userResponseDTO.setGender(u.getGender());
		userResponseDTO.setPets(getPets(u.getId()));
		return userResponseDTO;
	}
	
	public User create(String firstName, String lastName, String email, String gender) throws UserAlreadyExistsException {
		List<User> users = userRepository.findAll();
		for (User u : users) {
			if(email.equals(userRepository.findOne(u.getId()).getEmail())) {
				throw new UserAlreadyExistsException("Duplicated email");
			}
			if((firstName+lastName).equals((userRepository.findOne(u.getId()).getFirstName()
					+ userRepository.findOne(u.getId()).getLastName()))) {
				throw new UserAlreadyExistsException("Exact username");
			}
		}
		User user = User.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.gender(gender)
				.build();
		return userRepository.save(user);
	}

	public UserPetLink linkPet(Integer userId, Integer petId) throws UserDoesNotExistsException {
		User user = userRepository.findOne(userId);
		if(null == user)
			throw new UserDoesNotExistsException("User with id "+ user.getId() + " does not exists.");
		UserPetLink userPetLink = UserPetLink.builder()
				.userId(userId)
				.petId(petId)
				.build();
		return userPetLinkRepository.save(userPetLink);
	}
	
	public User update(User user) {
		return userRepository.save(user);
	}
	
	public void delete(Integer id) {
		userRepository.delete(id);
	}

	private List<PetDTO> getPets(Integer userId) {
		List<PetDTO> list = new ArrayList<>();
		List<UserPetLink> userPetLinks = userPetLinkRepository.findByUserId(userId);
		log.info(userId + " with " + userPetLinks.size() + " pets linked.");
		userPetLinks.forEach(userPetLink -> {
			log.info("getting pet " + userPetLink.getPetId() + " for " + userId);
			ResponseEntity<PetDTO> response = restTemplate.getForEntity(petResourceUrl + userPetLink.getPetId(),PetDTO.class);
			PetDTO petDTO = response.getBody();
			if(petDTO != null && petDTO.getName() != null) {
				list.add(petDTO);
			}
		});
		return list;
	}

}
