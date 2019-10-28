package pro.horoshilov.family.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.horoshilov.family.entity.Photo;
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

@Repository("photoRepository")
public class PhotoRepository implements IRepository<Photo> {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // language=sql
    private final static String SQL_INSERT =
            "insert into photo ( \n" +
            "            url, \n" +
            "            type) \n" +
            "values (:url, \n" +
            "        :type)";

    // language=sql
    private final static String SQL_UPDATE =
            "update photo p \n" +
            "   set p.url = :url, \n" +
            "       p.type = :type \n" +
            " where p.photo_id = :photo_id";

    //language=sql
    private final static String SQL_DELETE = "delete from photo p \n" +
                                             "      where p.photo_id = :photo_id";

    @Autowired
    PhotoRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Long add(Photo photo) throws EmptyInsertIdException {
        final Map<String, Object> namedParameters = getParams(photo);
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource(namedParameters);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(SQL_INSERT, sqlParameterSource, keyHolder);
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new EmptyInsertIdException("Can't return photo id.");
        }
    }

    @Override
    public int update(Photo photo) {
        final Map<String, Object> namedParameters = getParams(photo);
        namedParameters.put("photo_id", photo.getId());

        return namedParameterJdbcTemplate.update(SQL_UPDATE, namedParameters);
    }

    @Override
    public int remove(Photo photo) {
        final Long photoId = photo.getId();

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("photo_id", photoId);
        return namedParameterJdbcTemplate.update(SQL_DELETE, sqlParameterSource);
    }

    @Override
    public List<Photo> query(ISqlSpecification specification) {
        return namedParameterJdbcTemplate.query(specification.toSqlClauses(), specification.getParamMap(), new PhotoRowMapper());
    }

    private Map<String, Object> getParams(final Photo photo) {
        final Map<String, Object> namedParameters = new HashMap<>();

        if (photo.getType() != null) {
            namedParameters.put("type", photo.getType().name());
        } else {
            namedParameters.put("type", null);
        }

        if (photo.getUrl() != null) {
            namedParameters.put("url", photo.getUrl());
        } else {
            namedParameters.put("url", null);
        }

        return namedParameters;
    }

    private static class PhotoRowMapper implements RowMapper<Photo> {

        @Override
        public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Photo photo = new Photo();

            photo.setId(rs.getLong("photo_id"));
            photo.setType(Photo.Type.valueOf(rs.getString("type")));

            photo.setUrl(rs.getString("url"));

            return photo;
        }
    }
}
