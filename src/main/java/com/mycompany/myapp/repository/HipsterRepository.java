package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Hipster;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Hipster entity.
 */
@SuppressWarnings("unused")
public interface HipsterRepository extends MongoRepository<Hipster,String> {

}
