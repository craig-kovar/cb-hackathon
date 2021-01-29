package cb.fm.backtowork.entities;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.SplittableRandom;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Eligibility {

    @Getter @Setter private boolean isEligible;
    @Getter @Setter private String eligibilityEndDate;
    @Getter @Setter private String eligibilityStartDate;
    @Getter @Setter private String programName;


}
