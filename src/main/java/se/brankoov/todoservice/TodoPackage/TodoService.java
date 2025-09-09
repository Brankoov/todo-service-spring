package se.brankoov.todoservice.TodoPackage;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getAll() {
        return repository.findAll();
    }

    public Todo create(Todo todo) {
        return repository.save(todo);
    }

    public Todo update(Long id, Todo newTodo) {
        return repository.findById(id)
                .map(todo -> {
                    todo.setTitle(newTodo.getTitle());
                    todo.setDescription(newTodo.getDescription());
                    todo.setCompleted(newTodo.isCompleted());
                    return repository.save(todo);
                }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
