package com.cognizant.app.lms.users.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.app.lms.users.dto.UserDTO;
import com.cognizant.app.lms.users.entity.UserEntity;
import com.cognizant.app.lms.users.exception.UsersServiceException;
import com.cognizant.app.lms.users.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersServiceInt{

	private UsersRepository usersRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private ModelMapper modelMapper;
	
	
	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	
	@Override
	public UserDTO createUser(UserDTO createUserDto) {
		createUserDto.setUserId(UUID.randomUUID().toString());
		createUserDto.setPassword(bCryptPasswordEncoder.encode(createUserDto.getPassword()));
		
		UserEntity userEntityToCreate = modelMapper.map(createUserDto, UserEntity.class);
		UserEntity createdUserEntity = usersRepository.save(userEntityToCreate);
		UserDTO createdUserDTO = modelMapper.map(createdUserEntity, UserDTO.class);
		return createdUserDTO;
	}

	@Override
	public UserDTO getUserById(String userId) {
		UserEntity receivedUserEntity = usersRepository.findByUserId(userId);
		
		if(null == receivedUserEntity) {
			throw new UsersServiceException("User with given Id doesn't exist");
		}
		
		UserDTO receivedUserDto = modelMapper.map(receivedUserEntity, UserDTO.class);
		return receivedUserDto;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<UserEntity> allUsersEntity = usersRepository.findAll();
		
		List<UserDTO> allUsersDto = new ArrayList<>();
		
		for(UserEntity ue: allUsersEntity) {
			allUsersDto.add(modelMapper.map(ue, UserDTO.class));
		}
		return allUsersDto;
	}

	@Override
	public UserDTO updateUserById(UserDTO userUpdateRequestDTO,String userId) {
		UserEntity userEntityRequestedUpdate = modelMapper.map(userUpdateRequestDTO, UserEntity.class); 
		
		UserEntity receivedUserEntity = usersRepository.findByUserId(userId);
		
		if(null == receivedUserEntity) {
			throw new UsersServiceException("User with given Id doesn't exist");
		}
		
		if(null != userEntityRequestedUpdate.getUserName() &&
				!receivedUserEntity.getUserName().equals(userEntityRequestedUpdate.getUserName())) {
			receivedUserEntity.setUserName(userEntityRequestedUpdate.getUserName());
		}
		
		if(null != userEntityRequestedUpdate.getUserEmail() &&
				!receivedUserEntity.getUserEmail().equals(userEntityRequestedUpdate.getUserEmail())) {
			receivedUserEntity.setUserEmail(userEntityRequestedUpdate.getUserEmail());
		}
		
		
		if(null != userEntityRequestedUpdate.getPassword() &&
			!receivedUserEntity.getPassword().equals(bCryptPasswordEncoder.encode( userEntityRequestedUpdate.getPassword()))) {
			receivedUserEntity.setPassword(bCryptPasswordEncoder.encode( userEntityRequestedUpdate.getPassword()));
		}
		
		UserEntity updatedUseEntity = usersRepository.save(receivedUserEntity);
		UserDTO updatedUserDto = modelMapper.map(updatedUseEntity, UserDTO.class);
		
		return updatedUserDto;
	}

	@Override
	public UserDTO deleteUserById(String userId) {
		UserEntity userEntityToDelete = usersRepository.findByUserId(userId);
		UserDTO deletedUserDTO = modelMapper.map(userEntityToDelete, UserDTO.class);
	
		usersRepository.delete(userEntityToDelete);
		return deletedUserDTO;
	}

	@Override
	public String deletAllUsers() {
		usersRepository.deleteAll();
		return "Deleted All Users";
	}

}
