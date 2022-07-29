package ca.mombesoft.todoapp.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import ca.mombesoft.todoapp.model.Todo;

@ApplicationScoped
public interface TodoService {
    public List<Todo> getTodos();

    public Todo getTodo(int id);


    public Todo addTodo(Todo todo);

    public Todo updateTodo(Todo todo);

    public boolean deleteTodo(int id);
}
