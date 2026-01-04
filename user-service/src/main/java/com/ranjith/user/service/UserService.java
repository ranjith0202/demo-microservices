package com.ranjith.user.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ranjith.user.dto.UserDto;
import com.ranjith.user.dto.UserPatchDto;
import com.ranjith.user.entity.Roles;
import com.ranjith.user.entity.UserEntity;
import com.ranjith.user.exception.ResourceNotFoundException;
import com.ranjith.user.repository.RolesInterface;
import com.ranjith.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RolesInterface rolesRepository;
    private final PasswordEncoder passwordEncoder;

	
	private final ModelMapper modelMapper;
	
	 private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	 
    public UserService(UserRepository userRepository, RolesInterface rolesRepository,
    		ModelMapper modelMapper,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
    }

	
	public  UserDto convertToDto(UserEntity userEntity) {
		return modelMapper.map(userEntity,UserDto.class);
	}
	
	public  UserEntity convertToEntity(UserDto userDto) {
		return modelMapper.map(userDto,UserEntity.class);
	}
	@Transactional
	public UserDto createUser(UserDto userDto) {
		UserEntity userEntity = this.convertToEntity(userDto);
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		/*
		 * Set<Roles> roleEntities = userDto.getRolesIds() .stream()
		 * .map(rolesRepository::findById) .filter(Optional::isPresent)
		 * .map(Optional::get) .collect(Collectors.toSet());
		 */

		//userEntity.setRoles(roleEntities);

		
		
		return convertToDto(userRepository.save(userEntity));
	}
	
	public UserDto getUserById(Long id) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Record Not found with "+id ));
		return convertToDto(userEntity);
		
	}
	
	public List<UserDto> getAllUsers(){
		List<UserEntity> usersList = userRepository.findAll();
		
		return usersList.stream()
				.map(this::convertToDto)
				.toList();
	}
	@Transactional
	public UserDto updateUser(Long id,UserDto userDto) {
		
		//Have to update all Fields if you want to update particular fields use patchUser method
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Record Not found with "+id ));
		  //modelMapper.getConfiguration().setSkipNullEnabled(true); // ignore nulls
		 // modelMapper.map(userDto, userEntity); // maps only non-null fields
		
		userEntity = convertToEntity(userDto);
		userEntity.setId(id);
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return convertToDto(userRepository.save(userEntity));
	}
	@Transactional
	public UserDto patchUser(Long id, UserPatchDto userDto) {
	    UserEntity userEntity = userRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Record Not found with "+id ));
	    modelMapper.getConfiguration().setSkipNullEnabled(true);
	    modelMapper.map(userDto, userEntity); // only non-null fields
	    if(userDto.getPassword() != null) {
	    	userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
	    }
	    return convertToDto(userRepository.save(userEntity));
	}
	
	@Transactional
	public void deleteUser(Long id) {
		if(userRepository.existsById(id)) {
			userRepository.deleteById(id);
		}else {
			throw new ResourceNotFoundException("Record Not found with "+id );
		}
	}


	public UserDto getByUserName(String username) {
		UserEntity userEntity = userRepository.findByUserName(username)
		        .orElseThrow(() -> new ResourceNotFoundException("Record Not found with "+username ));
		return convertToDto(userEntity);
	}
	
	
}
