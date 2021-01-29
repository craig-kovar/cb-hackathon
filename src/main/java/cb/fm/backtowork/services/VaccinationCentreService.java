package cb.fm.backtowork.services;

import cb.fm.backtowork.entities.Office;
import cb.fm.backtowork.entities.VaccinationCentre;
import cb.fm.backtowork.repositories.OfficeRepository;
import cb.fm.backtowork.repositories.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccinationCentreService {

    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;

    public VaccinationCentre getVCById(String id) {
        return vaccinationCenterRepository.getVCById(id);
    }



}
