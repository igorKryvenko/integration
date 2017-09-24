package com.kyivstar.common.dao.repository;

import com.kyivstar.common.dao.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by igor on 24.09.17.
 */
public interface UserRepository extends MongoRepository<User,String> {
}
