package pro.horoshilov.family.repository;

import java.util.List;

import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.repository.specification.ISqlSpecification;

public interface IRepository<Entity> {
    Long add(Entity entity) throws EmptyInsertIdException;
    int update(Entity entity, ISqlSpecification specification);
    int remove(ISqlSpecification specification);
    List<Entity> query(ISqlSpecification specification);
}
