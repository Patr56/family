package pro.horoshilov.family.service;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.horoshilov.family.entity.PhotoInformation;
import pro.horoshilov.family.exception.EmptyInsertIdException;
import pro.horoshilov.family.exception.FoundTooManyEntityException;
import pro.horoshilov.family.exception.NotFoundEntityException;
import pro.horoshilov.family.helper.GeneratorUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@TestPropertySource(locations = "classpath:test.properties")
public class PhotoInformationServiceTest {

    @Autowired
    private PhotoInformationService photoInformationService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PersonService personService;

    @BeforeClass
    @FlywayTest
    public static void before() {

    }

    @Test
    public void testAdd() throws EmptyInsertIdException {
        final Long photoId = photoService.add(GeneratorUtil.generatePhoto());
        final Long personId = personService.add(GeneratorUtil.generatePerson());

        final Long id = photoInformationService.add(GeneratorUtil.generatePhotoInformation(personId, photoId));

        assertThat(id).isGreaterThan(0);
    }

    @Test
    public void testFindById() throws EmptyInsertIdException, NotFoundEntityException, FoundTooManyEntityException {
        final Long photoId = photoService.add(GeneratorUtil.generatePhoto());
        final Long personId = personService.add(GeneratorUtil.generatePerson());

        final PhotoInformation photoInformation = GeneratorUtil.generatePhotoInformation(personId, photoId);
        final Long id = photoInformationService.add(photoInformation);
        photoInformation.setId(id);


        final PhotoInformation photoInformationFromDb = photoInformationService.findById(id);
        assertThat(photoInformationFromDb).isEqualTo(photoInformation);
    }

    @Test
    public void testUpdate() throws EmptyInsertIdException, NotFoundEntityException, FoundTooManyEntityException {
        final Long photoId = photoService.add(GeneratorUtil.generatePhoto());
        final Long personId = personService.add(GeneratorUtil.generatePerson());

        final PhotoInformation photoInformation = new PhotoInformation();
        PhotoInformation.Area.Coordinate topLeft = new PhotoInformation.Area.Coordinate(0d, 0d);
        PhotoInformation.Area.Coordinate bottomRight = new PhotoInformation.Area.Coordinate(1d, 1d);
        PhotoInformation.Area area = new PhotoInformation.Area(topLeft, bottomRight);

        photoInformation.setPersonId(personId);
        photoInformation.setPhotoId(photoId);
        photoInformation.setArea(area);
        photoInformation.setDescription("description wrong");

        final Long photoInformationId = photoInformationService.add(photoInformation);
        photoInformation.setId(photoInformationId);

        final PhotoInformation photoInformationFromDb = photoInformationService.findById(photoInformationId);
        assertThat(photoInformationFromDb).isEqualTo(photoInformation);

        final Long newPhotoId = photoService.add(GeneratorUtil.generatePhoto());
        final PhotoInformation photoInformationForUpdate = new PhotoInformation();
        PhotoInformation.Area.Coordinate topLeftForUpdate = new PhotoInformation.Area.Coordinate(2d, 2d);
        PhotoInformation.Area.Coordinate bottomRightForUpdate = new PhotoInformation.Area.Coordinate(3d, 3d);
        PhotoInformation.Area areaForUpdate = new PhotoInformation.Area(topLeftForUpdate, bottomRightForUpdate);
        photoInformationForUpdate.setId(photoInformationId);
        photoInformationForUpdate.setPersonId(personId);
        photoInformationForUpdate.setPhotoId(newPhotoId);
        photoInformationForUpdate.setArea(areaForUpdate);
        photoInformationForUpdate.setDescription("Description right");

        photoInformationService.update(photoInformationForUpdate);

        final PhotoInformation photoInformationFromDbAfterUpdate = photoInformationService.findById(photoInformationId);
        assertThat(photoInformationFromDbAfterUpdate).isEqualTo(photoInformationForUpdate);
    }

    @Test(expected = NotFoundEntityException.class)
    public void testRemove() throws EmptyInsertIdException, NotFoundEntityException, FoundTooManyEntityException {
        final Long photoId = photoService.add(GeneratorUtil.generatePhoto());
        final Long personId = personService.add(GeneratorUtil.generatePerson());

        final PhotoInformation photoInformation = GeneratorUtil.generatePhotoInformation(personId, photoId);
        final Long id = photoInformationService.add(photoInformation);;

        photoInformation.setId(id);
        photoInformationService.remove(photoInformation);
        photoInformationService.findById(id);
    }
}
