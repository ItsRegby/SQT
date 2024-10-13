import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.User;
import org.example.UserRepository;
import org.example.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User newUser = new User("John Doe", "johndoe@example.com");
        logger.info("Creating user: {}", newUser);

        userService.createUser(newUser);

        verify(userRepository, times(1)).createUser(newUser);
        logger.info("User created successfully: {}", newUser);
    }

    @Test
    public void testGetUserById() {
        long userId = 1L;
        User existingUser = new User("Jane Doe", "janedoe@example.com");
        logger.info("Retrieving user by ID: {}", userId);

        when(userRepository.getUserById(userId)).thenReturn(existingUser);

        User result = userService.getUserById(userId);
        assertEquals(existingUser, result);

        verify(userRepository, times(1)).getUserById(userId);
        logger.info("User retrieved successfully: {}", result);
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User("John Doe", "johnupdated@example.com");
        updatedUser.setId(1L);
        logger.info("Updating user: {}", updatedUser);

        userService.updateUser(updatedUser);

        verify(userRepository, times(1)).updateUser(updatedUser);
        logger.info("User updated successfully: {}", updatedUser);
    }

    @Test
    public void testDeleteUser() {
        long userId = 1L;
        logger.info("Deleting user with ID: {}", userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteUser(userId);
        logger.info("User deleted successfully with ID: {}", userId);
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = Arrays.asList(
                new User("John Doe", "johndoe@example.com"),
                new User("Jane Doe", "janedoe@example.com"),
                new User("Alice Smith", "alicesmith@example.com"),
                new User("Bob Johnson", "bobjohnson@example.com"),
                new User("Charlie Brown", "charliebrown@example.com")
        );
        logger.info("Retrieving all users");

        when(userRepository.getAllUsers()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertEquals(userList, result);
        verify(userRepository, times(1)).getAllUsers();
        logger.info("Users retrieved successfully: {}", result);
    }

    @Test
    public void testCreateMultipleUsers() {
        List<User> users = Arrays.asList(
                new User("John Doe", "johndoe@example.com"),
                new User("Jane Doe", "janedoe@example.com"),
                new User("Alice Smith", "alicesmith@example.com")
        );

        for (User user : users) {
            logger.info("Creating user: {}", user);
            userService.createUser(user);
            verify(userRepository, times(1)).createUser(user);
            logger.info("User created successfully: {}", user);
        }
    }

    @Test
    public void testGetAllUsersEmptyList() {
        logger.info("Retrieving all users when the list is empty");
        when(userRepository.getAllUsers()).thenReturn(Arrays.asList());

        List<User> result = userService.getAllUsers();
        assertTrue(result.isEmpty());

        verify(userRepository, times(1)).getAllUsers();
        logger.info("No users found, empty list returned.");
    }

    @Test
    public void testUpdateMultipleUsers() {
        List<User> users = Arrays.asList(
                new User("John Doe", "johnupdated@example.com"),
                new User("Jane Doe", "janeupdated@example.com")
        );

        for (User user : users) {
            user.setId(1L);
            logger.info("Updating user: {}", user);
            userService.updateUser(user);
            verify(userRepository, times(1)).updateUser(user);
            logger.info("User updated successfully: {}", user);
        }
    }

    @Test
    public void testDeleteMultipleUsers() {
        long[] userIds = {1L, 2L, 3L};

        for (long userId : userIds) {
            logger.info("Deleting user with ID: {}", userId);
            userService.deleteUser(userId);
            verify(userRepository, times(1)).deleteUser(userId);
            logger.info("User deleted successfully with ID: {}", userId);
        }
    }

    @Test
    public void testCreateUserThrowsException() {
        User newUser = new User("John Doe", "johndoe@example.com");
        logger.error("Attempting to create user, expecting failure: {}", newUser);
        doThrow(new RuntimeException("Database error")).when(userRepository).createUser(newUser);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.createUser(newUser));
        assertEquals("Failed to create user: John Doe", exception.getMessage());
        logger.error("Failed to create user: {}", exception.getMessage());
    }

    @Test
    public void testGetUserByIdThrowsException() {
        long userId = 1L;
        logger.error("Attempting to retrieve user by ID, expecting failure: {}", userId);
        when(userRepository.getUserById(userId)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserById(userId));
        assertEquals("Failed to retrieve user with ID: 1", exception.getMessage());
        logger.error("Failed to retrieve user: {}", exception.getMessage());
    }

    @Test
    public void testUpdateUserThrowsException() {
        User updatedUser = new User("John Doe", "johnupdated@example.com");
        updatedUser.setId(1L);
        logger.error("Attempting to update user, expecting failure: {}", updatedUser);
        doThrow(new RuntimeException("Database error")).when(userRepository).updateUser(updatedUser);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.updateUser(updatedUser));
        assertEquals("Failed to update user: John Doe", exception.getMessage());
        logger.error("Failed to update user: {}", exception.getMessage());
    }

    @Test
    public void testDeleteUserThrowsException() {
        long userId = 1L;
        logger.error("Attempting to delete user, expecting failure with ID: {}", userId);
        doThrow(new RuntimeException("Database error")).when(userRepository).deleteUser(userId);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
        assertEquals("Failed to delete user with ID: 1", exception.getMessage());
        logger.error("Failed to delete user: {}", exception.getMessage());
    }

    @Test
    public void testGetAllUsersThrowsException() {
        logger.error("Attempting to retrieve all users, expecting failure");
        when(userRepository.getAllUsers()).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getAllUsers());
        assertEquals("Failed to retrieve all users", exception.getMessage());
        logger.error("Failed to retrieve users: {}", exception.getMessage());
    }
}
