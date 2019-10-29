package pro.horoshilov.family.repository.specification;

import java.util.HashMap;
import java.util.Map;

public class PhotoFindByIdSpecification implements ISqlSpecification {

    public Map<String, ?> getParamMap() {
        return paramMap;
    }

    private final Map<String, ?> paramMap;

    public PhotoFindByIdSpecification(final Long photoId) {
        final Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("photo_id", photoId);
        this.paramMap = paramMap;
    }

    @Override
    public String toSqlClauses(final String sql) {
        //language=sql
        return sql + " where p.photo_id = :photo_id";
    }
}
