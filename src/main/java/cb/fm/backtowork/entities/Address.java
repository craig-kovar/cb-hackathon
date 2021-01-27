package cb.fm.backtowork.entities;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    @Getter @Setter private String zipCode;
    @Getter @Setter private  String country;
    @Getter @Setter private  String streetName;
    @Getter @Setter private  String city;
    @Getter @Setter private String apt;
    @Getter @Setter private String state;
    @Getter @Setter private String phone;
    @Getter @Setter private String organization;
    @Getter @Setter private String name;
    @Getter @Setter private String id;
    @Getter @Setter private String type;

}
