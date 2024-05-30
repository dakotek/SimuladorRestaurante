package es.metrica.mar24.SimuladorRestaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import es.metrica.mar24.SimuladorRestaurante.entities.Rol;
import es.metrica.mar24.SimuladorRestaurante.entities.User;
import es.metrica.mar24.SimuladorRestaurante.repositories.UserRepository;
import es.metrica.mar24.SimuladorRestaurante.services.UserService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class SimuladorRestauranteApplicationTests {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
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
	void testFindByUsername() {
		UserRepository userRepository = mock(UserRepository.class);
		User user = new User(1, "test", "passwordTest", "test@email.com", Rol.CLIENT);
		when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

		Optional<User> result = userRepository.findByUsername("test");

		assertEquals(user, result.get());
	}

	@Test
    void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "user1", "password1", "user1@example.com", Rol.CLIENT));
        userList.add(new User(2, "user2", "password2", "user2@example.com", Rol.COOK));
        
        when(userRepository.findAll()).thenReturn(userList);
        
        List<User> result = userService.getAllUsers();
        
        assertEquals(2, result.size());
        assertEquals(userList.get(0), result.get(0)); 
        assertEquals(userList.get(1), result.get(1));
    }
}