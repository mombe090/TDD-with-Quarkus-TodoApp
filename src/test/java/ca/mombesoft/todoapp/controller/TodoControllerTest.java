package ca.mombesoft.todoapp.controller;

import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ca.mombesoft.todoapp.model.Todo;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class TodoControllerTest {
    @TestHTTPResource("/todos")
    URL todoUrl;

    @BeforeEach
    void setUp() {
        List<Todo> todos = List.of(
                Todo.builder().id(1).title("Learn Quarkus").description("Target befor december").completed(false).build(),
                Todo.builder().id(2).title("Be istio excellent").description("you have one year to do that").completed(false).build(),
                Todo.builder().id(3).title("Relearn java 11").description("yes relearn it from scratch").completed(false).build()
        );
        // Mockito.when()
    }

    @Test
    @DisplayName("Get all todos")
    public void testGetTodos() {
        given()
                .when().get(todoUrl)
                .then()
                .statusCode(200)
                .body(
                        "[0].id", is(1),
                        "[0].title", is("Learn Quarkus"),
                        "[0].description", is("Target befor december"),
                        "[1].completed", is(false)
                );
    }

    @Test
    @DisplayName("Get a todo by id")
    public void testGetTodo() {
        given()
                .when().get(todoUrl + "/1")
                .then()
                .statusCode(200)
                .body(
                        "id", is(1),
                        "title", is("Learn Quarkus"),
                        "description", is("Target befor december"),
                        "completed", is(false)
                );

        given()
                .when().get(todoUrl + "/2")
                .then()
                .statusCode(200)
                .body(
                        "id", is(2),
                        "title", is("Be istio excellent"),
                        "description", is("you have one year to do that"),
                        "completed", is(false)
                );
    }

    @Test
    @DisplayName("Get unexisting todo")
    public void testGetTodoNotFound() {
        given()
                .when().get(todoUrl + "/30")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Create new todo")
    public void testAddNewTodo() {
        int id = 3;

        Todo todo = Todo.builder().id(id).title("Learn Valero").description("Target befor November").build();
        given()
                .header("Content-Type", "application/json")
                .body(todo)
                .when().post("/todos")
                .then()
                .statusCode(201);

        given()
                .when().get(todoUrl + "/" + id)
                .then()
                .statusCode(200)
                .body(
                        "id", is(3),
                        "title", is("Learn Valero"),
                        "description", is("Target befor November"),
                        "completed", is(false)
                );
    }

    @Test
    @DisplayName("Terminate a todo by id")
    public void testTerminateTodo() {
        given()
                .when().delete(todoUrl + "/1")
                .then()
                .statusCode(204);

        given()
                .when().get(todoUrl + "/1")
                .then()
                .statusCode(200)
                .body(
                        "completed", is(true)
                );
    }

    @Test
    @DisplayName("Update a todo by id")
    void updateTodoById() {
        Todo todo = Todo.builder().id(1).title("Learn Quarkus II").description("Target befor december").completed(false).build();
        given()
                .header("Content-Type", "application/json")
                .body(todo)
                .when().put(todoUrl + "/1")
                .then()
                .statusCode(204);

        given()
                .when().get(todoUrl + "/1")
                .then()
                .statusCode(200)
                .body(
                        "id", is(1),
                        "title", is("Learn Quarkus II"),
                        "description", is("Target befor december"),
                        "completed", is(false)
                );
    }
}
