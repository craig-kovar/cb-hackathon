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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/employee")
@Api(value = "Employee REST Endpoint", description = "Manage Employee information")
public class EmployeeController {

    @Autowired
    EmployeeServices employeeServices;

    @Autowired
    RulesetService rulesetService;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Employee retrieved successfully"),
            @ApiResponse(code = 400, message = "Failed to retrieve employee")})
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployeeById(@RequestParam String scope, @RequestParam String employeeId) {
        return employeeServices.getEmployeeById(scope, employeeId);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Employee updated successfully"),
            @ApiResponse(code = 400, message = "Failed to update employee")})
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void upsertEmployee(@RequestParam String scope, @RequestParam String employeeId, @RequestBody Employee employee) {
        employeeServices.upsertEmployee(scope, employeeId, employee);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Employee deleted successfully"),
            @ApiResponse(code = 400, message = "Failed to delete employee")})
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEmployee(@RequestParam String scope, @RequestParam String employeeId) {
        employeeServices.deleteEmployee(scope, employeeId);
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
