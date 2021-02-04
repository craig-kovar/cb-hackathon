package cb.fm.backtowork.services;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.entities.VaccinationDetail;
import cb.fm.backtowork.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServices {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee getEmployeeById(String scope, String id) {
        return employeeRepository.getEmployeeById(scope, id);
    }

    public boolean upsertEmployee(String scope, String id, Employee employee) {
        return employeeRepository.upsertEmployee(scope, id, employee);
    }

    public boolean deleteEmployee(String scope, String id) {
        return employeeRepository.deleteEmployee(scope, id);
    }

    public boolean isEligible(Employee employee) {
        return true;
    }

    public boolean updateVacStatus(String scope, String id, String vcDate) {
        try {
            Employee employee = getEmployeeById(scope, id);
            if (employee != null) {
                List<VaccinationDetail> vcDetails = employee.getVaccinationDetails();
                VaccinationDetail newVCDetails = new VaccinationDetail();
                newVCDetails.setVaccinatedDate(vcDate);
                newVCDetails.setNumberOfDoses(vcDetails.size() + 1);

                if (vcDetails == null) {
                    vcDetails = new ArrayList<>();
                }
                vcDetails.add(newVCDetails);

                employee.setVaccinationDetails(vcDetails);
                upsertEmployee(scope, id, employee);
                return true;
            }
        } catch (Exception ex) {
            return false;
        }

        return false;
    }

    public boolean login(String scope,  String id,  String password) {
        Employee employee = employeeRepository.getEmployeeById(scope, id);
        if (employee != null) {
            String emplPassword = employee.getPassword();
            if (emplPassword != null && emplPassword.equals(password)) {
                return true;
            }
        }

        return false;
    }


}
