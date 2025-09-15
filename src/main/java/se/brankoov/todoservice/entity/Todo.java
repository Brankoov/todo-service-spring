package se.brankoov.todoservice.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import java.time.Instant;

@Document(collection = "todos")
public class Todo {
    @Id
    @JsonProperty(access = READ_ONLY)             // klient ska inte skriva id
    private String id;

    @NotBlank(message = "title must not be blank")
    @Size(max = 100, message = "title max 100 chars")
    private String title;

    @Size(max = 1000, message = "description max 1000 chars")
    private String description;

    private boolean completed = false;

    @JsonProperty(access = READ_ONLY)             // sätts från server (ägaren)
    private String owner;

    @JsonProperty(access = READ_ONLY)             // serverstyrda tidsstämplar
    private Instant createdAt = Instant.now();

    @JsonProperty(access = READ_ONLY)
    private Instant updatedAt = Instant.now();

    public Todo() {}

    public Todo(String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
}