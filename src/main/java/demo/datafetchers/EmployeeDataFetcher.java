package demo.datafetchers;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class EmployeeDataFetcher {
    public DataFetcher<String> searchEmployees() {

        return dataFetchingEnvironment -> {
            return "ciao";
        };
    }


}
