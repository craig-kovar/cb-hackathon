package cb.fm.backtowork.services;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.entities.Rule;
import cb.fm.backtowork.entities.RuleSet;
import cb.fm.backtowork.repositories.RulesetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RulesetService {

    @Autowired
    private  RulesetRepository rulesetRepository;

    public  RuleSet getRuleSet(String state) {
        return rulesetRepository.getRuleSet(state);
    }

    public boolean processRules(List<Rule> rulez, Employee employee) {

        Map<String, Object> employeeMap = employee.toMap();

        for (Rule itr: rulez) {

            if (employeeMap != null) {

                if (!employeeMap.containsKey(itr.getField()))
                    return false;

                Object tmpValue = employeeMap.get(itr.getField());

                //Equality Check
                if (itr.getLogic().equalsIgnoreCase("=") ||
                    itr.getLogic().equalsIgnoreCase("equals")) {
                    if (!tmpValue.equals(itr.getValue())) {
                        return false;
                    }
                }

                //Greater Than Check - Limiting to int compatible values for now
                if (itr.getLogic().equalsIgnoreCase(">") ||
                        itr.getLogic().equalsIgnoreCase("Greater Than")) {
                    int checkValue = 0;
                    int compareValue = Integer.parseInt(itr.getValue());
                    if (tmpValue instanceof Integer) {
                        checkValue = (int) tmpValue;
                    } else if (tmpValue instanceof String) {
                        checkValue = Integer.parseInt((String) tmpValue);
                    }

                    if (!(checkValue > compareValue)) {
                        return false;
                    }
                }

                //In list
                if (itr.getLogic().equalsIgnoreCase("in")) {
                    String checkValue = tmpValue.toString();
                    String[] compareValues = itr.getValue().split(",");

                    boolean retStatus = false;
                    for (String sItr : compareValues) {
                        if (checkValue.equalsIgnoreCase(sItr)) {
                            retStatus = true;
                            break;
                        }
                    }

                    return retStatus;
                }

            } else {
                return false;
            }
        }

        return true;
    }

}
