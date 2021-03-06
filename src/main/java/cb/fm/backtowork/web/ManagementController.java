package cb.fm.backtowork.web;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.entities.ManagementListResult;
import cb.fm.backtowork.entities.ManagementResultSet;
import cb.fm.backtowork.entities.RuleSet;
import cb.fm.backtowork.services.EmployeeServices;
import cb.fm.backtowork.services.ManagementServices;
import cb.fm.backtowork.services.OfficeServices;
import cb.fm.backtowork.services.RulesetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/management")
@Api(value = "Management REST Endpoint", description = "Manage office and employee information. Time to tell them to GET BACK TO WORK")
public class ManagementController {

    @Autowired
    ManagementServices managementServices;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieved Employees per Office"),
            @ApiResponse(code = 400, message = "Failed to retrieve employees per office")
    })
    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManagementResultSet> employeesPerStores(@RequestParam String scope) {
       return ResponseEntity.ok().body(managementServices.getNumEmployeesPerOffice(scope));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieved Employees per Office"),
            @ApiResponse(code = 400, message = "Failed to retrieve employees per office")
    })
    @RequestMapping(value = "/employees/eligible", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManagementResultSet> eligibleEmployeesPerStores(@RequestParam String scope) {
        return ResponseEntity.ok().body(managementServices.getEligibleEmployeesPerOffice(scope));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieved Eligible Employees for all offices"),
            @ApiResponse(code = 400, message = "Failed to retrieve eligible employees at all office")
    })
    @RequestMapping(value = "/employees/{storeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManagementResultSet> employeeAtStores(@RequestParam String scope, @PathVariable("storeId") String storeId) {
        return ResponseEntity.ok().body(managementServices.getNumEmployeesAtOffice(scope,storeId));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieved Eligible Employees at Office"),
            @ApiResponse(code = 400, message = "Failed to retrieve eligible employees at office")
    })
    @RequestMapping(value = "/employees/eligible/{storeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManagementResultSet> elgibleEmployeeAtStores(@RequestParam String scope, @PathVariable("storeId") String storeId) {
        return ResponseEntity.ok().body(managementServices.getNumEligibleEmployeesAtOffice(scope,storeId));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieved Vaccinated Employees per Office"),
            @ApiResponse(code = 400, message = "Failed to retrieve vaccinated employees per office")
    })
    @RequestMapping(value = "/employees/vaccinated", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManagementResultSet> vaccEmployeesPerStores(@RequestParam String scope, @RequestParam int numDoses) {
        return ResponseEntity.ok().body(managementServices.getVaccinatedEmployeesPerOffice(scope,numDoses));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieved Eligible Employees at Office"),
            @ApiResponse(code = 400, message = "Failed to retrieve eligible employees at office")
    })
    @RequestMapping(value = "/employees/vaccinated/{storeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManagementResultSet> vaccEmployeeAtStores(@RequestParam String scope, @PathVariable("storeId") String storeId, @RequestParam int numDoses) {
        return ResponseEntity.ok().body(managementServices.getVaccinatedEmployeesAtOffice(scope,storeId, numDoses));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieved Office Summary"),
            @ApiResponse(code = 400, message = "Failed to retrieve office summary")
    })
    @RequestMapping(value = "/offices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManagementResultSet> officesSummary(@RequestParam String scope) {
        return ResponseEntity.ok().body(managementServices.getOfficesSummary(scope));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieved Office Summary"),
            @ApiResponse(code = 400, message = "Failed to retrieve office summary")
    })
    @RequestMapping(value = "/offices/{officeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManagementResultSet> officesSummary(@RequestParam String scope, @PathVariable("officeId") String officeId) {
        return ResponseEntity.ok().body(managementServices.getOfficeSummary(scope,officeId));
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Retrieved Employee List for office"),
            @ApiResponse(code = 400, message = "Failed to retrieve Employee List for office")
    })
    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManagementListResult> employeeList(@RequestParam("scope") String scope,
                                                             @RequestParam("officeId") String officeId,
                                                             @RequestParam(required = false) String limit,
                                                             @RequestParam(required = false) String offset) {
        return ResponseEntity.ok().body(managementServices.getEmployeeList(scope,officeId, limit, offset));
    }

}
