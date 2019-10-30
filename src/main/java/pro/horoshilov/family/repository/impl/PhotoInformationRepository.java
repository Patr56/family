package pro.horoshilov.family.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.entity.PhotoInformation;
import pro.horoshilov.family.repository.AbstractRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("photoInformationRepository")
public class PhotoInformationRepository extends AbstractRepository<PhotoInformation> {

    @Autowired
    PhotoInformationRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(namedParameterJdbcTemplate);
    }

    private final static String ENTITY_NAME = PhotoInformation.class.getName();

    // language=sql
    private final static String SQL_INSERT =
            " insert into photo_information ( \n" +
            "        photo_id, \n" +
            "        person_id, \n" +
            "        description, \n" +
            "        area_top_left_x, \n" +
            "        area_top_left_y, \n" +
            "        area_bottom_right_x, \n" +
            "        area_bottom_right_y)" +
            " values (" +
            "        :photo_id, \n" +
            "        :person_id, \n" +
            "        :description, \n" +
            "        :area_top_left_x, \n" +
            "        :area_top_left_y, \n" +
            "        :area_bottom_right_x, \n" +
            "        :area_bottom_right_y \n" +
            "        )";

    // language=sql
    private final static String SQL_UPDATE =
            "update photo_information pi \n" +
            "   set pi.photo_id=:photo_id, \n" +
            "       pi.person_id=:person_id, \n" +
            "       pi.description=:description, \n" +
            "       pi.area_top_left_x=:area_top_left_x, \n" +
            "       pi.area_top_left_y=:area_top_left_y, \n" +
            "       pi.area_bottom_right_x=:area_bottom_right_x, \n" +
            "       pi.area_bottom_right_y=:area_bottom_right_y\n";

    //language=sql
    private final static String SQL_DELETE = "delete from photo_information pi \n";

    //language=sql
    private final static String SQL_SELECT =
            "select pi.photo_information_id, \n" +
            "       pi.photo_id, \n" +
            "       pi.person_id, \n" +
            "       pi.description, \n" +
            "       pi.area_top_left_x, \n" +
            "       pi.area_top_left_y, \n" +
            "       pi.area_bottom_right_x, \n" +
            "       pi.area_bottom_right_y \n" +
            "  from photo_information pi";

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
    protected RowMapper<PhotoInformation> getRowMapper() {
        return new PhotoInformationRowMapper();
    }

    @Override
    protected Map<String, Object> getParams(PhotoInformation entity) {
        final Map<String, Object> namedParameters = new HashMap<>();

        namedParameters.put("photo_id", entity.getPersonId());
        namedParameters.put("person_id", entity.getPhotoId());
        namedParameters.put("description", entity.getDescription());

        if (entity.getArea() != null && entity.getArea().getTopLeft() != null ) {
            namedParameters.put("area_top_left_x", entity.getArea().getTopLeft().getX());
            namedParameters.put("area_top_left_y", entity.getArea().getTopLeft().getY());
        } else {
            namedParameters.put("area_top_left_x", null);
            namedParameters.put("area_top_left_y", null);
        }

        if (entity.getArea() != null && entity.getArea().getBottomRight() != null ) {
            namedParameters.put("area_bottom_right_x", entity.getArea().getBottomRight().getX());
            namedParameters.put("area_bottom_right_y", entity.getArea().getBottomRight().getY());
        } else {
            namedParameters.put("area_bottom_right_x", null);
            namedParameters.put("area_bottom_right_y", null);
        }

        return namedParameters;
    }

    private static class PhotoInformationRowMapper implements RowMapper<PhotoInformation> {

        @Override
        public PhotoInformation mapRow(ResultSet rs, int rowNum) throws SQLException {

            final Long photoInformationId = rs.getLong("photo_information_id");
            final Long photoId = rs.getLong("photo_id");
            final Long personId = rs.getLong("person_id");
            final String description = rs.getString("description");
            final Double areaTopLeftX = rs.getDouble("area_top_left_x");
            final Double areaTopLeftY = rs.getDouble("area_top_left_y");
            final Double areaBottomRightX = rs.getDouble("area_bottom_right_x");
            final Double areaBottomRightY = rs.getDouble("area_bottom_right_y");

            return new PhotoInformation(
                    photoInformationId,
                    photoId,
                    personId,
                    new PhotoInformation.Area(
                            new PhotoInformation.Area.Coordinate(areaTopLeftX, areaTopLeftY),
                            new PhotoInformation.Area.Coordinate(areaBottomRightX, areaBottomRightY)
                    ),
                    description
            );
        }
    }
}
