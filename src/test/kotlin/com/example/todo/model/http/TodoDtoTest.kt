package com.example.todo.model.http

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.validation.Validation

//TodoApiController @Valid에 대한 테스트
class TodoDtoTest {

    val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun todoDtoTest(){
        val todoDto = TodoDto().apply {
            this.title = "테스트"
            this.description = ""
            this.schedule = "2020-12-12 13:00:00"
        }
        val result = validator.validate(todoDto)

        /* 에러가 났을 경우 어디가 문제인지 확인하기 위한 코드
        result.forEach{
            println(it.propertyPath.last().name)
            println(it.message)
            println(it.invalidValue)
        }
        */
        Assertions.assertEquals(true, result.isEmpty())
    }
}