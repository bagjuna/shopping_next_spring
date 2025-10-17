package org.zerock.apiserver.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.todo.entity.TodoEntity;
import org.zerock.apiserver.todo.repository.TodoRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
@Transactional
public class TodoRepositoryTests {
  
  @Autowired
  private TodoRepository repository;

  @Test
  @Commit
  public void testInserts() {

    for (int i = 1; i <= 100 ; i++) {
      
      TodoEntity entity = TodoEntity.builder()
      .title("Title..." + i)
      .writer("user00")
      .build();

      repository.save(entity);

      log.info(entity);

    }


  }

}
