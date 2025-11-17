package com.coingshuttle.projects.airbnbApp.Security;

import com.coingshuttle.projects.airbnbApp.Dto.SignUpRequestDto;
import com.coingshuttle.projects.airbnbApp.Dto.UserDto;
import com.coingshuttle.projects.airbnbApp.Entity.User;
import com.coingshuttle.projects.airbnbApp.Entity.enums.Role;
import com.coingshuttle.projects.airbnbApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto signUp(SignUpRequestDto  signUpRequestDto){

        User user = userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);

        if (user != null) {
            throw new RuntimeException("User is already present with same email id");
        }

        User newUser = modelMapper.map(signUpRequestDto, User.class);
        newUser.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        newUser = userRepository.save(newUser);

        return modelMapper.map(newUser, UserDto.class);
    }
}
