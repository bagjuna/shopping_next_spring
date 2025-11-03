package org.zerock.apiserver.domain.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.apiserver.domain.todo.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer>{
  
  



}
