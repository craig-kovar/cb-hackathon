package cb.fm.backtowork.utils;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.entities.Geo;
import cb.fm.backtowork.entities.GeoResult;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.search.SearchOptions;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.result.SearchResult;
import com.couchbase.client.java.search.result.SearchRow;
import com.couchbase.client.java.search.sort.SearchSort;
import com.couchbase.client.java.util.Coordinate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.In;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class GeoUtil {

    private static Cluster cluster = ConnectionManager.getConnManager().getCluster();

    public static GeoResult geoCodeZip(String zipCode) {

        GeoResult result = new GeoResult();

        try {
            ResteasyClient restClient = (ResteasyClient) ResteasyClientBuilder.newBuilder().build();
            ResteasyWebTarget target = restClient.target(new URI("https://geocode.xyz/"+zipCode+"?json=1"));
            String jsonString = target.request().get().readEntity(String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonString);

            String lon = "0";
            String lat = "0";
            if (jsonNode.has("longt"))
                lon = jsonNode.get("longt").asText();
            if (jsonNode.has("latt"))
                lat = jsonNode.get("latt").asText();

            result.setLon(lon);
            result.setLat(lat);

            restClient.close();

        } catch (URISyntaxException mEx) {
            mEx.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;

    }

    public static SearchResult doGeoSearchZipcode(String zipCode) {
        return null;
    }

    public static SearchResult doGeoSearchEmployee(Employee employee, String distance) {
        SearchResult results = null;
        Geo geo = null;
        if ((geo = employee.getGeo()) != null) {
            //System.out.println("Doing geo search");
            results = cluster.searchQuery("vaccGeo",
                    SearchQuery.geoDistance(geo.getLon(), geo.getLat(), distance),
                    SearchOptions.searchOptions().limit(10).sort(SearchSort.byGeoDistance(geo.getLon(),
                            geo.getLat(), distance ))
            );
        }

        return results;
    }

}
