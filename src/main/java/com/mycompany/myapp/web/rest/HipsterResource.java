package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Hipster;
import com.mycompany.myapp.repository.HipsterRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Hipster.
 */
@RestController
@RequestMapping("/api")
public class HipsterResource {

    private final Logger log = LoggerFactory.getLogger(HipsterResource.class);
        
    @Inject
    private HipsterRepository hipsterRepository;
    
    /**
     * POST  /hipsters : Create a new hipster.
     *
     * @param hipster the hipster to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hipster, or with status 400 (Bad Request) if the hipster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/hipsters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Hipster> createHipster(@Valid @RequestBody Hipster hipster) throws URISyntaxException {
        log.debug("REST request to save Hipster : {}", hipster);
        if (hipster.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hipster", "idexists", "A new hipster cannot already have an ID")).body(null);
        }
        Hipster result = hipsterRepository.save(hipster);
        return ResponseEntity.created(new URI("/api/hipsters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hipster", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hipsters : Updates an existing hipster.
     *
     * @param hipster the hipster to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hipster,
     * or with status 400 (Bad Request) if the hipster is not valid,
     * or with status 500 (Internal Server Error) if the hipster couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/hipsters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Hipster> updateHipster(@Valid @RequestBody Hipster hipster) throws URISyntaxException {
        log.debug("REST request to update Hipster : {}", hipster);
        if (hipster.getId() == null) {
            return createHipster(hipster);
        }
        Hipster result = hipsterRepository.save(hipster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hipster", hipster.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hipsters : get all the hipsters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hipsters in body
     */
    @RequestMapping(value = "/hipsters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Hipster> getAllHipsters() {
        log.debug("REST request to get all Hipsters");
        List<Hipster> hipsters = hipsterRepository.findAll();
        return hipsters;
    }

    /**
     * GET  /hipsters/:id : get the "id" hipster.
     *
     * @param id the id of the hipster to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hipster, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/hipsters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Hipster> getHipster(@PathVariable String id) {
        log.debug("REST request to get Hipster : {}", id);
        Hipster hipster = hipsterRepository.findOne(id);
        return Optional.ofNullable(hipster)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hipsters/:id : delete the "id" hipster.
     *
     * @param id the id of the hipster to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/hipsters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHipster(@PathVariable String id) {
        log.debug("REST request to delete Hipster : {}", id);
        hipsterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hipster", id.toString())).build();
    }

}
