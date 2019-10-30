package pro.horoshilov.family.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.entity.Relationship;
import pro.horoshilov.family.repository.AbstractRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("relationshipRepository")
public class RelationshipRepository extends AbstractRepository<Relationship> {

    private final static String ENTITY_NAME = Relationship.class.getName();

    // language=sql
    private final static String SQL_INSERT =
            "insert into relationship ( \n" +
             "           person_id,\n" +
             "           related_id,\n" +
             "           type) \n" +
             "   values (:person_id, \n" +
             "           :related_id, \n" +
             "           :type)";

    // language=sql
    private final static String SQL_UPDATE =
            "update relationship r \n" +
            "   set r.person_id = :person_id, \n" +
            "       r.related_id = :related_id, \n" +
            "       r.type = :type \n";

    //language=sql
    private final static String SQL_SELECT =
            "select r.relationship_id, \n" +
            "       r.person_id, \n" +
            "       r.related_id, \n" +
            "       r.type \n" +
            "  from relationship r \n";

    //language=sql
    private final static String SQL_DELETE = "delete from relationship r \n";

    @Autowired
    public RelationshipRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(namedParameterJdbcTemplate);
    }

    @Override
    protected String getSqlSelect() {
        return SQL_SELECT;
    }

    @Override
    protected String getSqlInsert() {
        return SQL_INSERT;
    }

    @Override
    protected String getSqlUpdate() {
        return SQL_UPDATE;
    }

    @Override
    protected String getSqlDelete() {
        return SQL_DELETE;
    }

    @Override
    protected String getEntityName() {
        return ENTITY_NAME;
    }

    @Override
    protected RowMapper<Relationship> getRowMapper() {
        return new RelationshipRowMapper();
    }

    @Override
    protected Map<String, Object> getParams(Relationship entity) {
        final Map<String, Object> namedParameters = new HashMap<>();

        namedParameters.put("person_id", entity.getPersonId());
        namedParameters.put("related_id", entity.getRelatedId());
        namedParameters.put("type", entity.getType().name());

        return namedParameters;
    }

    private static class RelationshipRowMapper implements RowMapper<Relationship> {

        @Override
        public Relationship mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Relationship relationship = new Relationship();

            relationship.setId(rs.getLong("relationship_id"));
            relationship.setPersonId(rs.getLong("person_id"));
            relationship.setRelatedId(rs.getLong("related_id"));
            relationship.setType(Relationship.Type.valueOf(rs.getString("type")));

            return relationship;
        }
    }
}
