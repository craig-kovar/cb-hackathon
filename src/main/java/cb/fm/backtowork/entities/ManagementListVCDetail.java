package cb.fm.backtowork.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManagementListVCDetail {

    @Getter @Setter
    String email;

    @Getter @Setter
    int numDoses;
}
