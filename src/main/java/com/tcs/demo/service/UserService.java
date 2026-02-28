package com.tcs.demo.service;

import com.tcs.demo.entity.User;
import com.tcs.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository repo,
                       BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public void registerUser(String username, String password) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password)); // 🔐 BCrypt
        user.setRole("USER");

        repo.save(user);
    }

    public void resetPassword(String username, String newPassword) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(encoder.encode(newPassword));
        repo.save(user);
    }
}