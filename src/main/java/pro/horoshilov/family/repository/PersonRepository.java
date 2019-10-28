package pro.horoshilov.family.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Repository("personRepository")
public class PersonRepository implements IRepository<Person> {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    PersonRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // language=sql
    private final static String SQL_UPDATE_PERSON =
            "update person p " +
               "set p.birthday = :birthday, " +
                   "p.death = :death, " +
                   "p.avatar_id = :avatar_id, " +
                   "p.description = :description, " +
                   "p.name_first = :name_first, " +
                   "p.name_middle = :name_middle, " +
                   "p.name_last = :name_last, " +
                   "p.sex = :sex " +
             "where p.person_id = :person_id";

    // language=sql
    private final static String SQL_INSERT_PERSON =
            "insert into person ( " +
                        "birthday, " +
                        "death, " +
                        "avatar_id, " +
                        "description, " +
                        "name_first, " +
                        "name_middle, " +
                        "name_last, " +
                        "sex) " +
                "values ( " +
                        ":birthday, " +
                        ":death, " +
                        ":avatar_id, " +
                        ":description, " +
                        ":name_first, " +
                        ":name_middle, " +
                        ":name_last, " +
                        ":sex)";

    //language=sql
    private final static String SQL_DELETE_PERSON = "delete from person p where p.person_id = :person_id";

    @Override
    public Long add(final Person person) throws EmptyInsertIdException {
        final Map<String, Object> namedParameters = getParamsFromPerson(person);
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource(namedParameters);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(SQL_INSERT_PERSON, sqlParameterSource, keyHolder);
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new EmptyInsertIdException("Can't return person id");
        }
    }

    @Override
    public int update(Person person) {
        final Map<String, Object> namedParameters = getParamsFromPerson(person);
        namedParameters.put("person_id", person.getId());

        return namedParameterJdbcTemplate.update(SQL_UPDATE_PERSON, namedParameters);
    }

    @Override
    public int remove(Person person) {
        final Long personId = person.getId();

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("person_id", personId);
        return namedParameterJdbcTemplate.update(SQL_DELETE_PERSON, sqlParameterSource);
    }

    @Override
    public List<Person> query(ISqlSpecification specification) {
        return namedParameterJdbcTemplate.query(specification.toSqlClauses(), specification.getParamMap(), new PersonRowMapper());
    }

    private Map<String, Object> getParamsFromPerson(final Person person) {
        final Map<String, Object> namedParameters = new HashMap<>();

        if (person.getBirthday() != null) {
            namedParameters.put("birthday", person.getBirthday().getTime());
        } else {
            namedParameters.put("birthday", null);
        }

        if (person.getDeath() != null) {
            namedParameters.put("death", person.getDeath().getTime());
        } else {
            namedParameters.put("death", null);
        }

        if (person.getAvatarId() != null) {
            namedParameters.put("avatar_id", person.getAvatarId());
        } else {
            namedParameters.put("avatar_id", null);
        }

        namedParameters.put("description", person.getDescription());

        if (person.getName() != null) {
            namedParameters.put("name_first", person.getName().getFirst());
            namedParameters.put("name_middle", person.getName().getMiddle());
            namedParameters.put("name_last", person.getName().getLast());
        } else {
            namedParameters.put("name_first", null);
            namedParameters.put("name_last", null);
            namedParameters.put("name_middle", null);
        }

        namedParameters.put("sex", person.getSex().name());

        return namedParameters;
    }

    private static class PersonRowMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Person person = new Person();

            final Date birthdayRow = rs.getDate("birthday");

            if (birthdayRow != null) {
                final Calendar birthday = new GregorianCalendar();
                birthday.setTime(birthdayRow);

                person.setBirthday(birthday);
            }

            final Date deathRow = rs.getDate("death");

            final Long avatarId = rs.getLong("avatar_id");

            if (avatarId > 0) {
                person.setAvatarId(avatarId);
            }

            if (deathRow != null) {
                Calendar death = new GregorianCalendar();
                death.setTime(deathRow);

                person.setDeath(death);
            }

            person.setDescription(rs.getString("description"));

            Person.Name personName = new Person.Name(
                    rs.getString("name_first"),
                    rs.getString("name_middle"),
                    rs.getString("name_last")
            );

            person.setName(personName);

            final Long personId = rs.getLong("person_id");

            person.setId(personId);
            person.setSex(Person.Sex.valueOf(rs.getString("sex")));

            return person;
        }
    }
}
