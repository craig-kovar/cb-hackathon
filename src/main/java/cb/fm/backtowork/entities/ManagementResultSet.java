package cb.fm.backtowork.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ManagementResultSet {

    @Getter @Setter private List<ManagementResult> results = new ArrayList<>();
    
}
