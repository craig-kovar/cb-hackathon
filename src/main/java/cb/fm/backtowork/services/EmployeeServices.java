package cb.fm.backtowork.services;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServices {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee getEmployeeById(String scope, String id) {
        return employeeRepository.getEmployeeById(scope, id);
    }

    public void upsertEmployee(String scope, String id, Employee employee) {
        employeeRepository.upsertEmployee(scope, id, employee);
    }

    public void deleteEmployee(String scope, String id) {
        employeeRepository.deleteEmployee(scope, id);
    }

    public boolean isEligible(Employee employee) {
        return true;
    }

}
