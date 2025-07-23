package com.example.todoapp.service;

import com.example.todoapp.model.User;
import com.example.todoapp.repository.UserRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
        throw new RuntimeException("User not found: " + username);
    }
    return user;

}
public User findByUsernameOrNull(String username) {
    return userRepository.findByUsername(username);
}



public void saveUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.getRoles().add("ROLE_USER"); // add role on signup
    userRepository.save(user);
}


public void changePassword(String username, String oldPassword, String newPassword) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
        throw new UsernameNotFoundException("User not found");
    }

    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
        throw new IllegalArgumentException("Current password is incorrect.");
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
}



}
