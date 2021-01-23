package cb.fm.backtowork.web;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.entities.Office;
import cb.fm.backtowork.services.EmployeeServices;
import cb.fm.backtowork.services.OfficeServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/office")
@SuppressWarnings("deprecated")
@Api(value = "Office REST Endpoint", description = "Manage Office information")
public class OfficeController {

    @Autowired
    OfficeServices officeServices;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Office retrieved successfully"),
            @ApiResponse(code = 400, message = "Failed to retrieve office")})
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Office getOfficeById(@RequestParam String scope, @RequestParam String officeId) {
        return officeServices.getOfficeById(scope, officeId);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Office updated successfully"),
            @ApiResponse(code = 400, message = "Failed to update office")})
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void upsertOffice(@RequestParam String scope, @RequestParam String officeId, @RequestBody Office office) {
        officeServices.upsertOffice(scope, officeId, office);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Office deleted successfully"),
            @ApiResponse(code = 400, message = "Failed to delete Office")})
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOffice(@RequestParam String scope, @RequestParam String officeId) {
        officeServices.deleteOffice(scope, officeId);
    }

}
