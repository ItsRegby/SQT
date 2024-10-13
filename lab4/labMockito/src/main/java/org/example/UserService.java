package org.example;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        try {
            userRepository.createUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + user.getName(), e);
        }
    }

    public User getUserById(long id) {
        try {
            return userRepository.getUserById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user with ID: " + id, e);
        }
    }

    public void updateUser(User user) {
        try {
            userRepository.updateUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + user.getName(), e);
        }
    }

    public void deleteUser(long id) {
        try {
            userRepository.deleteUser(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user with ID: " + id, e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.getAllUsers();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all users", e);
        }
    }
}
