package pro.horoshilov.family.service;

import pro.horoshilov.family.entity.ContactInformation;
import pro.horoshilov.family.entity.Tuple;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.repository.ContactInformationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("contactInformationService")
public class ContactInformationService {

    private ContactInformationRepository contactInformationRepository;

    @Autowired
    ContactInformationService(final  ContactInformationRepository contactInformationRepository) {
        this.contactInformationRepository = contactInformationRepository;
    }

    public Long add(final Long personId, final ContactInformation contactInformation) throws EmptyInsertIdException {
        return contactInformationRepository.add(new Tuple<>(personId, contactInformation));
    }

}
