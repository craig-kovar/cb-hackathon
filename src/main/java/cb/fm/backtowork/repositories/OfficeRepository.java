package cb.fm.backtowork.repositories;

import cb.fm.backtowork.entities.Office;
import cb.fm.backtowork.utils.ConnectionManager;
import cb.fm.backtowork.utils.JsonUtils;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Component
public class OfficeRepository {

    private ConnectionManager connMgr = ConnectionManager.getConnManager();

    public Office getOfficeById(String scope, String id) {
        Collection collection = connMgr.getCollection(scope+"-office");
        try {
            GetResult result = collection.get(id);
            return result.contentAs(Office.class);
        } catch (DocumentNotFoundException dex) {
            System.out.println("Document Not Found");
            Office result = new Office();
            result.setType("NotFound");
            return result;
        } catch (Exception ex)  {
            System.out.println("Suppressing all other office get errors");
            ex.printStackTrace();
        }

        return null;
    }

    public boolean upsertOffice(String scope, String id, Office office) {
        Collection collection = connMgr.getCollection(scope+"-office");
        try {
            JsonObject jo = JsonObject.fromJson(JsonUtils.toJsonString(office));
            MutationResult result = collection.upsert(id,jo);
        } catch (Exception ex) {
            System.out.println("Suppressing all office upsert errors");
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteOffice(String scope, String id) {
        Collection collection = connMgr.getCollection(scope+"-office");
        try {
            collection.remove(id);
        } catch (Exception ex) {
            System.out.println("Suppressing all office delete errors");
            ex.printStackTrace();
            return false;
        }

        return true;
    }

}
