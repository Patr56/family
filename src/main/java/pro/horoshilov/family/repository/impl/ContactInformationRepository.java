package pro.horoshilov.family.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.entity.ContactInformation;
import pro.horoshilov.family.repository.AbstractRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("contactInformationRepository")
public class ContactInformationRepository extends AbstractRepository<ContactInformation> {

    private final static String ENTITY_NAME = ContactInformation.class.getName();

    //language=sql
    private final static String SQL_DELETE = "delete from contact_information ci \n";

    // language=sql
    private final static String SQL_UPDATE =
            "update contact_information ci \n" +
            "   set ci.person_id = :person_id, \n" +
            "       ci.code = :code, \n" +
            "       ci.value = :value, \n" +
            "       ci.type = :type, \n" +
            "       ci.position = :position \n";

    // language=sql
    private final static String SQL_INSERT =
            "insert into contact_information ( \n" +
            "            person_id, \n" +
            "            code, \n" +
            "            value, \n" +
            "            type, \n" +
            "            position) \n" +
            "    values (:person_id, \n" +
            "            :code, \n" +
            "            :value, \n" +
            "            :type, \n" +
            "            :position)";

    //language=sql
    private final static String SQL_SELECT =
            "select \n" +
             "       ci.contact_information_id, \n" +
             "       ci.person_id, \n" +
             "       ci.code, \n" +
             "       ci.value, \n" +
             "       ci.type, \n" +
             "       ci.position \n" +
             "from \n" +
             "      contact_information ci \n";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
    protected RowMapper<ContactInformation> getRowMapper() {
        return new ContactInformationRowMapper();
    }

    @Override
    protected Map<String, Object> getParams(ContactInformation entity) {
        final Map<String, Object> namedParameter = new HashMap<>();

        namedParameter.put("person_id", entity.getPersonId());
        namedParameter.put("code", entity.getCode());
        namedParameter.put("value", entity.getValue());
        namedParameter.put("type", entity.getType().name());
        namedParameter.put("position", entity.getPosition());

        return namedParameter;
    }

    @Autowired
    public ContactInformationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(namedParameterJdbcTemplate);
    }

    private static class ContactInformationRowMapper implements RowMapper<ContactInformation> {

        @Override
        public ContactInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
            final ContactInformation contactInformation = new ContactInformation();

            contactInformation.setId(rs.getLong("contact_information_id"));
            contactInformation.setPersonId(rs.getLong("person_id"));
            contactInformation.setCode(rs.getString("code"));
            contactInformation.setValue(rs.getString("value"));
            contactInformation.setType(ContactInformation.Type.valueOf(rs.getString("type")));
            contactInformation.setPosition(rs.getInt("position"));

            return contactInformation;
        }
    }

}
