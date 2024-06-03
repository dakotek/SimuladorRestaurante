package es.metrica.mar24.SimuladorRestaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import es.metrica.mar24.SimuladorRestaurante.controller.UserController;
import es.metrica.mar24.SimuladorRestaurante.entities.Rol;
import es.metrica.mar24.SimuladorRestaurante.entities.User;
import es.metrica.mar24.SimuladorRestaurante.log.LoginRequest;
import es.metrica.mar24.SimuladorRestaurante.log.RegisterRequest;
import es.metrica.mar24.SimuladorRestaurante.repositories.UserRepository;
import es.metrica.mar24.SimuladorRestaurante.services.UserService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class TestUser {

    private UserService userService;
    private UserRepository userRepository;
    private UserController userController;

    @BeforeEach
    void setUp() {
    	userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        userController = new UserController();
        userController.userService = userService;
    }
    
	@Test
	void testUser() {
		User user = new User();
		user.setId(1);
		user.setUsername("user1");
		user.setPassword("password1234");
		user.setEmail("user1@email.com");
		user.setRole(Rol.CLIENT);
		assertEquals(1, user.getId());
		assertEquals("user1", user.getUsername());
		assertEquals("password1234", user.getPassword());
		assertEquals("user1@email.com", user.getEmail());
		assertEquals(Rol.CLIENT, user.getRole());

		User user2 = new User(2, "user2", "password4321", "user2@email.com", Rol.COOK);
		assertEquals(2, user2.getId());
		assertEquals("user2", user2.getUsername());
		assertEquals("password4321", user2.getPassword());
		assertEquals("user2@email.com", user2.getEmail());
		assertEquals(Rol.COOK, user2.getRole());
	}

	@Test
	void testUserBuilder() {
		User user = User.builder()
				.id(1)
				.username("user1")
				.password("password1234")
				.email("user1@email.com")
				.role(Rol.CLIENT)
				.build();
		assertEquals(1, user.getId());
		assertEquals("user1", user.getUsername());
		assertEquals("password1234", user.getPassword());
		assertEquals("user1@email.com", user.getEmail());
		assertEquals(Rol.CLIENT, user.getRole());
	}

	@Test
	void testFindByEmail() {
		UserRepository userRepository = mock(UserRepository.class);
		User user = new User(1, "test", "passwordTest", "test@email.com", Rol.CLIENT);
		when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));

		Optional<User> result = userRepository.findByEmail("test@email.com");
		assertEquals(user, result.get());
	}

	@Test
    void testGetAllUsersService() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "user1", "password1", "user1@example.com", Rol.CLIENT));
        userList.add(new User(2, "user2", "password2", "user2@example.com", Rol.COOK));
        when(userRepository.findAll()).thenReturn(userList);
        
        List<User> result = userService.getAllUsers();
        
        assertEquals(2, result.size());
        assertEquals(userList.get(0), result.get(0)); 
        assertEquals(userList.get(1), result.get(1));
    }
	
	@Test
    void testGetAllUsersController() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "user1", "password1", "user1@example.com", Rol.CLIENT));
        userList.add(new User(2L, "user2", "password2", "user2@example.com", Rol.COOK));
        when(userService.getAllUsers()).thenReturn(userList);

        List<User> result = userController.getAllUsers();

        assertEquals(2, result.size());
        assertEquals(userList.get(0), result.get(0));
        assertEquals(userList.get(1), result.get(1));
    }
	
	@Test
    void testGetUserByIdService() {
        User user = new User(1, "user1", "password1", "user1@example.com", Rol.CLIENT);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);
        assertEquals(user, result.get());
    }
	
	@Test
    void testGetUserByIdController() {
        User user = new User(1L, "user1", "password1", "user1@example.com", Rol.CLIENT);
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userController.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testSaveUserService() {
        User user = new User(1, "user1", "password1", "user1@example.com", Rol.CLIENT);
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);
        assertEquals(user, savedUser);
    }
    
    @Test
    void testCreateUserController() {
        User user = new User(1L, "user1", "password1", "user1@example.com", Rol.CLIENT);
        when(userService.saveUser(user)).thenReturn(user);

        User result = userController.createUser(user);

        assertEquals(user, result);
    }

    @Test
    void testDeleteUserService() {
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }
    
    @Test
    void testDeleteUserController() {
    	userController.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testFindByEmailService() {
        User user = new User(1, "user1", "password1", "user1@example.com", Rol.CLIENT);
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("user1@example.com");
        assertEquals(user, result.get());
    }
    
    @Test
    void testGetUserByEmailController() {
        User user = new User(1L, "user1", "password1", "user1@example.com", Rol.CLIENT);
        when(userService.findByEmail("user1@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userController.getUserByEmail("user1@example.com");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testUpdateUserService() {
        User existingUser = new User(1, "user1", "password1", "user1@example.com", Rol.CLIENT);
        User newUser = new User(1, "newUser", "newPassword", "newuser@example.com", Rol.COOK);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = userService.updateUser(1L, newUser);
        assertEquals(newUser.getUsername(), updatedUser.getUsername());
        assertEquals(newUser.getPassword(), updatedUser.getPassword());
        assertEquals(newUser.getEmail(), updatedUser.getEmail());
    }
    
    @Test
    void testUpdateUserController() {
    	User existingUser = new User(1, "user1", "password1", "user1@example.com", Rol.CLIENT);
        User newUser = new User(1, "newUser", "newPassword", "newuser@example.com", Rol.COOK);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = userController.updateUser(1L, newUser);
        assertEquals(newUser.getUsername(), updatedUser.getUsername());
        assertEquals(newUser.getPassword(), updatedUser.getPassword());
        assertEquals(newUser.getEmail(), updatedUser.getEmail());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.updateUser(1L, new User());
        });
    }
    
    @Test
    void getAuthorities() {
        User user = new User();
        user.setRole(Rol.CLIENT);

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(Rol.CLIENT.name())));
    }

    @Test
    void isAccountNonExpired() {
        User user = new User();
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        User user = new User();
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        User user = new User();
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        User user = new User();
        assertTrue(user.isEnabled());
    }
    
    @Test
    void testLoginRequest() {
    	String username = "user1@email.com";
        String password = "password1234";
        LoginRequest loginRequest = new LoginRequest(username, password);

        assertEquals(username, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());

        loginRequest.setEmail("user2@email.com");
        loginRequest.setPassword("password4321");
        assertEquals("user2@email.com", loginRequest.getEmail());
        assertEquals("password4321", loginRequest.getPassword());
    }

    @Test
    void testLoginRequestBuilder() {
    	String username = "user1@email.com";
        String password = "password1234";
        LoginRequest loginRequest = LoginRequest.builder()
                .setEmail(username)
                .setPassword(password)
                .build();

        assertEquals(username, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());

        LoginRequest loginRequest2 = LoginRequest.builder()
                .setEmail("user2@email.com")
                .setPassword("password4321")
                .build();

        assertEquals("user2@email.com", loginRequest2.getEmail());
        assertEquals("password4321", loginRequest2.getPassword());
    }
    
    @Test
    void testEmptyLoginRequestBuilder() {
        LoginRequest loginRequest = LoginRequest.builder().build();
        assertNull(loginRequest.getEmail());
        assertNull(loginRequest.getPassword());
    }
    
    @Test
    void testRegisterRequest() {
        String username = "user1";
        String password = "password1234";
        String email = "user1@example.com";
        String role = "CLIENT";
        
        RegisterRequest registerRequest = new RegisterRequest(username, password, email, role);

        assertEquals(username, registerRequest.getUsername());
        assertEquals(password, registerRequest.getPassword());
        assertEquals(email, registerRequest.getEmail());
        assertEquals(role, registerRequest.getRole());
        
        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setUsername("user2");
        registerRequest2.setPassword("password4321");
        registerRequest2.setEmail("user2@example.com");
        registerRequest2.setRole("COOK");

        assertEquals("user2", registerRequest2.getUsername());
        assertEquals("password4321", registerRequest2.getPassword());
        assertEquals("user2@example.com", registerRequest2.getEmail());
        assertEquals("COOK", registerRequest2.getRole());
    }

    @Test
    void testRegisterRequestBuilder() {
        String username = "user1";
        String password = "password1234";
        String email = "user1@example.com";
        String role = "CLIENT";
        
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();

        assertEquals(username, registerRequest.getUsername());
        assertEquals(password, registerRequest.getPassword());
        assertEquals(email, registerRequest.getEmail());
        assertEquals(role, registerRequest.getRole());

        RegisterRequest registerRequest2 = RegisterRequest.builder()
                .username("user2")
                .password("password4321")
                .email("user2@example.com")
                .role("COOK")
                .build();

        assertEquals("user2", registerRequest2.getUsername());
        assertEquals("password4321", registerRequest2.getPassword());
        assertEquals("user2@example.com", registerRequest2.getEmail());
        assertEquals("COOK", registerRequest2.getRole());
    }

    @Test
    void testEmptyRegisterRequestBuilder() {
        RegisterRequest registerRequest = RegisterRequest.builder().build();
        assertNull(registerRequest.getUsername());
        assertNull(registerRequest.getPassword());
        assertNull(registerRequest.getEmail());
        assertNull(registerRequest.getRole());
    }
}