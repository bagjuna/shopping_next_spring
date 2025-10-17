package org.zerock.apiserver.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.todo.dto.ListDTO;
import org.zerock.apiserver.todo.dto.TodoDTO;
import org.zerock.apiserver.todo.entity.TodoEntity;
import org.zerock.apiserver.todo.exceptions.TodoNotFoundException;
import org.zerock.apiserver.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;


@Transactional
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
 
  private final TodoRepository repository;


  @Override
  public TodoDTO register(TodoDTO todoDTO) {
    
    TodoEntity entity = dtoTOEntity(todoDTO);

    repository.save(entity);

    return entityToDTO(entity);

  }

  @Transactional(readOnly = true)
  @Override
  public Optional<TodoDTO> getOne(Integer tno) {
    
    Optional<TodoEntity> result = repository.findById(tno);

    TodoEntity entity  = result.orElseThrow(() -> new TodoNotFoundException("Not Found"));

    return Optional.of( entityToDTO(entity) );

  }

  @Override
  public TodoDTO modify(TodoDTO todoDTO) {

    TodoEntity entity = repository.findById(todoDTO.getTno())
            .orElseThrow(() -> new TodoNotFoundException("Not Found"));

    entity.changeCompleted(todoDTO.isCompleted());
    entity.changeTitle(todoDTO.getTitle());

    //dirty checking

    return entityToDTO(entity);

  }

  @Override
  public void remove(Integer tno) {

    TodoEntity entity = repository.findById(tno)
            .orElseThrow(() -> new TodoNotFoundException("Not Found"));

    repository.deleteById(tno);

  }

  @Transactional(readOnly = true)
  @Override
  public ListDTO<TodoDTO> list(int page) {
    
    Pageable pageable = PageRequest.of(page -1, 10, Sort.by("tno").descending());

    Page<TodoEntity> entityList = repository.findAll(pageable);
    
    List<TodoDTO> dtoList = entityList.getContent().stream().map(en -> entityToDTO(en)).toList();

    int total = (int)entityList.getTotalElements();

    return new ListDTO<>(dtoList, total);

  }

}