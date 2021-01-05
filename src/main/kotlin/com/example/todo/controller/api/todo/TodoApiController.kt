package com.example.todo.controller.api.todo

import com.example.todo.model.http.TodoDto
import com.example.todo.service.TodoService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/todo")
class TodoApiController(
    val todoService: TodoService
) {

    @GetMapping("")
    fun read(@RequestParam(required = false) index: Int?): ResponseEntity<Any> {
        return index?.let{
            todoService.read(it)
        }?.let {
            return ResponseEntity.ok(it)
        }?: kotlin.run {
            return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, "/api/todo/all")
                .build()
        }
    }

    @GetMapping("/all")
    fun readAll(): MutableList<TodoDto> {
        return todoService.readAll()
    }

    @PostMapping("")
    fun create(@Valid @RequestBody todoDto: TodoDto): ResponseEntity<Any> {
        return todoDto.let {
            todoService.create(it)
        }?.let {
            return ResponseEntity.ok(it)
        }?: kotlin.run {
            return ResponseEntity.badRequest().body("Create Failed")
        }
    }

    @PutMapping("")  // todo create status : 201 / update status : 200
    fun update(@Valid @RequestBody todoDto: TodoDto): ResponseEntity<Any>{
        return todoDto.let {
            todoService.update(it)
        }?.let {
            return ResponseEntity.ok(it)
        }?: kotlin.run {
            return ResponseEntity.badRequest().body("Create Failed")
        }
    }

    @DeleteMapping("/{index}")
    fun delete(@PathVariable(name = "index") _index: Int): ResponseEntity<Any> {

        if(!todoService.delete(_index)){
            return ResponseEntity.status(500).build()
        }
        return ResponseEntity.ok().build()
    }
}