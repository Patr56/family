package pro.horoshilov.family.service;

import java.util.List;

import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.repository.IRepository;
import pro.horoshilov.family.repository.impl.PersonRepository;
import pro.horoshilov.family.repository.specification.impl.PersonFindAllSpecification;
import pro.horoshilov.family.repository.specification.impl.PersonFindByIdSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personService")
public class PersonService {

    private IRepository<Person> personRepository;

    @Autowired
    PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Long add(final Person person) throws EmptyInsertIdException {
        return personRepository.add(person);
    }

    public void update(final Person person) throws NotFoundEntityException {
        final int count = personRepository.update(
                person,
                new PersonFindByIdSpecification(person.getId())
        );
        if (count == 0) {
            throw new NotFoundEntityException(String.format("Person with id: %s not found for updating.", person.getId()));
        }
    }

    public void remove(final Person person) throws NotFoundEntityException {
        final int count = personRepository.remove(new PersonFindByIdSpecification(person.getId()));
        if (count == 0) {
            throw new NotFoundEntityException(String.format("Person with id: %s not found for deleting.", person.getId()));
        }
    }

    public List<Person> findAll() {
        return personRepository.query(new PersonFindAllSpecification());
    }

    public Person findById(final Long personId) throws NotFoundEntityException, FoundTooManyEntityException {
        final List<Person> result = personRepository.query(new PersonFindByIdSpecification(personId));

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
