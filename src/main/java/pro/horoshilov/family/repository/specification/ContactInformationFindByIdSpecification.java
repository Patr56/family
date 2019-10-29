package pro.horoshilov.family.repository.specification;

import java.util.HashMap;
import java.util.Map;

public class ContactInformationFindByIdSpecification implements ISqlSpecification{
    public Map<String, ?> getParamMap() {
        return paramMap;
    }

    private final Map<String, ?> paramMap;

    public ContactInformationFindByIdSpecification(final Long contactInformationId) {
        final Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("contact_information_id", contactInformationId);
        this.paramMap = paramMap;
    }

    @Override
    public String toSqlClauses(final String sql) {
        //language=sql
        return sql + " where ci.contact_information_id = :contact_information_id";
    }
}
