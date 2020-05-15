package de.cloudbug.humanoid.profile;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/v1/profile")
@Produces(APPLICATION_JSON)
public class ProfileApi {

    private static final Logger LOGGER = Logger.getLogger(ProfileApi.class);

    @Inject
    ProfileService profileService;

    @Operation(summary = "Returns a profile for a given identifier")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Profile.class)))
    @APIResponse(responseCode = "204", description = "The profile is not found for a given identifier")
    @GET
    @Path("/{id}")
    public Response findProfileById(@PathParam("id") ObjectId id) {
        Profile profile = profileService.findProfileById(id);

        if (profile != null) {
            return Response.ok(profile).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    public Response findProfileByEmployeeId(@QueryParam("employeeid") String employeeId) {
        Profile profile = profileService.findProfileByEmployeeId(employeeId);

        if (profile != null) {
            return Response.ok(profile).build();
        } else {
            return Response.noContent().build();
        }
    }

    @Operation(summary = "Returns all the profiles from the database")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Profile.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No profiles yet")
    @GET
    public Response getAllProfiles() {
        List<Profile> profiles = profileService.findAllProfiles();
        return Response.ok(profiles).build();
    }

    @Operation(summary = "Creates a valid profile")
    @APIResponse(responseCode = "201", description = "The URI of the created profile", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
    @POST
    @Counted(name = "countInsertProfile", description = "Counts how many times the addProfile method has been invoked")
    @Timed(name = "timeInsertProfile", description = "Times how long it takes to invoke the addProfile method", unit = MetricUnits.MILLISECONDS)
    public Response addProfile(@Valid Profile profile, @Context UriInfo uriInfo) {
        profile = profileService.persistProfile(profile);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(String.valueOf(profile.id));

        return Response.created(builder.build()).build();
    }

    @Operation(summary = "Updates an existing profile")
    @APIResponse(responseCode = "200", description = "The updated profile", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Profile.class)))
    @PUT
    public Response updateProfile(@Valid Profile profile) {
        profile = profileService.updateProfile(profile);

        return Response.ok(profile).build();
    }

    @Operation(summary = "Deletes an existing profile")
    @APIResponse(responseCode = "204")
    @DELETE
    @Path("/{id}")
    public Response deleteProfile(@PathParam("id") ObjectId id) {
        profileService.deleteProfile(id);

        return Response.noContent().build();
    }

    public String healthCheck() {
        return "hello";
    }

}