package pro.horoshilov.family.controller;

import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.entity.BaseResponse;
import pro.horoshilov.family.entity.PhotoInformation;
import pro.horoshilov.family.entity.SuccessResponse;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.exception.RequestParamException;
import pro.horoshilov.family.service.PhotoInformationService;

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

@RestController("photoInformationController")
@RequestMapping(value = "photo-information")
public class PhotoInformationController {

    private PhotoInformationService photoInformationService;

    @Autowired
    PhotoInformationController(final PhotoInformationService photoInformationService) {
        this.photoInformationService = photoInformationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse add(@RequestBody final PhotoInformation photoInformation) throws EmptyInsertIdException {
        final Map<String, Long> result = new HashMap<>();
        final Long id = photoInformationService.add(photoInformation);
        result.put("id", id);

        return new SuccessResponse<>(result);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse remove(@RequestBody final PhotoInformation photoInformation) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        photoInformationService.remove(photoInformation);
        result.put("id", photoInformation.getId());

        return new SuccessResponse<>(result);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody final PhotoInformation photoInformation) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        photoInformationService.update(photoInformation);
        result.put("id", photoInformation.getId());

        return new SuccessResponse<>(result);
    }

    @GetMapping(path = "{photoInformationId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse findById(
            @PathVariable("photoInformationId") final Long photoInformationId
    ) throws FoundTooManyEntityException, NotFoundEntityException, RequestParamException {

        if (photoInformationId == null) {
            throw new RequestParamException("Param photoInformationId not set");
        }

        final Map<String, PhotoInformation> result = new HashMap<>();
        final PhotoInformation photoInformation = photoInformationService.findById(photoInformationId);
        result.put("photoInformation", photoInformation);

        return new SuccessResponse<>(result);
    }
}
