package cb.fm.backtowork.entities;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagementResult {

    @Getter @Setter private int employees;
    @Getter @Setter private String officeLocId;

}
