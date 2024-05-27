package es.metrica.mar24.SimuladorRestaurante.entities;

import jakarta.persistence.*;

import java.util.Objects;
 
@Entity
public class UserEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;
 
    @Column(unique = true)
    private String email;
 
    private String password;
 
    @Enumerated(EnumType.STRING)
    private Role role;
 
    public UserEntity() {
    }
 
    public UserEntity(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public Role getRole() {
        return role;
    }
 
    public void setRole(Role role) {
        this.role = role;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
 
    public enum Role {
        CLIENT,
        COOK
    }
}