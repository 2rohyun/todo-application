package com.example.todo.repository

import com.example.todo.database.Todo

interface TodoRepository {

    fun save(todo: Todo): Todo?
    fun saveAll(todoList: MutableList<Todo>): Boolean //객체 하나만 반환하기에는 애매하기 때문에 결과만 반환
    fun delete(index: Int): Boolean

    fun findOne(index: Int): Todo?
    fun findAll(): MutableList<Todo>
}