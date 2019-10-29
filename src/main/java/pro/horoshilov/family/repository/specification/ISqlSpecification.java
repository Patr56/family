package pro.horoshilov.family.repository.specification;

import java.util.Map;

public interface ISqlSpecification {
    public Map<String, ?> getParamMap();
    String toSqlClauses(final String sql);
}
