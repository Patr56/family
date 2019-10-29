package pro.horoshilov.family.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.entity.Photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("photoRepository")
public class PhotoRepository extends AbstractRepository<Photo> {

    private final static String ENTITY_NAME = Photo.class.getName();

    // language=sql
    private final static String SQL_INSERT =
            "insert into photo ( \n" +
            "       url, \n" +
            "       type) \n" +
            "values (:url, \n" +
            "        :type)";

    // language=sql
    private final static String SQL_UPDATE =
            "update photo p \n" +
            "   set p.url = :url, \n" +
            "       p.type = :type \n";

    //language=sql
    private final static String SQL_SELECT =
            "select p.photo_id, \n" +
            "       p.url, \n" +
            "       p.type \n" +
            "  from photo p\n";

    //language=sql
    private final static String SQL_DELETE = "delete from photo p \n";

    @Autowired
    PhotoRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
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
    protected RowMapper<Photo> getRowMapper() {
        return new PhotoRowMapper();
    }

    protected Map<String, Object> getParams(final Photo photo) {
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
