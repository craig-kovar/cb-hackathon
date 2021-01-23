package cb.fm.backtowork.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RuleSet {

    @Getter @Setter private List<Rule> rulez = new ArrayList<>();

}
