package se.brankoov.todoservice.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import se.brankoov.todoservice.entity.Todo;
import se.brankoov.todoservice.statistics.TodoStats;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends MongoRepository<Todo, String> {
    Optional<Todo> findByTitle(String title);

    @Aggregation(pipeline = {
            "{ $match: { completed: true } }",
            "{ $group: { _id: '$title', count: { $sum: 1 } } }",
            "{ $project: { title: '$_id', count: 1, _id: 0 } }",
            "{ $sort: { count: -1 } }"
    })
    List<TodoStats> getCompletedStatsByTitle();
}
