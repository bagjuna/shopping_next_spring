package org.zerock.apiserver.todo.service;

import java.util.Optional;

import org.zerock.apiserver.todo.dto.ListDTO;
import org.zerock.apiserver.todo.dto.TodoDTO;
import org.zerock.apiserver.todo.entity.TodoEntity;

public interface TodoService {
  
  TodoDTO register(TodoDTO todoDTO);

  Optional<TodoDTO> getOne(Integer tno);

  TodoDTO modify(TodoDTO todoDTO);

  void remove(Integer tno);

  ListDTO<TodoDTO> list(int page);

  default TodoDTO entityToDTO(TodoEntity todoEntity) {

    return new TodoDTO(
      todoEntity.getTno(), 
      todoEntity.getTitle(), 
      todoEntity.getWriter(),
      todoEntity.isCompleted(), 
      todoEntity.getCreatedDate());

  }

  default TodoEntity dtoTOEntity(TodoDTO todoDTO) {

    return TodoEntity.builder()
    .tno(todoDTO.getTno())
    .title(todoDTO.getTitle())
    .writer(todoDTO.getWriter())
    .completed(todoDTO.isCompleted())
    .createdDate(todoDTO.getCreatedDate())
    .build();
    
  }

}
