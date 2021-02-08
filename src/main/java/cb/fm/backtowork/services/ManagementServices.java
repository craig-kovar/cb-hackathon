package cb.fm.backtowork.services;

import cb.fm.backtowork.entities.*;
import cb.fm.backtowork.repositories.EmployeeRepository;
import cb.fm.backtowork.utils.ConnectionManager;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManagementServices {

    @Autowired EmployeeServices employeeServices;

    private ConnectionManager connMgr = ConnectionManager.getConnManager();

    public ManagementResultSet getNumEmployeesPerOffice(String scopeName) {
        Cluster cluster = connMgr.getCluster();
        if (cluster != null) {
            String statement = String.format("select officeLocId, count(*) as employees " +
                    "from %s where officeLocId is not missing " +
                    "group by officeLocId", "BackToWork."+scopeName+".employee");

            QueryResult result = cluster.query(statement);

            List<ManagementResult> resultList = new ArrayList<>();
            for (ManagementResult row : result.rowsAs(ManagementResult.class)) {
                resultList.add(row);
            }

            ManagementResultSet results = new ManagementResultSet();
            results.setResults(resultList);

            return results;
        }

        return null;
    }

    public ManagementResultSet getEligibleEmployeesPerOffice(String scopeName) {
        Cluster cluster = connMgr.getCluster();
        if (cluster != null) {
            String statement = String.format("select officeLocId, count(*) as employees " +
                    "from %s where officeLocId is not missing and isEligibleForVaccination = true " +
                    "group by officeLocId", "BackToWork."+scopeName+".employee");

            QueryResult result = cluster.query(statement);

            List<ManagementResult> resultList = new ArrayList<>();
            for (ManagementResult row : result.rowsAs(ManagementResult.class)) {
                resultList.add(row);
            }

            ManagementResultSet results = new ManagementResultSet();
            results.setResults(resultList);

            return results;
        }

        return null;
    }

    public ManagementResultSet getNumEmployeesAtOffice(String scopeName, String officeLocId) {
        Cluster cluster = connMgr.getCluster();
        if (cluster != null) {
            String statement = String.format("select officeLocId, count(*) as employees " +
                    "from %s where officeLocId = \"%s\" group by officeLocId", "BackToWork."+scopeName+".employee", officeLocId);
            //System.out.println("Statement = " + statement);

            QueryResult result = cluster.query(statement);

            List<ManagementResult> resultList = new ArrayList<>();
            for (ManagementResult row : result.rowsAs(ManagementResult.class)) {
                //System.out.println("Result = " + row.getOfficeLocId() + " " + row.getEmployees());
                resultList.add(row);
            }

            ManagementResultSet results = new ManagementResultSet();
            results.setResults(resultList);

            return results;
        }

        return null;
    }

    public ManagementResultSet getNumEligibleEmployeesAtOffice(String scopeName, String officeLocId) {
        Cluster cluster = connMgr.getCluster();
        if (cluster != null) {
            String statement = String.format("select officeLocId, count(*) as employees " +
                    "from %s where officeLocId = \"%s\" and isEligibleForVaccination = true group by officeLocId", "BackToWork."+scopeName+".employee", officeLocId);
            //System.out.println("Statement = " + statement);

            QueryResult result = cluster.query(statement);

            List<ManagementResult> resultList = new ArrayList<>();
            for (ManagementResult row : result.rowsAs(ManagementResult.class)) {
                //System.out.println("Result = " + row.getOfficeLocId() + " " + row.getEmployees());
                resultList.add(row);
            }

            ManagementResultSet results = new ManagementResultSet();
            results.setResults(resultList);

            return results;
        }

        return null;
    }

    //select count(*) from BackToWork.Walmart.employee where ARRAY_LENGTH(vaccinationDetails) = 1
    //and officeLocId = "38190"
    public ManagementResultSet getVaccinatedEmployeesAtOffice(String scopeName, String officeLocId, int numDoses) {
        Cluster cluster = connMgr.getCluster();
        if (cluster != null) {
            String statement = String.format("select officeLocId, count(*) as employees " +
                    "from %s where officeLocId = \"%s\" and ARRAY_LENGTH(vaccinationDetails) = " + numDoses +
                    " group by officeLocId", "BackToWork."+scopeName+".employee", officeLocId);
            //System.out.println("Statement = " + statement);

            QueryResult result = cluster.query(statement);

            List<ManagementResult> resultList = new ArrayList<>();
            for (ManagementResult row : result.rowsAs(ManagementResult.class)) {
                //System.out.println("Result = " + row.getOfficeLocId() + " " + row.getEmployees());
                resultList.add(row);
            }

            ManagementResultSet results = new ManagementResultSet();
            results.setResults(resultList);

            return results;
        }

        return null;
    }

    //select count(*) from BackToWork.Walmart.employee where ARRAY_LENGTH(vaccinationDetails) = 1
    //and officeLocId = "38190"
    public ManagementResultSet getVaccinatedEmployeesPerOffice(String scopeName, int numDoses) {
        Cluster cluster = connMgr.getCluster();
        if (cluster != null) {
            String statement = String.format("select officeLocId, count(*) as employees " +
                    "from %s where ARRAY_LENGTH(vaccinationDetails) = " + numDoses +
                    " group by officeLocId", "BackToWork."+scopeName+".employee");
            //System.out.println("Statement = " + statement);

            QueryResult result = cluster.query(statement);

            List<ManagementResult> resultList = new ArrayList<>();
            for (ManagementResult row : result.rowsAs(ManagementResult.class)) {
                //System.out.println("Result = " + row.getOfficeLocId() + " " + row.getEmployees());
                resultList.add(row);
            }

            ManagementResultSet results = new ManagementResultSet();
            results.setResults(resultList);

            return results;
        }

        return null;
    }

    /*select officeLocId,
    SUM(CASE WHEN a.isEligibleForVaccination = true THEN 1 ELSE 0 END) eligble,
    SUM(CASE WHEN ARRAY_LENGTH(a.vaccinationDetails) >= 1 THEN 1 ELSE 0 END) vaccination,
    COUNT(*) as employees
    FROM BackToWork.Walmart.employee a
    WHERE officeLocId is not missing
    GROUP BY officeLocId*/
    public ManagementResultSet getOfficesSummary(String scopeName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select officeLocId as officeLocId,\n");
        sb.append("SUM(CASE WHEN a.isEligibleForVaccination = true THEN 1 ELSE 0 END) eligibleEmployees,\n");
        sb.append("SUM(CASE WHEN ARRAY_LENGTH(a.vaccinationDetails) >= 1 THEN 1 ELSE 0 END) vaccinatedEmployees,\n");
        sb.append("COUNT(*) as employees\n");
        sb.append("FROM BackToWork.").append(scopeName).append(".employee a\n");
        sb.append("WHERE officeLocId is not missing\n");
        sb.append("GROUP BY officeLocId");

        Cluster cluster = connMgr.getCluster();
        if (cluster != null) {
            QueryResult result = cluster.query(sb.toString());

            List<ManagementResult> resultList = new ArrayList<>();
            for (ManagementResult row : result.rowsAs(ManagementResult.class)) {
                //System.out.println("Result = " + row.getOfficeLocId() + " " + row.getEmployees());
                resultList.add(row);
            }

            ManagementResultSet results = new ManagementResultSet();
            results.setResults(resultList);

            return results;
        }

        return null;

    }

    public ManagementResultSet getOfficeSummary(String scopeName, String officeLocId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select officeLocId as officeLocId,\n");
        sb.append("SUM(CASE WHEN a.isEligibleForVaccination = true THEN 1 ELSE 0 END) eligibleEmployees,\n");
        sb.append("SUM(CASE WHEN ARRAY_LENGTH(a.vaccinationDetails) >= 1 THEN 1 ELSE 0 END) vaccinatedEmployees,\n");
        sb.append("COUNT(*) as employees\n");
        sb.append("FROM BackToWork.").append(scopeName).append(".employee a\n");
        sb.append("WHERE officeLocId is not missing and officeLocId=\"").append(officeLocId).append("\"\n");
        sb.append("GROUP BY officeLocId");

        Cluster cluster = connMgr.getCluster();
        if (cluster != null) {
            QueryResult result = cluster.query(sb.toString());

            List<ManagementResult> resultList = new ArrayList<>();
            for (ManagementResult row : result.rowsAs(ManagementResult.class)) {
                //System.out.println("Result = " + row.getOfficeLocId() + " " + row.getEmployees());
                resultList.add(row);
            }

            ManagementResultSet results = new ManagementResultSet();
            results.setResults(resultList);

            return results;
        }

        return null;

    }

    public ManagementListResult getEmployeeList(String scopeName, String officeLocId, String limit, String offset) {
        StringBuilder sb = new StringBuilder();
        sb.append("select RAW meta().id as emailId\n");
        sb.append("FROM BackToWork.").append(scopeName).append(".employee\n");
        sb.append("WHERE officeLocId is not missing and officeLocId=\"").append(officeLocId).append("\"\n");
        if (limit != null)
            sb.append("LIMIT ").append(limit).append("\n");
        if (offset != null)
            sb.append("OFFSET ").append(offset).append("\n");

        //System.out.println(sb.toString());

        Cluster cluster = connMgr.getCluster();
        if (cluster != null) {
            QueryResult result = cluster.query(sb.toString());

            ManagementListResult resultList = new ManagementListResult();
            for (String row : result.rowsAs(String.class)) {
                //System.out.println("Result = " + row);
                //resultList.add(row);
                Employee employee = employeeServices.getEmployeeById(scopeName, row);
                if (employee != null) {
                    resultList.addEmployee(row);

                    if (employee.getIsEligibleForVaccination())
                        resultList.addEligibleEmployee(row);
                    else
                        resultList.addIneligibleEmployee(row);

                    ManagementListVCDetail vcDetail = new ManagementListVCDetail();
                    vcDetail.setEmail(row);
                    if (employee.getVaccinationDetails() != null) {
                        vcDetail.setNumDoses(employee.getVaccinationDetails().size());
                    } else {
                        vcDetail.setNumDoses(0);
                    }

                    resultList.addEmployeeVCDetails(vcDetail);
                }

            }

            return resultList;
        }

        return null;

    }

}
