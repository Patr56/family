package pro.horoshilov.family.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.horoshilov.family.entity.ContactInformation;
import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.repository.specification.ISqlSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("contactInformationRepository")
public class ContactInformationRepository implements IRepository<ContactInformation> {

    //language=sql
    private final static String SQL_DELETE_CI =
            "delete from contact_information ci \n" +
                  "where ci.contact_information_id = :contact_information_id";

    //language=sql
    private final static String SQL_DELETE_ALL_CI =
            "delete from contact_information ci \n" +
                    "where ci.person_id = :person_id";

    // language=sql
    private final static String SQL_UPDATE_CI =
            "update contact_information ci \n" +
               "set ci.person_id = :person_id, \n" +
                   "ci.code = :code, \n" +
                   "ci.value = :value, \n" +
                   "ci.type = :type, \n" +
                   "ci.position = :position \n" +
             "where ci.contact_information_id = :contact_information_id";

    // language=sql
    private final static String SQL_INSERT_CONTACT_INFORMATION =
            "insert into contact_information ( \n" +
                         "person_id, \n" +
                         "code, \n" +
                         "value, \n" +
                         "type, \n" +
                         "position) \n" +
                 "values ( \n" +
                         ":person_id, \n" +
                         ":code, \n" +
                         ":value, \n" +
                         ":type, \n" +
                         ":position \n" +
                 ")";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ContactInformationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Long add(final ContactInformation contactInformation) throws EmptyInsertIdException {

        final Map<String, Object> namedParameter = new HashMap<>();

        namedParameter.put("person_id", contactInformation.getPersonId());
        namedParameter.put("code", contactInformation.getCode());
        namedParameter.put("value", contactInformation.getValue());
        namedParameter.put("type", contactInformation.getType().name());
        namedParameter.put("position", contactInformation.getPosition());

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource(namedParameter);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(SQL_INSERT_CONTACT_INFORMATION, sqlParameterSource, keyHolder);
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new EmptyInsertIdException("Can't return person id");
        }
    }

    @Override
    public int update(final ContactInformation contactInformation) {
        final Long contactInformationId = contactInformation.getId();

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("contact_information_id", contactInformationId);
        return namedParameterJdbcTemplate.update(SQL_UPDATE_CI, sqlParameterSource);
    }

    @Override
    public int remove(final ContactInformation contactInformation) {
        final Long contactInformationId = contactInformation.getId();

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("contact_information_id", contactInformationId);
        return namedParameterJdbcTemplate.update(SQL_DELETE_CI, sqlParameterSource);
    }

    public int removeAll(final Person person) {
        final Long personId = person.getId();

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("person_id", personId);
        return namedParameterJdbcTemplate.update(SQL_DELETE_ALL_CI, sqlParameterSource);
    }

    @Override
    public List<ContactInformation> query(ISqlSpecification specification) {
        return namedParameterJdbcTemplate.query(specification.toSqlClauses(), specification.getParamMap(), new ContactInformationRepository.ContactInformationRowMapper());
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
