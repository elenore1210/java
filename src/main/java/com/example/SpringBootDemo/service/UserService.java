package com.example.SpringBootDemo.service;

import com.example.SpringBootDemo.entity.User;
import com.example.SpringBootDemo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.SpringBootDemo.utils.PasswordUtils;

import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    public User loginUser(String username, String password) {
        String hashedPassword = PasswordUtils.encryptPassword(password);
        return userRepository.findByUsernameAndPassword(username, hashedPassword);
    }

    public boolean saveUser(User user) {
        try {
            String encryptedPassword = PasswordUtils.encryptPassword(user.getPassword());
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            logger.error("Error saving user: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            logger.info(String.valueOf(e));
            return false;
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public boolean updateUser(User user) {
        String encryptedPassword = PasswordUtils.encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);
        if (userRepository.existsById(user.getId())) {
            try {
                userRepository.save(user);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}