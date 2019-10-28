package pro.horoshilov.family.repository.specification;

import java.util.HashMap;
import java.util.Map;

public class PhotoFindByIdSpecification implements ISqlSpecification {
    //language=sql
    private final static String SQL =
            "select \n" +
            "      p.photo_id, \n" +
            "      p.url, \n" +
            "      p.type \n" +
            "from \n" +
            "      photo p \n";

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
    public String toSqlClauses() {
        //language=sql
        return SQL + "where p.photo_id = :photo_id";
    }
}
