package pro.horoshilov.family.repository.specification.impl;

import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.repository.specification.ISqlSpecification;

public class PhotoInformationFindByIdSpecification implements ISqlSpecification {

    public Map<String, ?> getParamMap() {
        return paramMap;
    }

    private final Map<String, ?> paramMap;

    public PhotoInformationFindByIdSpecification(final Long photoInformationId) {
        final Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("photo_information_id", photoInformationId);
        this.paramMap = paramMap;
    }

    @Override
    public String toSqlClauses(final String sql) {
        //language=sql
        return sql + " where pi.photo_information_id = :photo_information_id";
    }
}
