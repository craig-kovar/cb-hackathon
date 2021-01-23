package cb.fm.backtowork.entities;

import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Getter @Setter private String zipCode;
    @Getter @Setter private  String country;
    @Getter @Setter private  String streetName;
    @Getter @Setter private  String city;
    @Getter @Setter private String apartmentNumber;
    @Getter @Setter private String state;

}
