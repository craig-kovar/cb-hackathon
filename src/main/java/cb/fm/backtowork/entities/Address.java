package cb.fm.backtowork.entities;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
