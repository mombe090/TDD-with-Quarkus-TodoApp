package ca.mombesoft.todoapp.services;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import ca.mombesoft.todoapp.model.Todo;
import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TodoServiceImplTest {
    //@InjectMocks
    TodoService todoServiceMock;

    @BeforeEach
    public void setUp() {

        todoServiceMock = Mockito.mock(TodoService.class);
        Mockito.when(todoServiceMock.getTodos()).thenReturn(List.of(
                Todo.builder().id(1).title("Learn Quarkus").description("Target befor december").completed(false).build(),
                Todo.builder().id(2).title("Be istio excellent").description("you have one year to do that").completed(false).build(),
                Todo.builder().id(3).title("Relearn java 11").description("yes relearn it from scratch").completed(false).build()
        ));

        Mockito.when(todoServiceMock.getTodo(1)).thenReturn(
                Todo.builder().id(1).title("Learn Quarkus").description("Target befor december").completed(false).build()
        );


    }

    @Test
    public void testMock() {
        assertEquals(3, todoServiceMock.getTodos().size());
    }

    @Test
    void getTodo() {
        assertAll(
                () -> assertEquals(1, todoServiceMock.getTodo(1).getId()),
                () -> assertEquals("Learn Quarkus", todoServiceMock.getTodo(1).getTitle()),
                () -> assertEquals("Target befor december", todoServiceMock.getTodo(1).getDescription())
        );
    }

    @Test
    void getTodos() {
        assert(todoServiceMock.getTodos().size() >= 2);
    }

    @Test
    void addTodo() {
        Todo todo = Todo.builder()
                .id(4)
                .title("Watch the new movie")
                .description("Thor: Ragnarok")
                .completed(false)
                .build();

        Mockito.when(todoServiceMock.addTodo(todo))
                .thenReturn(todo);



        assertEquals(todoServiceMock.addTodo(todo), todo);
    }

    @Test
    void updateTodo() {
        Mockito.when(todoServiceMock.updateTodo(this.todoServiceMock.getTodo(1)))
                .thenReturn(Todo.builder().id(1).title("Learn Quarkus").build());
        ;

        assertEquals("Learn Quarkus", this.todoServiceMock.getTodo(1).getTitle());
    }

    @Test
    void deleteTodo() {
        Mockito.when(todoServiceMock.deleteTodo(3))
                .thenReturn(true);
        assertTrue(todoServiceMock.deleteTodo(3));
    }
}