package pro.horoshilov.family.controller;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pro.horoshilov.family.entity.Person;
import pro.horoshilov.family.helper.GeneratorUtil;
import pro.horoshilov.family.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersonController.class, GlobalDefaultExceptionHandler.class})
@WebAppConfiguration
@EnableWebMvc
public class PersonControllerSpec {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private PersonService personService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testFindAllEmpty() throws Exception {
        given(personService.findAll()).willReturn(new LinkedList<>());

        mockMvc.perform(
                get("/person").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.person.length()").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true));
    }

    @Test
    public void testFindAll() throws Exception {
        final int COUNT_PERSON = 3;
        final List<Person> personList = GeneratorUtil.generatePersons(COUNT_PERSON);

        given(personService.findAll()).willReturn(personList);

        mockMvc.perform(
                get("/person").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.person.length()").value(COUNT_PERSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(
                delete("/person")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\": 0}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true));
    }
}
