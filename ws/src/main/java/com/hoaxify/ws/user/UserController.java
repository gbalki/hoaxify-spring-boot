package com.hoaxify.ws.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoaxify.ws.shared.CurrentUser;
import com.hoaxify.ws.shared.GenericResponse;
import com.hoaxify.ws.user.vm.UserUpdateVM;
import com.hoaxify.ws.user.vm.UserVM;

@RestController
@RequestMapping("/api/1.0")
public class UserController {

	@Autowired
	UserService userService;

	// this method saves the requests from comes the Front-end to the database
	@PostMapping("/users")
	public GenericResponse createUser(@Valid @RequestBody User user) {
		userService.save(user);
		return new GenericResponse("User Created");
	}

	// this method lists users to the Front-end
	@GetMapping("/users")
	Page<UserVM> getUsers(Pageable page, @CurrentUser User user) {
		return userService.getUsers(page, user).map(UserVM::new);
	}

	@GetMapping("/users/{username}")
	UserVM getUser(@PathVariable String username) {
		User user = userService.getByUsername(username);
		return new UserVM(user);
	}

	@PutMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	UserVM updateUser(@Valid @RequestBody UserUpdateVM updatedUser, @PathVariable String username) {
		User user = userService.updateUser(username, updatedUser);
		return new UserVM(user);
	}

	@DeleteMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	GenericResponse deleteUser(@PathVariable String username) {
		userService.deleteUser(username);
		return new GenericResponse("User is removed");
	}
}
