package cb.fm.backtowork.utils;

import com.couchbase.client.core.diagnostics.PingResult;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

    private static ConnectionManager instance = null;
    @Getter
    private static final String cbHost = "ec2-3-236-127-52.compute-1.amazonaws.com";

    @Setter
    private static Cluster cluster = null;

    @Getter
    @Setter
    private static Bucket bucket = null;

    @Getter
    @Setter
    private static Map<String, Collection> scopeMap = null;

    private ConnectionManager() {
        try {
            if (instance == null) {
                cluster = Cluster.connect(cbHost,
                        "Administrator", "mongoose2021");
                bucket = cluster.bucket("BackToWork");
                bucket.waitUntilReady(Duration.ofSeconds(10L));
                scopeMap = new HashMap<>();
                scopeMap.put("Meta-Tenants", bucket.scope("Meta").collection("Tenants"));
                scopeMap.put("Meta-Rules", bucket.scope("Meta").collection("Rules"));
                scopeMap.put("VaccinationCentre-centres", bucket.scope("VaccinationCentre").collection("centres"));
            }
        } catch (Exception ex) {
            System.out.println("Temporarily supressing CB connection error");
        }
    }


    public static ConnectionManager getConnManager() {
        if (instance == null) {
            instance = new ConnectionManager();
        }

        return instance;
    }

    public Collection getCollection(String name) {
        Collection retCollection;
        if (scopeMap.containsKey(name)) {
            retCollection = scopeMap.get(name);
        } else {
            retCollection = bucket.scope(name.split("-")[0]).collection(name.split("-")[1]);
            scopeMap.put(name, retCollection);
        }

        return retCollection;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public boolean isConnected() {

        if (instance != null) {
            PingResult pingResult = bucket.ping();
            if (pingResult.endpoints().size() > 0)
                return true;
        }

        return false;
    }

}
