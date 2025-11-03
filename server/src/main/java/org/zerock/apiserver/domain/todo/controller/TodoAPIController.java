

package org.zerock.apiserver.domain.todo.controller;

import org.springframework.web.bind.annotation.*;
import org.zerock.apiserver.domain.todo.dto.ListDTO;
import org.zerock.apiserver.domain.todo.dto.TodoDTO;
import org.zerock.apiserver.domain.todo.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todos")
public class TodoAPIController {

  private final TodoService service;

  @GetMapping("{tno}")
  public TodoDTO getOne(@PathVariable("tno") Integer tno) {
    log.info("getOne...............");
    log.info(tno);


      return service.getOne(tno).get();
  }


  //curl -X POST http://localhost:8080/api/todos -F "title=Hello World" -F "writer=Zerock" -F "completed=false"
  @PostMapping("")
  public TodoDTO register(TodoDTO dto) {

    log.info("register...............");
    log.info(dto);

      
    return service.register(dto);
  }


  @GetMapping("list")
  public ListDTO<TodoDTO> getList(@RequestParam("page") int page) {


      return service.list(page);
  }

  //curl -X PUT http://localhost:8080/api/todos/1 -F "title=Hello World" -F "writer=Zerock" -F "completed=true"
  @PutMapping("{tno}")
  public TodoDTO modify(@PathVariable("tno") Integer tno, TodoDTO dto) {
    log.info("modify...............");
    log.info(dto);

    return service.modify(dto);
  }

  //curl -X DELETE http://localhost:8080/api/todos/1
  @DeleteMapping("{tno}")
  public Map<String, String> remove(@PathVariable("tno") Integer tno) {
    log.info("remove...............");
    log.info(tno);
    service.remove(tno);
    return Map.of("result", "success");
  }
  
  
}
