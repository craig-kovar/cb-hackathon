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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/office")
@SuppressWarnings("deprecated")
@Api(value = "Office REST Endpoint", description = "Manage Office information")
public class OfficeController {

    @Autowired
    OfficeServices officeServices;

    private static final String okUpsert = "{ \"msg\" : \"upserted office successfully\" }";
    private static final String errUpsert = "{ \"msg\" : \"failed to upsert office successfully\" }";
    private static final String okDelete = "{ \"msg\" : \"deleted office successfully\" }";
    private static final String errDelete = "{ \"msg\" : \"failed to delete office successfully\" }";

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Office retrieved successfully"),
            @ApiResponse(code = 400, message = "Failed to retrieve office")})
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Office> getOfficeById(@RequestParam String scope, @RequestParam String officeId) {
        Office result = officeServices.getOfficeById(scope, officeId);
        if (result != null && result.getType().equalsIgnoreCase("NotFound")) {
            return ResponseEntity.badRequest().body(null);
        } else if (result != null) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(500).body(null);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Office updated successfully"),
            @ApiResponse(code = 400, message = "Failed to update office")})
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upsertOffice(@RequestParam String scope, @RequestParam String officeId, @RequestBody Office office) {
        if (officeServices.upsertOffice(scope, officeId, office)) {
            return ResponseEntity.ok(okUpsert);
        }

        return ResponseEntity.badRequest().body(errUpsert);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Office deleted successfully"),
            @ApiResponse(code = 400, message = "Failed to delete Office")})
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteOffice(@RequestParam String scope, @RequestParam String officeId) {
        if (officeServices.deleteOffice(scope, officeId)) {
            return ResponseEntity.ok(okDelete);
        }

        return ResponseEntity.badRequest().body(errDelete);
    }

}
