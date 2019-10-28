package pro.horoshilov.family.service;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.horoshilov.family.entity.Photo;
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
public class PhotoServiceTest {

    @Autowired
    private PhotoService photoService;

    @BeforeClass
    @FlywayTest
    public static void before() {

    }

    @Test
    public void testAdd() throws EmptyInsertIdException {
        final Long id = photoService.add(GeneratorUtil.generatePhoto());

        assertThat(id).isGreaterThan(0);
    }

    @Test
    public void testFindById() throws EmptyInsertIdException, NotFoundEntityException, FoundTooManyEntityException {
        final Photo photo = GeneratorUtil.generatePhoto();
        final Long photoId = photoService.add(photo);
        photo.setId(photoId);

        final Photo photoFromDb = photoService.findById(photoId);
        assertThat(photoFromDb).isEqualTo(photo);
    }

    @Test
    public void testUpdate() throws EmptyInsertIdException, NotFoundEntityException, FoundTooManyEntityException {
        final Photo photo = new Photo();
        photo.setType(Photo.Type.FRONT);
        photo.setUrl("wrong");
        final Long photoId = photoService.add(photo);
        photo.setId(photoId);

        final Photo photoFromDb = photoService.findById(photoId);
        assertThat(photoFromDb).isEqualTo(photo);
        
        final Photo photoForUpdate = new Photo();
        photoForUpdate.setUrl("right");
        photoForUpdate.setType(photo.getType());
        photoForUpdate.setId(photo.getId());
        photoService.update(photoForUpdate);

        final Photo photoFromDbAfterUpdate = photoService.findById(photoId);
        assertThat(photoFromDbAfterUpdate).isEqualTo(photoForUpdate);
    }

    @Test(expected = NotFoundEntityException.class)
    public void testRemove() throws EmptyInsertIdException, NotFoundEntityException, FoundTooManyEntityException {
        final Photo photo = GeneratorUtil.generatePhoto();
        final Long photoId = photoService.add(photo);

        photo.setId(photoId);
        photoService.remove(photo);
        photoService.findById(photoId);
    }
}
