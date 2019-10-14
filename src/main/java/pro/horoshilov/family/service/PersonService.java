package pro.horoshilov.family.service;

import java.util.List;

import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.repository.PersonRepository;
import pro.horoshilov.family.repository.specification.ISqlSpecification;
import pro.horoshilov.family.repository.specification.PersonFindAllSpecification;
import pro.horoshilov.family.repository.specification.PersonFindByIdSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personService")
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Long add(final Person person) throws EmptyInsertIdException {
        return personRepository.add(person);
    }

    public List<Person> findAll() {
        final ISqlSpecification sqlSpecification = new PersonFindAllSpecification();
        return personRepository.query(sqlSpecification);
    }

    public Person findById(final Long personId) throws NotFoundEntityException, FoundTooManyEntityException {
        final ISqlSpecification sqlSpecification = new PersonFindByIdSpecification(personId);
        final List<Person> result = personRepository.query(sqlSpecification);

        final int size = result.size();

        if (size == 0) {
            throw new NotFoundEntityException(String.format("Person with id: %d not found.", personId));
        } else if (size > 1) {
            throw new FoundTooManyEntityException(String.format("Person with id: %d found: %d. Must be one.", personId, size));
        } else {
            return result.get(0);
        }
    }
}
