package org.zerock.apiserver.todo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


@Data
public class TodoDTO {
  
  private Integer tno;

  private String title;

  private String writer;

  private boolean completed;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdDate;

  public TodoDTO(Integer tno, String title, String writer, boolean completed, LocalDateTime createdDate) {
    this.tno = tno;
    this.title = title;
    this.writer = writer;
    this.completed = completed;
    this.createdDate = createdDate;
  }

}