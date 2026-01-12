package com.ranjith.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.dto.main.dto.ApiResponse;
import com.dto.main.dto.UserDto;
import com.ranjith.authservice.dto.LoginRequest;
import com.ranjith.authservice.repositoy.UserServiceClient;
import com.ranjith.authservice.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserServiceClient userClient;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(LoginRequest request) {
    	System.out.println("request -> "+request.getUserName());
    	
        UserDto apiResponse = userClient.getUser(request.getUserName());
System.out.println("request password ->"+request.getPassword());
System.out.println("apiResponse.getBody().getData()  ->"+apiResponse.getPassword());
        if (!BCrypt.checkpw(request.getPassword(), apiResponse.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(apiResponse.getUserName(), apiResponse.getRolesIds());
    }
    
    public String createUser(UserDto userDto) {
    	System.out.println("User Dto -> "+userDto);
    	
    	ResponseEntity<ApiResponse<UserDto>> apiRespose = userClient.createUser(userDto);
		

        return jwtUtil.generateToken(apiRespose.getBody().getData().getUserName(), apiRespose.getBody().getData().getRolesIds()); 
    }	
}
