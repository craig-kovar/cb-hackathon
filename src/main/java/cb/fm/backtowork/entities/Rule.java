package cb.fm.backtowork.entities;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rule {

    @Getter @Setter private String field;
    @Getter @Setter private String logic;
    @Getter @Setter private String value;

}
