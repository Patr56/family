package pro.horoshilov.family.service;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.horoshilov.family.entity.Relationship;
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
public class RelationshipServiceTest {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private PersonService personService;

    @BeforeClass
    @FlywayTest
    public static void before() {

    }

    @Test
    public void testAdd() throws EmptyInsertIdException {

        final Long personId = personService.add(GeneratorUtil.generatePerson());
        final Long relatedId = personService.add(GeneratorUtil.generatePerson());

        final Long id = relationshipService.add(GeneratorUtil.generateRelationship(personId, relatedId));

        assertThat(id).isGreaterThan(0);
    }

    @Test
    public void testFindById() throws EmptyInsertIdException, NotFoundEntityException, FoundTooManyEntityException {

        final Long personId = personService.add(GeneratorUtil.generatePerson());
        final Long relatedId = personService.add(GeneratorUtil.generatePerson());

        final Relationship relationship = GeneratorUtil.generateRelationship(personId, relatedId);
        final Long relationshipId = relationshipService.add(relationship);
        relationship.setId(relationshipId);

        final Relationship relationshipFromDb = relationshipService.findById(relationshipId);
        assertThat(relationshipFromDb).isEqualTo(relationship);
    }

    @Test
    public void testUpdate() throws EmptyInsertIdException, NotFoundEntityException, FoundTooManyEntityException {
        final Long personId = personService.add(GeneratorUtil.generatePerson());
        final Long relatedId = personService.add(GeneratorUtil.generatePerson());

        final Relationship relationship = new Relationship();

        relationship.setPersonId(personId);
        relationship.setRelatedId(relatedId);
        relationship.setType(Relationship.Type.MARRIAGE);

        final Long relationshipId = relationshipService.add(relationship);
        relationship.setId(relationshipId);

        final Relationship relationshipFromDb = relationshipService.findById(relationshipId);
        assertThat(relationshipFromDb).isEqualTo(relationship);

        final Relationship relationshipForUpdate = new Relationship();

        relationshipForUpdate.setId(relationshipId);
        relationshipForUpdate.setPersonId(relationship.getPersonId());
        relationshipForUpdate.setRelatedId(relationship.getRelatedId());
        relationshipForUpdate.setType(Relationship.Type.DIVORCE);

        relationshipService.update(relationshipForUpdate);

        final Relationship relationshipFromDbAfterUpdate = relationshipService.findById(relationshipId);
        assertThat(relationshipFromDbAfterUpdate).isEqualTo(relationshipForUpdate);
    }

    @Test(expected = NotFoundEntityException.class)
    public void testRemove() throws EmptyInsertIdException, NotFoundEntityException, FoundTooManyEntityException {
        final Long personId = personService.add(GeneratorUtil.generatePerson());
        final Long relatedId = personService.add(GeneratorUtil.generatePerson());

        final Relationship relationship = GeneratorUtil.generateRelationship(personId, relatedId);
        final Long relationshipId = relationshipService.add(relationship);

        relationship.setId(relationshipId);
        relationshipService.remove(relationship);
        relationshipService.findById(relationshipId);
    }
}
