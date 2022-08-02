package ca.mombesoft.todoapp.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ca.mombesoft.todoapp.model.Todo;
import ca.mombesoft.todoapp.services.TodoServiceImpl;

@Produces("application/json")
@Consumes("application/json")
@Path("/todos")
public class TodoController {
    @Inject
    TodoServiceImpl todoService;

    @GET
    public Response getTodos() {
        return Response.ok(todoService.getTodos()).build();
    }

    @GET
    @Path("/{id}")
    public Response getTodo(@PathParam("id") int id) {
        Todo todo = todoService.getTodo(id);
        if (todo.getId() != 0) {
            return Response.ok(todo).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addTodo(Todo todo) {
        this.todoService.addTodo(todo);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateTod(Todo todo) {
        if (todoService.updateTodo(todo).getId() != 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response terminateTodo(@PathParam("id") int id) {
        if (todoService.deleteTodo(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
