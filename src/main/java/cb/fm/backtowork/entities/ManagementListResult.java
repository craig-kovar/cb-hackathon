package cb.fm.backtowork.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManagementListResult {

    @Getter @Setter
    List<String> employees = new ArrayList<>();

    @Getter @Setter
    List<String> eligbleEmployees = new ArrayList<>();

    @Getter @Setter
    List<String> inelgibleEmployees = new ArrayList<>();

    @Getter @Setter
    List<ManagementListVCDetail> employeeVCDetails = new ArrayList<>();

    public void addEmployee(String newEmployee) {
        employees.add(newEmployee);
    }

    public void addEligibleEmployee(String newEmployee) {
        eligbleEmployees.add(newEmployee);
    }

    public void addIneligibleEmployee(String newEmployee) {
        inelgibleEmployees.add(newEmployee);
    }

    public void addEmployeeVCDetails(ManagementListVCDetail newEmployeeVCDetail) {
        employeeVCDetails.add(newEmployeeVCDetail);
    }

}
