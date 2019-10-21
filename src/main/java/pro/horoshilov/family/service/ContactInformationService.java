package pro.horoshilov.family.service;

import java.util.List;

import pro.horoshilov.family.entity.ContactInformation;
import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.repository.ContactInformationRepository;
import pro.horoshilov.family.repository.specification.ContactInformationFindAllSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("contactInformationService")
public class ContactInformationService {

    private ContactInformationRepository contactInformationRepository;

    @Autowired
    ContactInformationService(final ContactInformationRepository contactInformationRepository) {
        this.contactInformationRepository = contactInformationRepository;
    }

    public Long add(final ContactInformation contactInformation) throws EmptyInsertIdException {
        return contactInformationRepository.add(contactInformation);
    }

    public void update(final ContactInformation contactInformation) throws NotFoundEntityException {
        final int count = contactInformationRepository.update(contactInformation);
        if (count == 0) {
            throw new NotFoundEntityException(String.format("ContactInformation with id: %s not found for updating.", contactInformation.getId()));
        }
    }

    public void remove(final ContactInformation contactInformation) throws NotFoundEntityException {
        final int count = contactInformationRepository.remove(contactInformation);
        if (count == 0) {
            throw new NotFoundEntityException(String.format("ContactInformation with id: %s not found for deleting.", contactInformation.getId()));
        }
    }

    public void remove(final Person person) {
        contactInformationRepository.removeAll(person);
    }

    public List<ContactInformation> findAll(final Long personId) {
        return contactInformationRepository.query(new ContactInformationFindAllSpecification(personId));
    }

}
