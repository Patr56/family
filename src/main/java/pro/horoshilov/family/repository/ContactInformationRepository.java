package pro.horoshilov.family.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.horoshilov.family.entity.ContactInformation;
import pro.horoshilov.family.entity.Tuple;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.repository.specification.ISqlSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("contactInformationRepository")
public class ContactInformationRepository implements IRepository<Tuple<Long, ContactInformation>> {

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
                    ":position" +
                    ")";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ContactInformationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Long add(Tuple<Long, ContactInformation> tuple) throws EmptyInsertIdException {

        final ContactInformation contactInformation = tuple.getRight();
        final Long personId = tuple.getLeft();

        final Map<String, Object> namedParameter = new HashMap<>();

        namedParameter.put("person_id", personId);
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
    public int update(Tuple<Long, ContactInformation> longContactInformationTuple) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public int remove(Tuple<Long, ContactInformation> longContactInformationTuple) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Tuple<Long, ContactInformation>> query(ISqlSpecification specification) {
        throw new UnsupportedOperationException("Method not implemented");
    }

}
