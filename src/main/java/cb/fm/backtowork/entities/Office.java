package cb.fm.backtowork.entities;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Office {

    @Getter @Setter private String name;
    @Getter @Setter private Address address;
    @Getter @Setter private Geo geo;
    @Getter @Setter private String phone;
    @Getter @Setter private String type;
    @Getter @Setter private String id;
    @Getter @Setter private String organization;

    public Map<String,Object> toMap() {
        Map<String,Object> retMap = new HashMap<>();

        if (name != null)
            retMap.put("name",name);

        if (address != null)
            retMap.put("address", address);

        if (geo != null)
            retMap.put("geo",geo);

        if (phone != null)
            retMap.put("phone", phone);

        if (type != null)
            retMap.put("type", type);

        return retMap;
    }

}
