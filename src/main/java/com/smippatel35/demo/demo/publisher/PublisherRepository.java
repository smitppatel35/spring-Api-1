package com.smippatel35.demo.demo.publisher;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends CrudRepository<PublisherEntity, Integer> {

}
