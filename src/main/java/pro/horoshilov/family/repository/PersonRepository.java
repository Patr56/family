package pro.horoshilov.family.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.entity.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("personRepository")
public class PersonRepository extends AbstractRepository<Person> {

    private final static String ENTITY_NAME = Person.class.getName();

    // language=sql
    private final static String SQL_UPDATE =
            "update person p \n" +
            "   set p.birthday = :birthday, \n" +
            "       p.death = :death, \n" +
            "       p.avatar_id = :avatar_id, \n" +
            "       p.description = :description, \n" +
            "       p.name_first = :name_first, \n" +
            "       p.name_middle = :name_middle, \n" +
            "       p.name_last = :name_last, \n" +
            "       p.sex = :sex \n";

    // language=sql
    private final static String SQL_INSERT =
            "insert into person ( \n" +
            "            birthday, \n" +
            "            death, \n" +
            "            avatar_id, \n" +
            "            description, \n" +
            "            name_first, \n" +
            "            name_middle, \n" +
            "            name_last, \n" +
            "            sex) \n" +
            "    values (:birthday, \n" +
            "            :death, \n" +
            "            :avatar_id, \n" +
            "            :description, \n" +
            "            :name_first, \n" +
            "            :name_middle, \n" +
            "            :name_last, \n" +
            "            :sex)";

    //language=sql
    private final static String SQL_DELETE = "delete from person p \n";

    //language=sql
    final static String SQL_SELECT =
            "select p.person_id, \n" +
            "       p.birthday, \n" +
            "       p.death, \n" +
            "       p.avatar_id, \n" +
            "       p.description, \n" +
            "       p.name_first, \n" +
            "       p.name_last, \n" +
            "       p.name_middle, \n" +
            "       p.person_id, \n" +
            "       p.sex \n" +
            "  from person p \n";

    @Autowired
    PersonRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
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
    protected RowMapper<Person> getRowMapper() {
        return new PersonRowMapper();
    }

    @Override
    protected Map<String, Object> getParams(Person entity) {
        final Map<String, Object> namedParameters = new HashMap<>();

        if (entity.getBirthday() != null) {
            namedParameters.put("birthday", entity.getBirthday().getTime());
        } else {
            namedParameters.put("birthday", null);
        }

        if (entity.getDeath() != null) {
            namedParameters.put("death", entity.getDeath().getTime());
        } else {
            namedParameters.put("death", null);
        }

        namedParameters.put("avatar_id", entity.getAvatarId());
        namedParameters.put("description", entity.getDescription());

        if (entity.getName() != null) {
            namedParameters.put("name_first", entity.getName().getFirst());
            namedParameters.put("name_middle", entity.getName().getMiddle());
            namedParameters.put("name_last", entity.getName().getLast());
        } else {
            namedParameters.put("name_first", null);
            namedParameters.put("name_last", null);
            namedParameters.put("name_middle", null);
        }

        namedParameters.put("sex", entity.getSex().name());

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

            person.setId(rs.getLong("person_id"));
            person.setSex(Person.Sex.valueOf(rs.getString("sex")));

            return person;
        }
    }
}
