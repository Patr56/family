package pro.horoshilov.family.repository.specification;

import java.util.HashMap;
import java.util.Map;

public class PersonFindByIdSpecification extends PersonSpecification implements ISqlSpecification {

    public Map<String, ?> getParamMap() {
        return paramMap;
    }

    private final Map<String, ?> paramMap;

    public PersonFindByIdSpecification(final Long personId) {
        final Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("person_id", personId);
        this.paramMap = paramMap;
    }

    @Override
    public String toSqlClauses() {
        //language=sql
        return SQL + "where p.person_id = :person_id";
    }
}
