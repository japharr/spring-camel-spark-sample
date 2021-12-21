package com.japharr.springsparksample.repository;

import com.japharr.springsparksample.entity.FileDesc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDescRepository extends MongoRepository<FileDesc, String> {
  List<FileDesc> findAllBySyncedOnIsNull();
}
