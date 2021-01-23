package cb.fm.backtowork.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    @Getter @Setter private String lat;
    @Getter @Setter private String lng;

}
