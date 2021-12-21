package com.japharr.springsparksample.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.Instant;

@Document
@Getter @Setter
@NoArgsConstructor
public class FileDesc implements Serializable {
  @Id
  private String id;

  @NotEmpty
  private String filename;
  private long size;
  private Instant syncedOn;

  public FileDesc(String filename, long size) {
    this.filename = filename;
    this.size = size;
  }

  public boolean isSynced() {
    return syncedOn != null;
  }
}
