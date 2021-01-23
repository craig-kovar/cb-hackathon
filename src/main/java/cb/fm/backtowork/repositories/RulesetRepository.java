package cb.fm.backtowork.repositories;

import cb.fm.backtowork.entities.Office;
import cb.fm.backtowork.entities.RuleSet;
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
public class RulesetRepository {

    private ConnectionManager connMgr = ConnectionManager.getConnManager();

    public RuleSet getRuleSet(String state) {
        Collection collection = connMgr.getCollection("Meta-Rules");
        try {
            GetResult result = collection.get(state.toUpperCase());
            return result.contentAs(RuleSet.class);
        } catch (DocumentNotFoundException dex) {
            System.out.println("Document Not Found");
        } catch (Exception ex)  {
            System.out.println("Suppressing all other ruleset get errors");
            ex.printStackTrace();
        }

        return null;
    }

}
