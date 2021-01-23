package cb.fm.backtowork.services;

import cb.fm.backtowork.entities.Employee;
import cb.fm.backtowork.entities.ManagementResult;
import cb.fm.backtowork.entities.ManagementResultSet;
import cb.fm.backtowork.repositories.EmployeeRepository;
import cb.fm.backtowork.utils.ConnectionManager;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagementServices {

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

    public ManagementResultSet getNumEmployeesAtOffice(String scopeName, String officeLocId) {
        Cluster cluster = connMgr.getCluster();
        if (cluster != null) {
            String statement = String.format("select officeLocId, count(*) as employees " +
                    "from %s where officeLocId = \"%s\" group by officeLocId", "BackToWork."+scopeName+".employee", officeLocId);
            System.out.println("Statement = " + statement);

            QueryResult result = cluster.query(statement);

            List<ManagementResult> resultList = new ArrayList<>();
            for (ManagementResult row : result.rowsAs(ManagementResult.class)) {
                System.out.println("Result = " + row.getOfficeLocId() + " " + row.getEmployees());
                resultList.add(row);
            }

            ManagementResultSet results = new ManagementResultSet();
            results.setResults(resultList);

            return results;
        }

        return null;
    }

}