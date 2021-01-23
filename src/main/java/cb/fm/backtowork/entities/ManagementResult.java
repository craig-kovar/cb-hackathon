package cb.fm.backtowork.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ManagementResult {

    @Getter @Setter private int employees;
    @Getter @Setter private String officeLocId;

}
