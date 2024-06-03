package es.metrica.mar24.SimuladorRestaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.metrica.mar24.SimuladorRestaurante.entities.Rol;
import es.metrica.mar24.SimuladorRestaurante.entities.User;
import es.metrica.mar24.SimuladorRestaurante.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
 
    @Autowired
    public UserService userService;
 
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
 
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
 
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
 
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
 
    @GetMapping("/email/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
    
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}