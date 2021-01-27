package cb.fm.backtowork.web;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.entities.Rule;
import cb.fm.backtowork.entities.RuleSet;
import cb.fm.backtowork.services.EmployeeServices;
import cb.fm.backtowork.services.RulesetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/employee")
@Api(value = "Employee REST Endpoint", description = "Manage Employee information")
public class EmployeeController {

    @Autowired
    EmployeeServices employeeServices;

    @Autowired
    RulesetService rulesetService;

    private static final String notFound = "{ \"msg\" : \"Document not found\" }";
    private static final String okUpsert = "{ \"msg\" : \"upserted employee successfully\" }";
    private static final String errUpsert = "{ \"msg\" : \"failed to upsert employee successfully\" }";
    private static final String okDelete = "{ \"msg\" : \"deleted employee successfully\" }";
    private static final String errDelete = "{ \"msg\" : \"failed to delete employee successfully\" }";

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
                return rulesetService.processRules(rulez.getRulez(),employee);
            }
        }

        return false;
    }

}
