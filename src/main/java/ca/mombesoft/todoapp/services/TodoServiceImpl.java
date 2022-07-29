package ca.mombesoft.todoapp.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

import ca.mombesoft.todoapp.model.Todo;

@ApplicationScoped
public class TodoServiceImpl implements TodoService {
    List<Todo> todos = Stream.of(
            Todo.builder().id(1).title("Learn Quarkus").description("Target befor december").build(),
            Todo.builder().id(2).title("Be istio excellent").description("you have one year to do that").build()
    ).collect(Collectors.toList());


    @Override public Todo getTodo(int id) {
        if (todos.stream().anyMatch(todo -> todo.getId() == id)) {
            return todos.stream().filter(todo -> todo.getId() == id).findFirst().get();
        } else {
            return Todo.builder().id(0).build();
        }
    }

    @Override public List<Todo> getTodos() {
        return todos;
    }

    @Override public Todo addTodo(Todo todo) {
        this.todos.add(todo);
        return todo;
    }

    @Override public Todo updateTodo(Todo todo) {
        if (todos.stream().anyMatch(t -> todo.getId() == t.getId())) {
            todos.stream().filter(t -> todo.getId() == t.getId())
                    .findFirst()
                    .ifPresent(t -> {
                        if (todo.getTitle() != null) {
                            t.setTitle(todo.getTitle());
                        }
                        if (todo.getDescription() != null) {
                            t.setDescription(todo.getDescription());
                        }
                        t.setCompleted(todo.isCompleted());
                    });
            return this.todos.stream().filter(t -> todo.getId() == t.getId()).findFirst().get();
        } else {
            return Todo.builder().id(0).build();
        }
    }

    @Override public boolean deleteTodo(int id) {
        if (todos.stream().anyMatch(todo -> todo.getId() == id)) {
            todos.stream().filter(todo -> todo.getId() == id).findFirst().ifPresent(t -> t.setCompleted(true));
            return true;
        }

        return false;
    }
}