package org.zerock.apiserver.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.apiserver.todo.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer>{
  
  



}
