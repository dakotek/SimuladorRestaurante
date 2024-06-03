package es.metrica.mar24.SimuladorRestaurante.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.metrica.mar24.SimuladorRestaurante.entities.User;
import es.metrica.mar24.SimuladorRestaurante.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
 
@Service
public class UserService {
 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
 
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
 
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
 
    public User saveUser(User user) {
        return userRepository.save(user);
    }
 
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
 
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
        	User existingUser = optionalUser.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }
}