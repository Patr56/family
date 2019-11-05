package pro.horoshilov.family.controller;

import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.entity.BaseResponse;
import pro.horoshilov.family.entity.Photo;
import pro.horoshilov.family.entity.SuccessResponse;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.exception.RequestParamException;
import pro.horoshilov.family.service.PhotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("photoController")
@RequestMapping(value = "photo")
public class PhotoController {

    private PhotoService photoService;

    @Autowired
    PhotoController(final PhotoService personService) {
        this.photoService = personService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse add(@RequestBody final Photo photo) throws EmptyInsertIdException {
        final Map<String, Long> result = new HashMap<>();
        final Long id = photoService.add(photo);
        result.put("id", id);

        return new SuccessResponse<>(result);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse remove(@RequestBody final Photo photo) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        photoService.remove(photo);
        result.put("id", photo.getId());

        return new SuccessResponse<>(result);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody final Photo photo) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        photoService.update(photo);
        result.put("id", photo.getId());

        return new SuccessResponse<>(result);
    }

    @GetMapping(path = "{photoId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse findById(
            @PathVariable("photoId") final Long photoId
    ) throws FoundTooManyEntityException, NotFoundEntityException, RequestParamException {

        if (photoId == null) {
            throw new RequestParamException("Param photoId not set");
        }

        final Map<String, Photo> result = new HashMap<>();
        final Photo photo = photoService.findById(photoId);
        result.put("photo", photo);

        return new SuccessResponse<>(result);
    }
}
