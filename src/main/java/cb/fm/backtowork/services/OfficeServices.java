package cb.fm.backtowork.services;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.entities.Office;
import cb.fm.backtowork.repositories.EmployeeRepository;
import cb.fm.backtowork.repositories.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficeServices {

    @Autowired
    OfficeRepository officeRepository;

    public Office getOfficeById(String scope, String id) {
        return officeRepository.getOfficeById(scope, id);
    }

    public void upsertOffice(String scope, String id, Office office) {
        officeRepository.upsertOffice(scope, id, office);
    }

    public void deleteOffice(String scope, String id) {
        officeRepository.deleteOffice(scope, id);
    }

}
