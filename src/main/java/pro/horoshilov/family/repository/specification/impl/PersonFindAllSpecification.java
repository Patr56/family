package pro.horoshilov.family.repository.specification.impl;

import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.repository.specification.ISqlSpecification;

public class PersonFindAllSpecification implements ISqlSpecification {
    @Override
    public Map<String, ?> getParamMap() {
        return new HashMap<>();
    }

    @Override
    public String toSqlClauses(final String sql) {
        // language=sql
        return sql + "where 1 = 1";
    }
}
