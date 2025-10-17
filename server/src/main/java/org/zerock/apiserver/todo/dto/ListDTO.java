package org.zerock.apiserver.todo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListDTO<E> {
  
  private List<E> list;

  private int total;

}
