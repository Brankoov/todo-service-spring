package se.brankoov.todoservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.brankoov.todoservice.entity.AppUser;

import java.util.Optional;

public interface UserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);
}