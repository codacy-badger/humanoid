package de.cloudbug.humanoid.profile.client;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/project")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface ProjectService {

    @GET
    @Path("/{id}")
    List<Project> getProjectsById(@PathParam("id") ObjectId profileId);
}
