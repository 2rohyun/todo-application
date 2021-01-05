package com.example.todo.service

import com.example.todo.database.Todo
import com.example.todo.database.convertTodo
import com.example.todo.model.http.TodoDto
import com.example.todo.model.http.convertTodoDto
import com.example.todo.repository.TodoRepositoryImpl
import org.springframework.stereotype.Service

/*
*     현업에서는 자바의 경우 model mapper
*             코틀린의 경우 kotlin reflection을 통해
*           엔티티와 DTO간의 형 변환을 담당한다.
 */
@Service
class TodoService(var todoRepositoryImpl: TodoRepositoryImpl) {

    fun create(todoDto: TodoDto): TodoDto?{
        return todoDto.let {
            Todo().convertTodo(it)
        }.let {
            todoRepositoryImpl.save(it)
        }?.let {
            TodoDto().convertTodoDto(it)
        }
    }

    fun read(index: Int): TodoDto? {
        return todoRepositoryImpl.findOne(index)?.let{
            TodoDto().convertTodoDto(it)
        }
    }

    fun readAll(): MutableList<TodoDto> {
        return todoRepositoryImpl.findAll().map {
            TodoDto().convertTodoDto(it)
        }.toMutableList()
    }

    fun update(todoDto: TodoDto): TodoDto?{
        return todoDto.let {
            Todo().convertTodo(it)
        }.let {
            todoRepositoryImpl.save(it)
        }?.let {
            TodoDto().convertTodoDto(it)
        }
    }

    fun delete(index: Int): Boolean {
        return todoRepositoryImpl.delete(index)
    }




}