package cb.fm.backtowork.web;

import cb.fm.backtowork.entities.Employee;
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
    @RequestMapping(value = "/employees/{storeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManagementResultSet> employeeAtStores(@RequestParam String scope, @PathVariable("storeId") String storeId) {
        return ResponseEntity.ok().body(managementServices.getNumEmployeesAtOffice(scope,storeId));
    }



}
