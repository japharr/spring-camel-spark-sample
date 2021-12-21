package com.japharr.springsparksample.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Getter @Setter
@NoArgsConstructor
public class FileDesc implements Serializable {
  @Id
  private String id;

  private String filename;
  private long size;

  public FileDesc(String filename, long size) {
    this.filename = filename;
    this.size = size;
  }
}
