package cb.fm.backtowork.services;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.entities.Organization;
import cb.fm.backtowork.utils.ConnectionManager;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class SignUpServices {

    ConnectionManager connMgr = ConnectionManager.getConnManager();

    public boolean checkTenant(String name) {
        Collection collection = connMgr.getCollection("Meta-Tenants");

        try {
            GetResult result = collection.get(name.replace(" ","").toUpperCase());
            return true;
        } catch (DocumentNotFoundException ex) {
            return false;
        }
    }

    public void registerTenant(String name) {
        Organization org = new Organization();
        org.setOrgName(name);

        JsonObject jo = JsonObject.create().put("orgName", name);

        Collection collection = connMgr.getCollection("Meta-Tenants");
        MutationResult result = collection.upsert(name.replace(" ","").toUpperCase(), jo);
    }

}
