package cb.fm.backtowork.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Getter @Setter private String dateOfBirth;
    @Getter @Setter private String type;
    @Getter @Setter private String ssn;
    @Getter @Setter private String firstName;
    @Getter @Setter private boolean isPrimary;
    @Getter @Setter private String relationship;
    @Getter @Setter private int age;
    @Getter @Setter private String lastName;
    @Getter @Setter private String emailId;
    @Getter @Setter private String userName;
    @Getter @Setter private String password;
    @Getter @Setter private Address address;
    @Getter @Setter private Geo geo;
    @Getter @Setter private String ethnicity;
    @Getter @Setter private String gender;
    @Getter @Setter private Eligibility eligibility;
    @Getter @Setter private String officeLocId;


    public Map<String,Object> toMap() {
        Map<String,Object> retMap = new HashMap<String,Object>();
        if (dateOfBirth != null)
            retMap.put("dateOfBirth",dateOfBirth);

        if (type != null)
            retMap.put("type",type);

        if (ssn != null)
            retMap.put("ssn",ssn);

        if (firstName != null)
            retMap.put("firstName",firstName);

        retMap.put("isPrimary",isPrimary);

        if (relationship != null)
            retMap.put("relationship",relationship);

        retMap.put("age",age);

        if (lastName != null)
            retMap.put("lastName",lastName);

        if (emailId != null)
            retMap.put("emailId",emailId);

        if (userName != null)
            retMap.put("userName",userName);

        if (password != null)
            retMap.put("password",password);

        if (address != null)
            retMap.put("address",address);

        if (geo != null)
            retMap.put("geo",geo);

        if (ethnicity != null)
            retMap.put("ethnicity",ethnicity);

        if (gender != null)
            retMap.put("gender",gender);

        if (eligibility != null)
            retMap.put("eligibility",eligibility);

        if (officeLocId != null)
            retMap.put("officeLocId", officeLocId);


        return retMap;
    }


}
