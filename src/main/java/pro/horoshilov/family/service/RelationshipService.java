package pro.horoshilov.family.service;

import java.util.List;

import pro.horoshilov.family.entity.Relationship;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.repository.IRepository;
import pro.horoshilov.family.repository.impl.RelationshipRepository;
import pro.horoshilov.family.repository.specification.impl.RelationshipFindByIdSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("relationshipService")
public class RelationshipService {

    private IRepository<Relationship> relationshipRepository;

    @Autowired
    RelationshipService(final RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }

    public Long add(final Relationship photo) throws EmptyInsertIdException {
        return relationshipRepository.add(photo);
    }

    public void update(final Relationship relationship) throws NotFoundEntityException {
        final int count = relationshipRepository.update(relationship, new RelationshipFindByIdSpecification(relationship.getId()));
        if (count == 0) {
            throw new NotFoundEntityException(String.format("Relationship with id: %s not found for updating.", relationship.getId()));
        }
    }

    public void remove(final Relationship relationship) throws NotFoundEntityException {
        final int count = relationshipRepository.remove(new RelationshipFindByIdSpecification(relationship.getId()));
        if (count == 0) {
            throw new NotFoundEntityException(String.format("Relationship with id: %s not found for deleting.", relationship.getId()));
        }
    }

    public Relationship findById(final Long relationshipId) throws NotFoundEntityException, FoundTooManyEntityException {
        final List<Relationship> result = relationshipRepository.query(new RelationshipFindByIdSpecification(relationshipId));

        final int size = result.size();

        if (size == 0) {
            throw new NotFoundEntityException(String.format("Relationship with id: %d not found.", relationshipId));
        } else if (size > 1) {
            throw new FoundTooManyEntityException(String.format("Relationship with id: %d found: %d. Must be one.", relationshipId, size));
        } else {
            return result.get(0);
        }
    }
}
