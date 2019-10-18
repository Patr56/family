package pro.horoshilov.family.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.horoshilov.family.entity.ContactInformation;
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

    // language=sql
    private final static String SQL_INSERT_CONTACT_INFORMATION =
            "insert into contact_information ( " +
                         "person_id, " +
                         "code, " +
                         "value, " +
                         "type, " +
                         "position) " +
                 "values ( " +
                         ":person_id, " +
                         ":code, " +
                         ":value, " +
                         ":type, " +
                         ":position " +
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
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public int remove(final ContactInformation contactInformation) {
        throw new UnsupportedOperationException("Method not implemented");
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
