package com.example.todo.repository

import com.example.todo.config.AppConfig
import com.example.todo.database.Todo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TodoRepositoryImpl::class, AppConfig::class])
class TodoRepositoryTest {

    @Autowired
    lateinit var todoRepositoryImpl: TodoRepositoryImpl

    @BeforeEach // 각 테스트 메소드 호출 전 before() 메소드 실행
    // 원래 데이터베이스를 사용할 때에는 초기화 어노테이션 존재, but 우리가 만든
    // 데이터베이스 이므로 직접 구현한 init() 메소드 호출
    fun before(){
        todoRepositoryImpl.todoDataBase.init()
    }

    @Test
    fun saveTest(){
        val todo = Todo().apply {
            this.title = "테스트 일정"
            this.description = "테스트"
            this.schedule = LocalDateTime.now()
        }

        val result = todoRepositoryImpl.save(todo)

        Assertions.assertEquals(1, result?.index)
        Assertions.assertNotNull(result?.createdAt)
        Assertions.assertNotNull(result?.updatedAt)
        Assertions.assertEquals("테스트 일정",result?.title)
        Assertions.assertEquals("테스트",result?.description)
    }

    @Test
    fun saveAllTest(){
        val todoList = mutableListOf(
            Todo().apply {
                this.title = "테스트 일정"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            }
        )
        val result = todoRepositoryImpl.saveAll(todoList)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun findOneTest(){

        val todoList = mutableListOf(
            Todo().apply {
                this.title = "테스트 일정1"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정2"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정3"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            }
        )

        todoRepositoryImpl.saveAll(todoList)
        val result = todoRepositoryImpl.findOne(2)

        Assertions.assertNotNull(result)
        Assertions.assertEquals("테스트 일정2", result?.title)
    }

    @Test
    fun updateTest(){
        val todo = Todo().apply {
            this.title = "테스트 일정"
            this.description = "테스트"
            this.schedule = LocalDateTime.now()
        }

        val insertTodo = todoRepositoryImpl.save(todo)

        val newTodo = Todo().apply {
            this.index = insertTodo?.index
            this.title = "테스트 일정 업데이트"
            this.description = "테스트 업데이트"
            this.schedule = LocalDateTime.now()
        }

        val result = todoRepositoryImpl.save(newTodo)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(insertTodo?.index, result?.index)
        Assertions.assertEquals("테스트 일정 업데이트", result?.title)
        Assertions.assertEquals("테스트 업데이트", result?.description)

    }
}