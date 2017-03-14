package br.com.g4labs.route;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tasks")
public class Tasks {

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public br.com.g4labs.model.Task task(@PathParam("id") Integer id) {
		br.com.g4labs.controller.Task controller = new br.com.g4labs.controller.Task();
		return controller.getTask(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<br.com.g4labs.model.Task> tasks() {
		br.com.g4labs.controller.Task controller = new br.com.g4labs.controller.Task();
		return controller.getTasks();

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(br.com.g4labs.model.Task track) {
		String result = "Track saved : " + track;
		return Response.status(201).entity(result).build();

	}

}