package pro.horoshilov.family.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.horoshilov.family.entity.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("personRepository")
public class PersonRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    PersonRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //language=sql
    private final static String SQL_INSERT_PERSON = "insert into person ( " +
            "birthday, " +
            "death, " +
            "description, " +
            "name_first, " +
            "name_last, " +
            "name_middle, " +
            "sex) " +
            "values ( " +
            ":birthday, " +
            ":death, " +
            ":description, " +
            ":name_first, " +
            ":name_last, " +
            ":name_middle, " +
            ":sex)";

    //language=sql
    private final static String SQL_INSERT_CONTACT_INFORMATION = "insert into contact_information ( " +
            "person_id, " +
            "code, " +
            "value) " +
            "values ( " +
            ":person_id, " +
            ":code, " +
            ":value)";
    
    //language=sql
    private final static String SQL_GET_ALL = "select " +
            "p.person_id, " +
            "p.birthday, " +
            "p.death, " +
            "p.description, " +
            "p.name_first, " +
            "p.name_last, " +
            "p.name_middle, " +
            "p.person_id, " +
            "p.sex " +
            "from " +
            "person p";

    //language=sql
    private final static String SQL_GET_CONTACT_INFORMATION = "select " +
            "ci.code, " +
            "ci.value " +
            "from " +
            "contact_information ci where ci.person_id = :person_id;";

    public Long insert(final Person person) throws Exception {
        Long personId = insertPerson(person);

        Map<String, String> contactInformation = person.getContactInformation();

        if (contactInformation != null) {
            insertContactInformation(personId, contactInformation);
        }

        return personId;
    }
    
    private Long insertPerson(final Person person) throws Exception {
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

        namedParameters.put("description", person.getDescription());

        if (person.getName() != null) {
            namedParameters.put("name_first", person.getName().getFirst());
            namedParameters.put("name_last", person.getName().getMiddle());
            namedParameters.put("name_middle", person.getName().getLast());
        } else {
            namedParameters.put("name_first", null);
            namedParameters.put("name_last", null);
            namedParameters.put("name_middle", null);
        }

        namedParameters.put("sex", person.getSex().name());

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(namedParameters);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(SQL_INSERT_PERSON, sqlParameterSource, keyHolder);
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new Exception("Can't return person id");
        }
    }

    private int[] insertContactInformation(final Number personId, final Map<String, String> contactInformation) {

        List<Map<String, Object>> namedParameters = new ArrayList<>(contactInformation.size());

        for (Map.Entry<String, String> entry : contactInformation.entrySet()) {
            final Map<String, Object> namedParameter = new HashMap<>();

            namedParameter.put("person_id", personId);
            namedParameter.put("code", entry.getKey());
            namedParameter.put("value", entry.getValue());

            namedParameters.add(namedParameter);
        }

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(namedParameters);
  


        return namedParameterJdbcTemplate.batchUpdate(SQL_INSERT_CONTACT_INFORMATION, batch);
    }

    public List<Person> findAll() {
        return namedParameterJdbcTemplate.query(SQL_GET_ALL, (rs, rowNum) -> {
            Person person = new Person();

            final Date birthdayRow = rs.getDate("birthday");

            if (birthdayRow != null) {
                Calendar birthday = new GregorianCalendar();
                birthday.setTime(birthdayRow);

                person.setBirthday(birthday);
            }

            final Date deathRow = rs.getDate("death");

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

            Long personId = rs.getLong("person_id");

            person.setId(personId);
            person.setSex(Person.Sex.valueOf(rs.getString("sex")));

            final Map<String, String> contactInformation = namedParameterJdbcTemplate.query(
                    SQL_GET_CONTACT_INFORMATION,
                    new MapSqlParameterSource("person_id", personId),
                    rs1 -> {
                        Map<String, String> mapResult = new HashMap<>();
                        while (rs1.next()) {
                            mapResult.put(rs1.getString("code"), rs1.getString("value"));
                        }
                        return mapResult;
                    }
            );

            person.setContactInformation(contactInformation);

            return person;
        });
    }
}
