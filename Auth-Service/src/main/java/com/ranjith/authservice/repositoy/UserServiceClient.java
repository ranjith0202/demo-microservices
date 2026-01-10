package com.ranjith.authservice.repositoy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dto.main.ApiResponse;
import com.dto.main.UserDto;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
	@GetMapping("/api/users/userName/{userName}")
	UserDto getUser(@PathVariable("userName") String userName);

    @PostMapping("/api/users")
    //ApiResponse<UserDto> createUser(@RequestBody UserDto userDto);
    ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody UserDto userDto);

}
