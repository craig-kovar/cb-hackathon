package cb.fm.backtowork.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Rule {

    @Getter @Setter private String field;
    @Getter @Setter private String logic;
    @Getter @Setter private String value;

}
