package cb.fm.backtowork.web;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.services.EmployeeServices;
import cb.fm.backtowork.services.SignUpServices;
import cb.fm.backtowork.utils.CouchbaseUtils;
import com.couchbase.client.java.json.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/signup")
@Api(value = "Signup REST Endpoint", description = "Sign up and register Organizations and Employees")
public class SignUpController {

    @Autowired
    SignUpServices signUpServices;

    @Autowired
    EmployeeServices employeeServices;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Employee registered"),
            @ApiResponse(code = 400, message = "Failed to register employee"),
        @ApiResponse(code = 201, message = "Organization not registered customer.  Please contact your system administrator")
    })
    @RequestMapping(value = "/employee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerEmployee(@RequestParam String scope, @RequestParam String employeeId, @RequestBody Employee employee) {
        if (signUpServices.checkTenant(scope)) {
            employeeServices.upsertEmployee(scope, employeeId, employee);
            return ResponseEntity.status(200).body("{\"msg\" : \"Employee succesfully registered\"}");
        } else {
            return ResponseEntity.status(201).body("{\"msg\" : \"Organization not registered customer.  " +
                    "Please contact your system administrator\"}");
        }

    }


    @ApiResponses(value = {@ApiResponse(code = 200, message = "Organization already registered"),
            @ApiResponse(code = 201, message = "Organization successfully registered"),
            @ApiResponse(code = 400, message = "Failed to register organization"),
            @ApiResponse(code = 500, message = "Failed to connect to Couchbase Cluster")})
    @RequestMapping(value = "/{orgName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerOrg(@PathVariable("orgName") String orgName) {

        JsonObject jo = JsonObject.create();

        if (!signUpServices.checkTenant(orgName)) {

            //Add Tenet document
            signUpServices.registerTenant(orgName);

            //Create Scopes
            int retCode = CouchbaseUtils.createScope(orgName);
            if (retCode != 200) {
                jo.put("msg", "Failed to create scope");
                return ResponseEntity.status(retCode).body(jo.toString());
            }

            retCode = CouchbaseUtils.createCollection(orgName, "employee");
            if (retCode != 200) {
                jo.put("msg", "Failed to create collection employee");
                return ResponseEntity.status(retCode).body(jo.toString());
            }

            retCode = CouchbaseUtils.createCollection(orgName, "office");
            if (retCode != 200) {
                jo.put("msg", "Failed to create collection office");
                return ResponseEntity.status(retCode).body(jo.toString());
            }

            //Create indexes
            CouchbaseUtils.createEmployeeOffice(orgName);

            jo.put("msg", "Greetings " + orgName);
            return ResponseEntity.status(201).body(jo.toString());

        } else {
            jo.put("msg", "Organization already registered");
            return ResponseEntity.ok(jo.toString());
        }
    }

}
