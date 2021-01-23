package cb.fm.backtowork.entities;

import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Getter @Setter private String orgName;

    @Getter @Setter private String parentCompany;

    @Getter @Setter private Address corporateAddress;

}
