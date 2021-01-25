package cb.fm.backtowork.entities;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization {

    @Getter @Setter private String orgName;

    @Getter @Setter private String parentCompany;

    @Getter @Setter private Address corporateAddress;

}
