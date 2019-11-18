package br.com.training.user.service;

import br.com.training.user.exception.UserAlreadyExistsException;
import br.com.training.user.model.User;
import br.com.training.user.repository.UserRepository;
import br.com.training.user.utils.UserSort;
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
public class UserService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	private UserSort userSort = new UserSort();
	
	public Page<User> find(Pageable pageable) {
		return find(pageable, null);
	}

	public Page<User> find(Pageable pageable, String searchString) {
		log.info("finding users search = " + searchString );
		if(null != searchString) {
			List<User> users = new ArrayList<>();
			for (User u : userRepository.findAll()) {
				if(u.getFirstName() != null && u.getFirstName().toLowerCase().contains(searchString.toLowerCase())) {
					if(!users.contains(u))
						users.add(u);
				}
				if(u.getLastName() != null && u.getLastName().toLowerCase().contains(searchString.toLowerCase())) {
					if(!users.contains(u))
						users.add(u);
				}
				if(u.getEmail() != null && u.getEmail().toLowerCase().contains(searchString.toLowerCase())) {
					if(!users.contains(u))
						users.add(u);
				}
				if(u.getGender() != null && u.getGender().toLowerCase().contains(searchString.toLowerCase())) {
					if(!users.contains(u))
						users.add(u);
				}
				if(u.getIpAddress() != null && u.getIpAddress().toLowerCase().contains(searchString.toLowerCase())) {
					if(!users.contains(u))
						users.add(u);
				}
			}
			userSort.sortUsers(users);
			return createPage(users, pageable);
		} else {
			log.info("null search string");
			return userRepository.findAll(pageable);
		}
	}

	private Page<User> createPage(List<User> users, Pageable pageable) {
		if(pageable.getPageNumber() * pageable.getPageSize() >= users.size())
			return  new PageImpl(Arrays.asList(), pageable, users.size());
		return new PageImpl(users.subList(pageable.getPageNumber() * pageable.getPageSize(),
				(pageable.getPageNumber() * pageable.getPageSize()) + pageable.getPageSize() >= users.size()
						? users.size() - 1 : (pageable.getPageNumber() *
						pageable.getPageSize()) + pageable.getPageSize()), pageable, users.size());
	}
	public User find(String id) {
		return userRepository.findOne(id);
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
	
	public User update(User user) {
		return userRepository.save(user);
	}
	
	public void delete(String id) {
		userRepository.delete(id);
	}

}
