package com.japharr.springsparksample.repository;

import com.japharr.springsparksample.entity.FileDesc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDescRepository extends MongoRepository<FileDesc, String> { }
