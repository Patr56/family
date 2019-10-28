package pro.horoshilov.family.service;

import java.util.List;

import pro.horoshilov.family.entity.Photo;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.repository.PhotoRepository;
import pro.horoshilov.family.repository.specification.PhotoFindByIdSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("photoService")
public class PhotoService {

    private PhotoRepository photoRepository;

    @Autowired
    PhotoService(final PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Long add(final Photo photo) throws EmptyInsertIdException {
        return photoRepository.add(photo);
    }

    public void update(final Photo photo) throws NotFoundEntityException {
        final int count = photoRepository.update(photo);
        if (count == 0) {
            throw new NotFoundEntityException(String.format("Photo with id: %s not found for updating.", photo.getId()));
        }
    }

    public void remove(final Photo photo) throws NotFoundEntityException {
        final int count = photoRepository.remove(photo);
        if (count == 0) {
            throw new NotFoundEntityException(String.format("Photo with id: %s not found for deleting.", photo.getId()));
        }
    }

    public Photo findById(final Long photoId) throws NotFoundEntityException, FoundTooManyEntityException {
        final List<Photo> result = photoRepository.query(new PhotoFindByIdSpecification(photoId));

        final int size = result.size();

        if (size == 0) {
            throw new NotFoundEntityException(String.format("Photo with id: %d not found.", photoId));
        } else if (size > 1) {
            throw new FoundTooManyEntityException(String.format("Photo with id: %d found: %d. Must be one.", photoId, size));
        } else {
            return result.get(0);
        }
    }
}
