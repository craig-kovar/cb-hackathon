package cb.fm.backtowork.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/signup")
public class SignUpController {

    @RequestMapping(value = "/employee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerEmployee(@RequestParam String employeeName) {
        return "Hello " + employeeName;
    }

    @RequestMapping(value = "/{orgName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerOrg(@PathVariable("orgName") String orgName) {
        return "hello " + orgName;
    }

}
