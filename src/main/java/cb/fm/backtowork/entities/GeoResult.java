package cb.fm.backtowork.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.lang.Nullable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeoResult {

   @Getter @Setter private String lat = "0";
   @Getter @Setter private String lon = "0";
   @Getter @Setter private String msg;

}
