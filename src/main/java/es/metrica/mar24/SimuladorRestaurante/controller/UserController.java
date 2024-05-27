package es.metrica.mar24.SimuladorRestaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.metrica.mar24.SimuladorRestaurante.entities.UserEntity;
import es.metrica.mar24.SimuladorRestaurante.services.UserService;

import java.util.List;
import java.util.Optional;
 
@RestController
@RequestMapping("/users")
public class UserController {
 
    @Autowired
    private UserService userService;
 
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }
 
    @GetMapping("/{id}")
    public Optional<UserEntity> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
 
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.saveUser(user);
    }
 
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
 
    @GetMapping("/email/{email}")
    public Optional<UserEntity> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
    
    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        return userService.updateUser(id, user);
    }
}