package se.brankoov.todoservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.brankoov.todoservice.entity.Todo;
import java.util.Optional;

public interface TodoRepository extends MongoRepository<Todo, String> {
    Optional<Todo> findByTitle(String title);
}
