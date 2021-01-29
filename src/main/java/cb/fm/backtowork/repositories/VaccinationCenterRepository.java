package cb.fm.backtowork.repositories;

import cb.fm.backtowork.entities.Office;
import cb.fm.backtowork.entities.VaccinationCentre;
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
public class VaccinationCenterRepository {

    private ConnectionManager connMgr = ConnectionManager.getConnManager();

    public VaccinationCentre getVCById(String id) {
        Collection collection = connMgr.getCollection("VaccinationCentre-centres");
        try {
            GetResult result = collection.get(id);
            return result.contentAs(VaccinationCentre.class);
        } catch (DocumentNotFoundException dex) {
            System.out.println("Document Not Found");
            VaccinationCentre result = new VaccinationCentre();
            result.setType("NotFound");
            return result;
        } catch (Exception ex)  {
            System.out.println("Suppressing all other office get errors");
            ex.printStackTrace();
        }

        return null;
    }


}
