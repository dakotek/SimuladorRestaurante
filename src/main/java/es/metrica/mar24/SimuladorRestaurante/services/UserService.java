package es.metrica.mar24.SimuladorRestaurante.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import es.metrica.mar24.SimuladorRestaurante.entities.UserEntity;
import es.metrica.mar24.SimuladorRestaurante.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
 
@Service
public class UserService {
 
    @Autowired
    private UserRepository userRepository;
 
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
 
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }
 
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }
 
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
 
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public UserEntity updateUser(Long id, UserEntity user) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity existingUser = optionalUser.get();
            // Update fields as necessary
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            // ... update other fields as necessary
            return userRepository.save(existingUser);
        } else {
            // Handle the case where the user does not exist
            throw new RuntimeException("User not found with id " + id);
        }
    }
}