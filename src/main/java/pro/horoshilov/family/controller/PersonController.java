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
import pro.horoshilov.family.exception.RequestParamException;
import pro.horoshilov.family.service.PersonService;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("personController")
@RequestMapping(value = "person")
public class PersonController {

    private PersonService personService;

    @Autowired
    PersonController(final PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse add(@RequestBody final Person person) throws EmptyInsertIdException {
        final Map<String, Long> result = new HashMap<>();
        final Long id = personService.add(person);
        result.put("id", id);

        return new SuccessResponse<>(result);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse remove(@RequestBody final Person person) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        personService.remove(person);
        result.put("id", person.getId());

        return new SuccessResponse<>(result);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody final Person person) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        personService.update(person);
        result.put("id", person.getId());

        return new SuccessResponse<>(result);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse findAll() {
        final Map<String, List<Person>> result = new HashMap<>();
        final List<Person> persons = personService.findAll();
        result.put("person", persons);

        return new SuccessResponse<>(result);
    }

    @GetMapping(path = "{personId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse findById(
            @PathVariable("personId") final Long personId
    ) throws FoundTooManyEntityException, NotFoundEntityException, RequestParamException {

        if (personId == null) {
            throw new RequestParamException("Param personId not set");
        }

        final Map<String, Person> result = new HashMap<>();
        final Person person = personService.findById(personId);
        result.put("person", person);

        return new SuccessResponse<>(result);
    }
}
