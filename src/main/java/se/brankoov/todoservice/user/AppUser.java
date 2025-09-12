package se.brankoov.todoservice.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("users")
public class AppUser {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    // BCRYPT-hash (ex: {bcrypt}$2a$10$...)
    private String password;

    private Set<String> roles = Set.of("USER");

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}