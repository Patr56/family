package pro.horoshilov.family.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.horoshilov.family.entity.BaseResponse;
import pro.horoshilov.family.entity.FailureResponse;
import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.entity.SuccessResponse;
import pro.horoshilov.family.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("person")
public class PersonController {

    private PersonService personService;

    @Autowired
    PersonController(final PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public BaseResponse insert(@RequestParam final Person person) throws Exception {
        final Map<String, Long> result = new HashMap<>();
        final Long personId = personService.insert(person);
        result.put("personId", personId);

        return new SuccessResponse<>(result);
    }

    @GetMapping
    public BaseResponse findAll() {
        final Map<String, List<Person>> result = new HashMap<>();
        final List<Person> persons = personService.findAll();
        result.put("person", persons);

        return new SuccessResponse<>(result);
    }

    @GetMapping("{personId}")
    public BaseResponse findById(@PathVariable("personId") final Long personId) throws Exception {
        final Map<String, Person> result = new HashMap<>();
        final Person person = personService.findById(personId);
        result.put("person", person);

        return new SuccessResponse<>(result);
    }
}
