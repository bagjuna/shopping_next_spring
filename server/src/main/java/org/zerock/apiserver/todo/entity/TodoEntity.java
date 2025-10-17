package org.zerock.apiserver.todo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_todo_next")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class TodoEntity{

  //모든 엔티티는 반드시 Id가 존재 
  //PK는 반드시 객체타입(기본자료형 사용불가)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer tno;
  
  @Column(nullable = false, length = 300)
  private String title;

  private boolean completed;

  @Column(nullable = false, length = 100)
  private String writer;

  private LocalDateTime createdDate;

  @PrePersist
  public void prePersist() {
      this.createdDate = LocalDateTime.now();
  }

  //JPA는 가능하면 엔티티 객체를 readonly로 하는 것을 권장 
  //변경하고 싶을때는 setxxx가 아니라 별도의 메서드를 이용함(필수는 아님)
  public void changeTitle(String title){
    this.title = title;
  }

  public void changeCompleted(boolean completed) {
    this.completed = completed;
  }

}