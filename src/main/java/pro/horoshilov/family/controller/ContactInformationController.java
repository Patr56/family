package pro.horoshilov.family.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.horoshilov.family.entity.BaseResponse;
import pro.horoshilov.family.entity.ContactInformation;
import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.entity.SuccessResponse;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.exception.RequestParamException;
import pro.horoshilov.family.service.ContactInformationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("contactInformationController")
@RequestMapping(value = "contact-information")
public class ContactInformationController {

    private ContactInformationService contactInformationService;

    @Autowired
    ContactInformationController(final ContactInformationService contactInformationService) {
        this.contactInformationService = contactInformationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse add(@RequestBody final ContactInformation contactInformation) throws EmptyInsertIdException {
        final Map<String, Long> result = new HashMap<>();
        final Long id = contactInformationService.add(contactInformation);
        result.put("id", id);

        return new SuccessResponse<>(result);
    }

    @DeleteMapping(path = "by-person", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse removeByPerson(@RequestBody final Person person) {
        final Map<String, Long> result = new HashMap<>();
        contactInformationService.remove(person);
        result.put("id", person.getId());

        return new SuccessResponse<>(result);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse remove(@RequestBody final ContactInformation contactInformation) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        contactInformationService.remove(contactInformation);
        result.put("id", contactInformation.getId());

        return new SuccessResponse<>(result);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody final ContactInformation contactInformation) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        contactInformationService.update(contactInformation);
        result.put("id", contactInformation.getId());

        return new SuccessResponse<>(result);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse findAll(@RequestBody final Long personId) throws RequestParamException {

        if (personId == null) {
            throw new RequestParamException("Param personId not set");
        }

        final Map<String, List<ContactInformation>> result = new HashMap<>();
        final List<ContactInformation> contactInformationList = contactInformationService.findAll(personId);
        result.put("contactInformation", contactInformationList);

        return new SuccessResponse<>(result);
    }

}
