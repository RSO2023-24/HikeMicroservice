package si.fri.rso.samples.hikecatalog.api.v1.resources;

import org.json.JSONObject;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import org.json.JSONArray;
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
import si.fri.rso.samples.hikecatalog.lib.HikeMetadataTest;

import java.util.ArrayList;
import java.util.Iterator;
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


    @Operation(description = "Get all hikes.", summary = "Get all hikes")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of hikes",
                    content = @Content(schema = @Schema(implementation = HikeMetadataTest.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getHikes(@QueryParam("lat") double lat, @QueryParam("lon") double lon, @QueryParam("distance") double distance, @QueryParam("difficulty") int difficulty) {

        // Create a list to store the tracks
        List<HikeMetadataTest> hikeMetadataTest = new ArrayList<>();

        // Create a HTTP client to make the API call
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://mapzs.pzs.si/api/search?trailTypes=1&difficulty=" + String.valueOf(difficulty)))
                .build();

        // Send the request and handle the response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(body -> {
                    // Parse the JSON response
                    JSONObject json = new JSONObject(body);
                    JSONArray tracks = json.getJSONArray("tracks");

                    // Loop through the tracks array
                    for (int i = 0; i < tracks.length(); i++) {
                        // Get the track object
                        JSONObject track = tracks.getJSONObject(i);

                        // Create a new HikeMetadata object
                        HikeMetadataTest hike = new HikeMetadataTest();

                        // Set the fields from the track object
                        hike.setName(track.getString("name"));
                        hike.setDifficulty(track.getInt("difficulty"));
                        hike.setLength(track.getDouble("distance")); // Convert meters to kilometers
                        hike.setDuration(track.getDouble("duration")); // Convert minutes to hours

                        // Assign random latitude and longitude values
                        double latitude = Math.random() * (46.8769 - 45.4215) + 45.4215;
                        double longitude = Math.random() * (16.6105 - 13.3753) + 13.3753;
                        hike.setStartingLatitude(latitude);
                        hike.setStartingLongitude(longitude);

                        // Add the hike to the list
                        hikeMetadataTest.add(hike);
                    }
                })
                .join();

        // Loop through the list of hikeMetadata
        Iterator<HikeMetadataTest> iterator = hikeMetadataTest.iterator();
        while (iterator.hasNext()) {
            // Get the current hike
            HikeMetadataTest hike = iterator.next();

            // Calculate the distance between the starting point and the provided point
            double dist = calculateDistance(hike.getStartingLatitude(), hike.getStartingLongitude(), lat, lon);

            // Check if the distance or the difficulty do not match the criteria
            if (dist > distance) {
                // Remove the hike from the list
                iterator.remove();
            }
        }

        // Return the filtered list as a response
        return Response.status(Response.Status.OK).entity(hikeMetadataTest).build();
    }


    // Helper methods for calculating distance and converting degrees to radians
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Radius of the Earth in kilometers
        double dLat = degreesToRadians(lat2 - lat1);
        double dLon = degreesToRadians(lon2 - lon1);

        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(degreesToRadians(lat1)) * Math.cos(degreesToRadians(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // Distance in kilometers

        return distance;
    }

    private double degreesToRadians(double degrees) {
        return degrees * (Math.PI / 180);
    }
}
