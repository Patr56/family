package pro.horoshilov.family.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pro.horoshilov.family.entity.BaseResponse;
import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.entity.Relationship;
import pro.horoshilov.family.entity.SuccessResponse;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.exception.RequestParamException;
import pro.horoshilov.family.service.PersonService;
import pro.horoshilov.family.service.RelationshipService;

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

@RestController("relationshipController")
@RequestMapping(value = "relationship")
public class RelationshipController {

    private RelationshipService relationshipService;

    @Autowired
    RelationshipController(final RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse add(@RequestBody final Relationship relationship) throws EmptyInsertIdException {
        final Map<String, Long> result = new HashMap<>();
        final Long id = relationshipService.add(relationship);
        result.put("id", id);

        return new SuccessResponse<>(result);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse remove(@RequestBody final Relationship relationship) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        relationshipService.remove(relationship);
        result.put("id", relationship.getId());

        return new SuccessResponse<>(result);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody final Relationship relationship) throws NotFoundEntityException {
        final Map<String, Long> result = new HashMap<>();
        relationshipService.update(relationship);
        result.put("id", relationship.getId());

        return new SuccessResponse<>(result);
    }

    @GetMapping(path = "{relationshipId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse findById(
            @PathVariable("relationshipId") final Long relationshipId
    ) throws FoundTooManyEntityException, NotFoundEntityException, RequestParamException {

        if (relationshipId == null) {
            throw new RequestParamException("Param relationshipId not set");
        }

        final Map<String, Relationship> result = new HashMap<>();
        final Relationship relationship = relationshipService.findById(relationshipId);
        result.put("relationship", relationship);

        return new SuccessResponse<>(result);
    }
}
