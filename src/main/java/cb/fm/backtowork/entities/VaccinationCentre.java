package cb.fm.backtowork.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VaccinationCentre {

    @Getter @Setter private Geo geo;
    @Getter @Setter private String name;
    @Getter @Setter private Address address;
    @Getter @Setter private String phoneNumber;
    @Getter @Setter private String id;
    @Getter @Setter private String type;

}
