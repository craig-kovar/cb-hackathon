package cb.fm.backtowork.repositories;

import cb.fm.backtowork.entities.Employee;
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
public class EmployeeRepository {

    private ConnectionManager connMgr = ConnectionManager.getConnManager();

    public Employee getEmployeeById(String scope, String id) {
        Collection collection = connMgr.getCollection(scope+"-employee");
        try {
            GetResult result = collection.get(id);
            return result.contentAs(Employee.class);
        } catch (DocumentNotFoundException dex) {
            System.out.println("Document Not Found");
        } catch (Exception ex)  {
            System.out.println("Suppressing all other employee get errors");
            ex.printStackTrace();
        }

        return null;
    }

    public void upsertEmployee(String scope, String id, Employee employee) {
        Collection collection = connMgr.getCollection(scope+"-employee");
        try {
            JsonObject jo = JsonObject.fromJson(JsonUtils.toJsonString(employee));
            MutationResult result = collection.upsert(id,jo);
        } catch (Exception ex) {
            System.out.println("Suppressing all employee upsert errors");
            ex.printStackTrace();
        }
    }

    public void deleteEmployee(String scope, String id) {
        Collection collection = connMgr.getCollection(scope+"-employee");
        try {
            collection.remove(id);
        } catch (Exception ex) {
            System.out.println("Suppressing all employee delete errors");
            ex.printStackTrace();
        }
    }

}
