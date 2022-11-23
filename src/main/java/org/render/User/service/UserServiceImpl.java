package org.render.User.service;

import org.render.Role;
import org.render.User.DTO.UserDTO;
import org.render.User.entity.User;
import org.render.User.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void createNewUser(UserDTO userDTO) {

        User existingUser = userRepository.findUserByEmail(userDTO.getEmail());

        if (existingUser != null) {
            throw new IllegalArgumentException("This email address already exists");
        }

        User newUser = User.newBuilder()
                            .setFirstName(userDTO.getFirstName())
                            .setLastName(userDTO.getLastName())
                            .setEmail(userDTO.getEmail())
                            .setRole(Role.ROLE_USER)
                            .setPassword(passwordEncoder.encode(userDTO.getPassword())).build();

        userRepository.save(newUser);
    }
}
