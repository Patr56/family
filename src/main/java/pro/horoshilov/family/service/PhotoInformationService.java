package pro.horoshilov.family.service;

import java.util.List;

import pro.horoshilov.family.entity.PhotoInformation;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.repository.IRepository;
import pro.horoshilov.family.repository.PhotoInformationRepository;
import pro.horoshilov.family.repository.specification.PhotoInformationFindByIdSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("photoInformationService")
public class PhotoInformationService {

    private IRepository<PhotoInformation> photoInformationRepository;

    @Autowired
    PhotoInformationService(final PhotoInformationRepository photoInformationRepository) {
        this.photoInformationRepository = photoInformationRepository;
    }

    public Long add(final PhotoInformation entity) throws EmptyInsertIdException {
        return photoInformationRepository.add(entity);
    }

    public void update(final PhotoInformation entity) throws NotFoundEntityException {
        final int count = photoInformationRepository.update(
                entity,
                new PhotoInformationFindByIdSpecification(entity.getId())
        );

        if (count == 0) {
            throw new NotFoundEntityException(String.format("PhotoInformation with id: %s not found for updating.", entity.getId()));
        }
    }

    public void remove(final PhotoInformation entity) throws NotFoundEntityException {
        final int count = photoInformationRepository.remove(new PhotoInformationFindByIdSpecification(entity.getId()));
        if (count == 0) {
            throw new NotFoundEntityException(String.format("PhotoInformation with id: %s not found for deleting.", entity.getId()));
        }
    }

    public PhotoInformation findById(final Long photoInformationId) throws NotFoundEntityException, FoundTooManyEntityException {
        final List<PhotoInformation> result = photoInformationRepository.query(new PhotoInformationFindByIdSpecification(photoInformationId));

        final int size = result.size();

        if (size == 0) {
            throw new NotFoundEntityException(String.format("PhotoInformation with id: %d not found.", photoInformationId));
        }
        else if (size > 1) {
            throw new FoundTooManyEntityException(String.format("PhotoInformation with id: %d found: %d. Must be one.", photoInformationId, size));
        }
        else {
            return result.get(0);
        }
    }

}
