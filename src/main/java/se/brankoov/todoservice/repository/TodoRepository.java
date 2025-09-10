package se.brankoov.todoservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.brankoov.todoservice.entity.Todo;

public interface TodoRepository extends MongoRepository<Todo, String> {
}
