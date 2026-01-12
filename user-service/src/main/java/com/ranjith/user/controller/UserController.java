package com.ranjith.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.main.ApiResponse;
import com.dto.main.ResponseHandler;
import com.dto.main.UserDto;
import com.dto.main.UserPatchDto;
import com.ranjith.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers(){
		
		List<UserDto> allUsersList = userService.getAllUsers();
		return ResponseHandler.success("User fetched successfully", allUsersList, HttpStatus.OK);
	}
	
	@GetMapping("/userName/{userName}")
    public UserDto getByUsername(@PathVariable String userName) {
        return userService.getByUserName(userName);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id){
		UserDto userDto = userService.getUserById(id);
		return ResponseHandler.success("User fetched successfully", userDto, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody UserDto userDto){
		userDto = userService.createUser(userDto);
		System.out.println("User controller user dto ->"+userDto);
		return ResponseHandler.success("User "+userDto.getId() +" created successfully", userDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDto>> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Long id){
		userDto = userService.updateUser(id, userDto);
		return ResponseHandler.success("User "+id +" updated successfully", userDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDto>> updateUserPatch(@Valid 
			@RequestBody UserPatchDto userPatchDto,@PathVariable Long id){
		UserDto userDto = userService.patchUser(id, userPatchDto);
		return ResponseHandler.success("User "+userDto.getId() +" updated successfully", userDto, HttpStatus.OK);
	}
	
}
