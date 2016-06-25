package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Hipster;
import com.mycompany.myapp.repository.HipsterRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Band;

/**
 * Test class for the HipsterResource REST controller.
 *
 * @see HipsterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class HipsterResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";

    private static final Band DEFAULT_SHIRT = Band.kraftclub;
    private static final Band UPDATED_SHIRT = Band.arcticmonkeys;

    private static final Integer DEFAULT_AGE = 17;
    private static final Integer UPDATED_AGE = 18;

    private static final byte[] DEFAULT_AVATAR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AVATAR = TestUtil.createByteArray(5000000, "1");
    private static final String DEFAULT_AVATAR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AVATAR_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_REALLY_HIPSTER = false;
    private static final Boolean UPDATED_REALLY_HIPSTER = true;

    private static final Double DEFAULT_SAMPLE_FIELD = 1D;
    private static final Double UPDATED_SAMPLE_FIELD = 2D;

    @Inject
    private HipsterRepository hipsterRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHipsterMockMvc;

    private Hipster hipster;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HipsterResource hipsterResource = new HipsterResource();
        ReflectionTestUtils.setField(hipsterResource, "hipsterRepository", hipsterRepository);
        this.restHipsterMockMvc = MockMvcBuilders.standaloneSetup(hipsterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        hipsterRepository.deleteAll();
        hipster = new Hipster();
        hipster.setName(DEFAULT_NAME);
        hipster.setShirt(DEFAULT_SHIRT);
        hipster.setAge(DEFAULT_AGE);
        hipster.setAvatar(DEFAULT_AVATAR);
        hipster.setAvatarContentType(DEFAULT_AVATAR_CONTENT_TYPE);
        hipster.setBirthday(DEFAULT_BIRTHDAY);
        hipster.setReallyHipster(DEFAULT_REALLY_HIPSTER);
        hipster.setSampleField(DEFAULT_SAMPLE_FIELD);
    }

    @Test
    public void createHipster() throws Exception {
        int databaseSizeBeforeCreate = hipsterRepository.findAll().size();

        // Create the Hipster

        restHipsterMockMvc.perform(post("/api/hipsters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hipster)))
                .andExpect(status().isCreated());

        // Validate the Hipster in the database
        List<Hipster> hipsters = hipsterRepository.findAll();
        assertThat(hipsters).hasSize(databaseSizeBeforeCreate + 1);
        Hipster testHipster = hipsters.get(hipsters.size() - 1);
        assertThat(testHipster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHipster.getShirt()).isEqualTo(DEFAULT_SHIRT);
        assertThat(testHipster.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testHipster.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testHipster.getAvatarContentType()).isEqualTo(DEFAULT_AVATAR_CONTENT_TYPE);
        assertThat(testHipster.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testHipster.isReallyHipster()).isEqualTo(DEFAULT_REALLY_HIPSTER);
        assertThat(testHipster.getSampleField()).isEqualTo(DEFAULT_SAMPLE_FIELD);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hipsterRepository.findAll().size();
        // set the field null
        hipster.setName(null);

        // Create the Hipster, which fails.

        restHipsterMockMvc.perform(post("/api/hipsters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hipster)))
                .andExpect(status().isBadRequest());

        List<Hipster> hipsters = hipsterRepository.findAll();
        assertThat(hipsters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkShirtIsRequired() throws Exception {
        int databaseSizeBeforeTest = hipsterRepository.findAll().size();
        // set the field null
        hipster.setShirt(null);

        // Create the Hipster, which fails.

        restHipsterMockMvc.perform(post("/api/hipsters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hipster)))
                .andExpect(status().isBadRequest());

        List<Hipster> hipsters = hipsterRepository.findAll();
        assertThat(hipsters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAvatarIsRequired() throws Exception {
        int databaseSizeBeforeTest = hipsterRepository.findAll().size();
        // set the field null
        hipster.setAvatar(null);

        // Create the Hipster, which fails.

        restHipsterMockMvc.perform(post("/api/hipsters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hipster)))
                .andExpect(status().isBadRequest());

        List<Hipster> hipsters = hipsterRepository.findAll();
        assertThat(hipsters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBirthdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = hipsterRepository.findAll().size();
        // set the field null
        hipster.setBirthday(null);

        // Create the Hipster, which fails.

        restHipsterMockMvc.perform(post("/api/hipsters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hipster)))
                .andExpect(status().isBadRequest());

        List<Hipster> hipsters = hipsterRepository.findAll();
        assertThat(hipsters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkReallyHipsterIsRequired() throws Exception {
        int databaseSizeBeforeTest = hipsterRepository.findAll().size();
        // set the field null
        hipster.setReallyHipster(null);

        // Create the Hipster, which fails.

        restHipsterMockMvc.perform(post("/api/hipsters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(hipster)))
                .andExpect(status().isBadRequest());

        List<Hipster> hipsters = hipsterRepository.findAll();
        assertThat(hipsters).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllHipsters() throws Exception {
        // Initialize the database
        hipsterRepository.save(hipster);

        // Get all the hipsters
        restHipsterMockMvc.perform(get("/api/hipsters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(hipster.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shirt").value(hasItem(DEFAULT_SHIRT.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].avatarContentType").value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].avatar").value(hasItem(Base64Utils.encodeToString(DEFAULT_AVATAR))))
                .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
                .andExpect(jsonPath("$.[*].reallyHipster").value(hasItem(DEFAULT_REALLY_HIPSTER.booleanValue())))
                .andExpect(jsonPath("$.[*].sampleField").value(hasItem(DEFAULT_SAMPLE_FIELD.doubleValue())));
    }

    @Test
    public void getHipster() throws Exception {
        // Initialize the database
        hipsterRepository.save(hipster);

        // Get the hipster
        restHipsterMockMvc.perform(get("/api/hipsters/{id}", hipster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(hipster.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shirt").value(DEFAULT_SHIRT.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.avatarContentType").value(DEFAULT_AVATAR_CONTENT_TYPE))
            .andExpect(jsonPath("$.avatar").value(Base64Utils.encodeToString(DEFAULT_AVATAR)))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.reallyHipster").value(DEFAULT_REALLY_HIPSTER.booleanValue()))
            .andExpect(jsonPath("$.sampleField").value(DEFAULT_SAMPLE_FIELD.doubleValue()));
    }

    @Test
    public void getNonExistingHipster() throws Exception {
        // Get the hipster
        restHipsterMockMvc.perform(get("/api/hipsters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateHipster() throws Exception {
        // Initialize the database
        hipsterRepository.save(hipster);
        int databaseSizeBeforeUpdate = hipsterRepository.findAll().size();

        // Update the hipster
        Hipster updatedHipster = new Hipster();
        updatedHipster.setId(hipster.getId());
        updatedHipster.setName(UPDATED_NAME);
        updatedHipster.setShirt(UPDATED_SHIRT);
        updatedHipster.setAge(UPDATED_AGE);
        updatedHipster.setAvatar(UPDATED_AVATAR);
        updatedHipster.setAvatarContentType(UPDATED_AVATAR_CONTENT_TYPE);
        updatedHipster.setBirthday(UPDATED_BIRTHDAY);
        updatedHipster.setReallyHipster(UPDATED_REALLY_HIPSTER);
        updatedHipster.setSampleField(UPDATED_SAMPLE_FIELD);

        restHipsterMockMvc.perform(put("/api/hipsters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedHipster)))
                .andExpect(status().isOk());

        // Validate the Hipster in the database
        List<Hipster> hipsters = hipsterRepository.findAll();
        assertThat(hipsters).hasSize(databaseSizeBeforeUpdate);
        Hipster testHipster = hipsters.get(hipsters.size() - 1);
        assertThat(testHipster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHipster.getShirt()).isEqualTo(UPDATED_SHIRT);
        assertThat(testHipster.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testHipster.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testHipster.getAvatarContentType()).isEqualTo(UPDATED_AVATAR_CONTENT_TYPE);
        assertThat(testHipster.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testHipster.isReallyHipster()).isEqualTo(UPDATED_REALLY_HIPSTER);
        assertThat(testHipster.getSampleField()).isEqualTo(UPDATED_SAMPLE_FIELD);
    }

    @Test
    public void deleteHipster() throws Exception {
        // Initialize the database
        hipsterRepository.save(hipster);
        int databaseSizeBeforeDelete = hipsterRepository.findAll().size();

        // Get the hipster
        restHipsterMockMvc.perform(delete("/api/hipsters/{id}", hipster.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Hipster> hipsters = hipsterRepository.findAll();
        assertThat(hipsters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
