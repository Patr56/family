package pro.horoshilov.family.service;

import java.util.List;

import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personService")
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Long insert(final Person person) throws Exception {
        return personRepository.insert(person);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(final Long personId) {
        return personRepository.findById(personId);
    }
}
