package pro.horoshilov.family.controller;

import java.util.HashMap;
import java.util.Map;

import pro.horoshilov.family.entity.BaseResponse;
import pro.horoshilov.family.entity.ContactInformation;
import pro.horoshilov.family.entity.SuccessResponse;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.service.ContactInformationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
            @RequestParam("personId") final Long personId,
            @RequestParam("contactInformation") final ContactInformation contactInformation
    ) throws EmptyInsertIdException {
        final Map<String, Long> result = new HashMap<>();
        final Long id = contactInformationService.add(personId, contactInformation);
        result.put("id", personId);

        return new SuccessResponse<>(result);
    }

}
