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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contact-information")
public class ContactInformationController {

    private ContactInformationService contactInformationService;

    @Autowired
    ContactInformationController(final ContactInformationService contactInformationService) {
        this.contactInformationService = contactInformationService;
    }

    @PostMapping
    public BaseResponse add(
            @RequestParam("contactInformation") final ContactInformation contactInformation
    ) throws EmptyInsertIdException {
        final Map<String, Long> result = new HashMap<>();
        final Long id = contactInformationService.add(contactInformation);
        result.put("id", id);

        return new SuccessResponse<>(result);
    }

    @DeleteMapping
    public BaseResponse remove(
            @RequestParam("contactInformation") final ContactInformation contactInformation
    ) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        contactInformationService.remove(contactInformation);
        result.put("id", contactInformation.getId());

        return new SuccessResponse<>(result);
    }

    @PutMapping
    public BaseResponse update(
            @RequestParam("contactInformation") final ContactInformation contactInformation
    ) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        contactInformationService.update(contactInformation);
        result.put("id", contactInformation.getId());

        return new SuccessResponse<>(result);
    }

    @DeleteMapping
    public BaseResponse remove(@RequestParam("person") final Person person) {
        final Map<String, Long> result = new HashMap<>();
        contactInformationService.remove(person);
        result.put("id", person.getId());

        return new SuccessResponse<>(result);
    }

    @PostMapping
    public BaseResponse findAll(
            @RequestParam("personId") final Long personId
    ) throws RequestParamException {

        if (personId == null) {
            throw new RequestParamException("Param personId not set");
        }

        final Map<String, List<ContactInformation>> result = new HashMap<>();
        final List<ContactInformation> contactInformationList = contactInformationService.findAll(personId);
        result.put("contactInformation", contactInformationList);

        return new SuccessResponse<>(result);
    }

}
