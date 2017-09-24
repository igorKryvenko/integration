package com.kyivstar.common.dao.repository;

import com.kyivstar.common.dao.entity.Melody;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MelodyRepository extends MongoRepository<Melody,String> {
}
