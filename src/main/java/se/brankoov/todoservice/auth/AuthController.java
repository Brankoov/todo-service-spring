package se.brankoov.todoservice.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import se.brankoov.todoservice.repository.UserRepository;
import se.brankoov.todoservice.entity.AppUser;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo; this.encoder = encoder;
    }

    public record RegisterReq(
            @NotBlank
            @Size(min=3, max=32, message="username must be 3–32 chars")
            @Pattern(regexp="^[a-zA-Z0-9._-]+$", message="username allows letters, digits, dot, underscore, hyphen")
            String username,

            @NotBlank
            @Size(min=8, max=72, message="password must be 8–72 chars")
            String password
    ) {}

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterReq r, UriComponentsBuilder uri) {
        // enkel koll – unikt username (fångas även av unique index)
        repo.findByUsername(r.username()).ifPresent(u -> {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.CONFLICT, "Username already exists");
        });

        var u = new AppUser();
        u.setUsername(r.username());
        u.setPassword(encoder.encode(r.password())); // BCRYPT
        var saved = repo.save(u);

        var location = uri.path("/users/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
