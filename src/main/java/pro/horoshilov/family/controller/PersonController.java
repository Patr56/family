package pro.horoshilov.family.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.horoshilov.family.entity.BaseResponse;
import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.entity.SuccessResponse;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public BaseResponse insert(@RequestParam final Person person) throws EmptyInsertIdException {
        final Map<String, Long> result = new HashMap<>();
        final Long personId = personService.add(person);
        result.put("personId", personId);

        return new SuccessResponse<>(result);
    }

    @DeleteMapping
    public BaseResponse remove(@RequestParam final Person person) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        personService.remove(person);
        result.put("personId", person.getId());

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
    public BaseResponse findById(@PathVariable("personId") final Long personId) throws FoundTooManyEntityException, NotFoundEntityException {
        final Map<String, Person> result = new HashMap<>();
        final Person person = personService.findById(personId);
        result.put("person", person);

        return new SuccessResponse<>(result);
    }
}
