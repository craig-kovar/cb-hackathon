package cb.fm.backtowork.utils.utils;

import com.couchbase.client.core.diagnostics.PingResult;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Scope;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

    private static ConnectionManager instance = null;

    @Getter
    @Setter
    private static Cluster cluster = null;

    @Getter
    @Setter
    private static Bucket bucket = null;

    @Getter
    @Setter
    private static Map<String, Scope> scopeMap = null;

    private ConnectionManager() {
        if (instance == null) {
            cluster = Cluster.connect("ec2-3-236-127-52.compute-1.amazonaws.com",
                    "Administrator", "mongoose2021");
            bucket = cluster.bucket("BackToWork");
            bucket.waitUntilReady(Duration.ofSeconds(10L));
            scopeMap = new HashMap<>();
        }
    }


    public static ConnectionManager getConnManager() {
        if (instance == null) {
            instance = new ConnectionManager();
        }

        return instance;
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
