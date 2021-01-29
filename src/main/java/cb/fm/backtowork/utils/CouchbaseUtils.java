package cb.fm.backtowork.utils;

import cb.fm.backtowork.entities.Employee;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.search.result.SearchResult;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.couchbase.client.java.query.QueryOptions.queryOptions;

public class CouchbaseUtils {

    private static String user = "Administrator";
    private static String password = "mongoose2021";
    private static ConnectionManager connectionManager = ConnectionManager.getConnManager();

    public static int createScope(String scopeName) {
        int responseCode = 400;

        if (ConnectionManager.getConnManager().isConnected()) {
            try {
                URL obj = new URL("http://" + ConnectionManager.getCbHost() +
                        ":8091/pools/default/buckets/BackToWork/collections");

                HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
                postConnection.setRequestMethod("POST");
                //postConnection.setRequestProperty("Content-Type", "application/json");
                postConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String auth = user + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
                String authHeaderValue = "Basic " + new String(encodedAuth);
                postConnection.setRequestProperty("Authorization", authHeaderValue);

                postConnection.setDoOutput(true);
                OutputStream os = postConnection.getOutputStream();
                String postBody = "name="+scopeName;
                os.write(postBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                responseCode = postConnection.getResponseCode();

                if (responseCode != 200) {
                    System.out.println("Failed to create scope");
                }

            } catch (IOException mEx) {
                mEx.printStackTrace();
            }
        } else {
            return 500;
        }

        return responseCode;
    }

    public static int createCollection(String scopeName, String collectionName) {
        int responseCode = 400;

        if (ConnectionManager.getConnManager().isConnected()) {
            try {
                URL obj = new URL("http://" + ConnectionManager.getCbHost() +
                        ":8091/pools/default/buckets/BackToWork/collections/"+scopeName);

                HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
                postConnection.setRequestMethod("POST");
                //postConnection.setRequestProperty("Content-Type", "application/json");
                postConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String auth = user + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
                String authHeaderValue = "Basic " + new String(encodedAuth);
                postConnection.setRequestProperty("Authorization", authHeaderValue);

                postConnection.setDoOutput(true);
                OutputStream os = postConnection.getOutputStream();
                String postBody = "name="+collectionName;
                os.write(postBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                responseCode = postConnection.getResponseCode();

                if (responseCode != 200) {
                    System.out.println("Failed to create scope");
                }

            } catch (IOException iEx) {
                iEx.printStackTrace();
            }
        } else {
            return 500;
        }
        return responseCode;
    }

    public static void createEmployeeOffice(String scopeName) {
        Cluster cluster = connectionManager.getCluster();
        if (cluster != null) {
            String statement = String.format("create index %s on %s",scopeName+"EmplOffice", "BackToWork."+scopeName+".employee(officeLocId)");
            //System.out.println("Statement = " + statement);
            QueryResult result = cluster.query(statement);
        }
    }

    public static void createEmployeeVaccination(String scopeName) {
        //CREATE INDEX walmart_vaccinated ON BackToWork.Walmart.employee(ARRAY_LENGTH(vaccinationDetails))
        //WHERE vaccinationDetails IS NOT MISSING
        Cluster cluster = connectionManager.getCluster();
        if (cluster != null) {
            String statement = String.format("create index %s on %s", scopeName + "Vaccinations",
                    "BackToWork." + scopeName + ".employee(ARRAY_LENGTH(vaccinationDetails)) "
                            + "WHERE vaccinationDetails IS NOT MISSING");
            //System.out.println("Statement = " + statement);
            QueryResult result = cluster.query(statement);
        }
    }

}
