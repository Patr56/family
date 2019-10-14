package pro.horoshilov.family.repository.specification;

import java.util.HashMap;
import java.util.Map;

public class PersonFindAllSpecification extends PersonSpecification implements ISqlSpecification {
    @Override
    public Map<String, ?> getParamMap() {
        return new HashMap<>();
    }

    @Override
    public String toSqlClauses() {
        // language=sql
        return SQL + "where 1 = 1";
    }
}
