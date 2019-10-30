package pro.horoshilov.family.repository.specification.impl;

import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.repository.specification.ISqlSpecification;

public class RelationshipFindByIdSpecification implements ISqlSpecification {

    public Map<String, ?> getParamMap() {
        return paramMap;
    }

    private final Map<String, ?> paramMap;

    public RelationshipFindByIdSpecification(final Long relationshipId) {
        final Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("relationship_id", relationshipId);
        this.paramMap = paramMap;
    }

    @Override
    public String toSqlClauses(final String sql) {
        //language=sql
        return sql + " where r.relationship_id = :relationship_id";
    }
}
