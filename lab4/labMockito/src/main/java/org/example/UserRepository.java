package org.example;

import java.util.List;

public interface UserRepository {
    void createUser(User user);
    User getUserById(long id);
    void updateUser(User user);
    void deleteUser(long id);
    List<User> getAllUsers();
}

