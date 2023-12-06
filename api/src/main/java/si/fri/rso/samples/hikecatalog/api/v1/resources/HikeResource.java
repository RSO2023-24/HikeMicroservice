package si.fri.rso.samples.hikecatalog.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.samples.hikecatalog.lib.HikeMetadata;
import si.fri.rso.samples.hikecatalog.services.beans.HikeBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;



@ApplicationScoped
@Path("/hikes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HikeResource {

    private Logger log = Logger.getLogger(HikeResource.class.getName());

    @Inject
    private HikeBean hikeBean;



    @Operation(description = "Get all hikes.", summary = "Get all hikes")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of hikes",
                    content = @Content(schema = @Schema(implementation = HikeMetadata.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getHikes() {

        List<HikeMetadata> hikeMetadata = hikeBean.getHikeMetadata();
        System.out.println("Hike Metadata: " + hikeMetadata);

        return Response.status(Response.Status.OK).entity(hikeMetadata).build();
    }


    @Operation(description = "Get hike.", summary = "Get hike")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Hike",
                    content = @Content(
                            schema = @Schema(implementation = HikeMetadata.class))
            )})
    @GET
    @Path("/{hikeId}")
    public Response getImageMetadata(@Parameter(description = "Hike ID.", required = true)
                                     @PathParam("hikeId") Integer hikeId) {

        HikeMetadata hike = hikeBean.getHikeMetaData(hikeId);

        if (hike == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(hike).build();
    }

    @Operation(description = "Add hike.", summary = "Add hike")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Hike successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createHike(@RequestBody(
            description = "DTO object with hike.",
            required = true, content = @Content(
            schema = @Schema(implementation = HikeMetadata.class))) HikeMetadata hikeMetadata) {

        if ((hikeMetadata.getDifficulty() == null || hikeMetadata.getLength() == null || hikeMetadata.getStartingLatitude() == null || hikeMetadata.getStartingLongitude() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            hikeMetadata = hikeBean.createHikeMetadata(hikeMetadata);
        }

        return Response.status(Response.Status.CONFLICT).entity(hikeMetadata).build();

    }


    @Operation(description = "Update hike.", summary = "Update hike")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Hike successfully updated."
            )
    })
    @PUT
    @Path("{hikeId}")
    public Response putHike(@Parameter(description = "Hike ID.", required = true)
                                     @PathParam("hikeId") Integer hikeId,
                                     @RequestBody(
                                             description = "DTO object with hike metadata.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = HikeMetadata.class)))
                                             HikeMetadata hikeMetadata){

        hikeMetadata = hikeBean.putHikeMetadata(hikeId, hikeMetadata);

        if (hikeMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete hike.", summary = "Delete hike")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{hikeId}")
    public Response deleteHike(@Parameter(description = "Hike ID.", required = true)
                                        @PathParam("hikeId") Integer hikeId){

        boolean deleted = hikeBean.deleteHikeMetadata(hikeId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
