package pro.horoshilov.family.repository;

import java.util.List;
import java.util.Map;

import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.repository.specification.ISqlSpecification;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public abstract class AbstractRepository<T> implements IRepository<T> {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected abstract String getSqlSelect();
    protected abstract String getSqlInsert();
    protected abstract String getSqlUpdate();
    protected abstract String getSqlDelete();
    protected abstract String getEntityName();
    protected abstract RowMapper<T> getRowMapper();
    protected abstract Map<String, Object> getParams(T entity);

    public AbstractRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Long add(T entity) throws EmptyInsertIdException {
        final Map<String, Object> namedParameters = getParams(entity);
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource(namedParameters);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(getSqlInsert(), sqlParameterSource, keyHolder);
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new EmptyInsertIdException(String.format("Can't return %s id.", getEntityName()));
        }
    }

    @Override
    public int update(T entity, ISqlSpecification specification) {
        final Map<String, Object> namedParameters = getParams(entity);
        namedParameters.putAll(specification.getParamMap());

        return namedParameterJdbcTemplate.update(specification.toSqlClauses(getSqlUpdate()), namedParameters);
    }

    @Override
    public int remove(ISqlSpecification specification) {
        return namedParameterJdbcTemplate.update(specification.toSqlClauses(getSqlDelete()), specification.getParamMap());
    }

    @Override
    public List<T> query(ISqlSpecification specification) {
        return namedParameterJdbcTemplate.query(
                specification.toSqlClauses(getSqlSelect()),
                specification.getParamMap(),
                getRowMapper()
        );
    }
}
