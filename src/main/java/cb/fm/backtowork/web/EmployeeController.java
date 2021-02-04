package cb.fm.backtowork.web;

import cb.fm.backtowork.entities.*;
import cb.fm.backtowork.services.EmployeeServices;
import cb.fm.backtowork.services.RulesetService;
import cb.fm.backtowork.services.VaccinationCentreService;
import cb.fm.backtowork.utils.GeoUtil;
import com.couchbase.client.java.search.result.SearchResult;
import com.couchbase.client.java.search.result.SearchRow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/employee")
@Api(value = "Employee REST Endpoint", description = "Manage Employee information")
public class EmployeeController {

    @Autowired
    EmployeeServices employeeServices;

    @Autowired
    VaccinationCentreService vaccinationCentreService;

    @Autowired
    RulesetService rulesetService;

    private static final String notFound = "{ \"msg\" : \"Document not found\" }";
    private static final String okUpsert = "{ \"msg\" : \"upserted employee successfully\" }";
    private static final String errUpsert = "{ \"msg\" : \"failed to upsert employee successfully\" }";
    private static final String okDelete = "{ \"msg\" : \"deleted employee successfully\" }";
    private static final String errDelete = "{ \"msg\" : \"failed to delete employee successfully\" }";

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Login status returned")})
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> login(@RequestParam String scope, @RequestParam String employeeId, @RequestParam String password) {
       Boolean status = employeeServices.login(scope, employeeId, password);
       return ResponseEntity.ok(status);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Employee retrieved successfully"),
            @ApiResponse(code = 400, message = "Failed to retrieve employee")})
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getEmployeeById(@RequestParam String scope, @RequestParam String employeeId) {
        Employee result = employeeServices.getEmployeeById(scope, employeeId);
        if (result != null && result.getType().equalsIgnoreCase("NotFound")) {
            return ResponseEntity.status(400).body(null);
        } else if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(500).body(null);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Employee updated successfully"),
            @ApiResponse(code = 400, message = "Failed to update employee")})
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String>upsertEmployee(@RequestParam String scope, @RequestParam String employeeId, @RequestBody Employee employee) {
        if (employeeServices.upsertEmployee(scope, employeeId, employee)) {
            return ResponseEntity.ok(okUpsert);
        }

        return ResponseEntity.badRequest().body(errUpsert);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Employee deleted successfully"),
            @ApiResponse(code = 400, message = "Failed to delete employee")})
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteEmployee(@RequestParam String scope, @RequestParam String employeeId) {
        if (employeeServices.deleteEmployee(scope, employeeId)) {
            return ResponseEntity.ok(okDelete);
        }

        return ResponseEntity.badRequest().body(errDelete);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Employee eligibility checked successfully"),
            @ApiResponse(code = 400, message = "Failed to check eligibility employee")})
    @RequestMapping(value = "/eligibility", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean eligibiltiyCheck(@RequestParam String scope, @RequestParam String employeeId) {
        Employee employee = employeeServices.getEmployeeById(scope, employeeId);
        if (employee != null) {
            String state = employee.getAddress().getState();
            if (state != null) {
                RuleSet rulez = rulesetService.getRuleSet(state);
                if (rulez != null)
                    return rulesetService.processRules(rulez.getRulez(),employee);
                else
                    return false;
            }
        }

        return false;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Employee vaccination updated successfully"),
            @ApiResponse(code = 400, message = "Failed to updated vaccination info")})
    @RequestMapping(value = "/vc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> vcUpdate(@RequestParam String scope, @RequestParam String employeeId, @RequestParam String date) {
       boolean status = employeeServices.updateVacStatus(scope, employeeId, date);
       if (status)
           return ResponseEntity.ok("{\"msg\": \"Vaccination Status updated succesfully\"}");

       return ResponseEntity.badRequest().body("{\"msg\": \"Failed to update Vaccination Status\"}");
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved GeoCode"),
            @ApiResponse(code = 400, message = "Failed to retrieve GeoCode")})
    @RequestMapping(value = "/geocode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeoResult> geoCodeZip(@RequestParam String zipCode) {
        GeoResult result = GeoUtil.geoCodeZip(zipCode);
        if (result != null) {
            if (result.getLat().equals("0") && result.getLat().equals("0")) {
                result.setMsg("Failed to retrieve GeoCode");
                ResponseEntity.badRequest().body(result);
            }
        }

        return ResponseEntity.ok(result);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved Vaccination Centers by Employee"),
            @ApiResponse(code = 400, message = "Failed to retrieve Vaccination Centers by Employee")})
    @RequestMapping(value = "/geo/employee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VaccinationCentre>> findVCByEmployee(@RequestParam String distance, @RequestBody Employee employee) {

        SearchResult results = GeoUtil.doGeoSearchEmployee(employee, distance);

        if (results != null) {
            List<VaccinationCentre> centres = new ArrayList<>();
            for (SearchRow row : results.rows()) {
                centres.add(vaccinationCentreService.getVCById(row.id()));
            }

            return ResponseEntity.ok(centres);
        }

        return ResponseEntity.badRequest().body(null);

    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved Vaccination Centers by Employee Id"),
            @ApiResponse(code = 400, message = "Failed to retrieve Vaccination Centers by Employee Id")})
    @RequestMapping(value = "/geo/id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VaccinationCentre>> findVCByEmployeeId(@RequestParam String distance, @RequestParam String scope, @RequestParam String id) {

        Employee employee = employeeServices.getEmployeeById(scope, id);
        if (employee != null) {
            SearchResult results = GeoUtil.doGeoSearchEmployee(employee, distance);

            if (results != null) {
                List<VaccinationCentre> centres = new ArrayList<>();
                for (SearchRow row : results.rows()) {
                    centres.add(vaccinationCentreService.getVCById(row.id()));
                }

                return ResponseEntity.ok(centres);
            }
        }

        return ResponseEntity.badRequest().body(null);

    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved Vaccination Centers by zipcode"),
            @ApiResponse(code = 400, message = "Failed to retrieve Vaccination Centers by zipcode")})
    @RequestMapping(value = "/geo/zipcode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VaccinationCentre>> findVCByZipCode(@RequestParam String distance, @RequestParam String zipCode) {

        GeoResult geoResult = GeoUtil.geoCodeZip(zipCode);
        if (geoResult.getMsg() == null) {

            Employee employee = new Employee();
            Geo tmpGeo = new Geo();
            tmpGeo.setLat(Double.parseDouble(geoResult.getLat()));
            tmpGeo.setLon(Double.parseDouble(geoResult.getLon()));
            employee.setGeo(tmpGeo);

            SearchResult results = GeoUtil.doGeoSearchEmployee(employee, distance);

            if (results != null) {
                List<VaccinationCentre> centres = new ArrayList<>();
                for (SearchRow row : results.rows()) {
                    centres.add(vaccinationCentreService.getVCById(row.id()));
                }

                return ResponseEntity.ok(centres);
            }
        }

        return ResponseEntity.badRequest().body(null);

    }

}
