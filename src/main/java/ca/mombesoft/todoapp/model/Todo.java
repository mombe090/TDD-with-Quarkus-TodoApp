package ca.mombesoft.todoapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Todo {
    private int id;
    private String title;
    private String description;
    private boolean completed;
    private int userId;
}
