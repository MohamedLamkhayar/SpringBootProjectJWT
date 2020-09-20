package com.brightcoding.app.ws.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brightcoding.app.ws.entities.UserEntity;
import com.brightcoding.app.ws.repositories.UserRepository;
import com.brightcoding.app.ws.responses.UserResponse;
import com.brightcoding.app.ws.services.UserService;
import com.brightcoding.app.ws.shared.Utils;
import com.brightcoding.app.ws.shared.dto.AddressDto;
import com.brightcoding.app.ws.shared.dto.ContactDto;
import com.brightcoding.app.ws.shared.dto.UserDto;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	Utils util;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	ModelMapper modelMapper = new ModelMapper();
	
	public ModelMapper modelMapper() {
	    ModelMapper modelMapper = new ModelMapper();
	    modelMapper.getConfiguration()
	        .setMatchingStrategy(MatchingStrategies.STRICT);
			return modelMapper;
	}
	@Override
	public UserDto createUser(UserDto user) {

		UserEntity checkUser = userRepository.findByEmail(user.getEmail());
		if (checkUser != null) {
			throw new RuntimeException("User is already exist");
		}
		
		
		for (int i = 0; i < user.getAddresses().size(); i++) {
			AddressDto address = user.getAddresses().get(i);
			//address.setUser(user);
			address.setAddressId(util.generateStringId(30));
			user.getAddresses().set(i, address);
		}
		ContactDto contact = user.getContact();
		
		contact.setUser(user);
		
		contact.setContactId(util.generateStringId(30));
		/*UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);*/
		
		UserEntity userEntity = modelMapper.map(user,UserEntity.class);

		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		userEntity.setUserId(util.generateStringId(30));

		UserEntity newUser = userRepository.save(userEntity);
		//ModelMapper modelMapper = new ModelMapper();
		//UserDto userDto =  modelMapper.map(newUser,UserDto.class);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(newUser, userDto);

		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		/*ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userEntity, UserDto.class);*/
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}
		
		UserDto userDto =  modelMapper().map(userEntity, UserDto.class);

		//BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());

		UserEntity userUpdated = userRepository.save(userEntity);
		
		UserDto user = modelMapper().map(userUpdated, UserDto.class);

		//BeanUtils.copyProperties(userUpdated, user);
		return user;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDto> getUsers(int page, int limit, String search, int status) {
		if (page > 0) {
			page = page - 1;
		}
		List<UserDto> usersDto = new ArrayList<UserDto>();
		org.springframework.data.domain.Pageable pageableRequest = PageRequest.of(page, limit);
		Page<UserEntity> userPage;
		if (search.isEmpty()) {
			userPage =  userRepository.findAllUsers(pageableRequest);
		}
		else {
			 userPage =  userRepository.finAllUserByCriteria(pageableRequest, search, status);
		}
		
		List<UserEntity> users = userPage.getContent();
		for (UserEntity userEntity : users) {
			
			//UserDto user = modelMapper.map(userEntity, UserDto.class);
			UserDto user = new UserDto();
			BeanUtils.copyProperties(userEntity,user);
			usersDto.add(user);
		}
		return usersDto;
	}

}
