package com.dnlab.dway.auth.integration

import com.dnlab.dway.annotaions.IntegrationTest
import com.dnlab.dway.auth.domain.Gender
import com.dnlab.dway.auth.dto.request.RegistrationRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.sql.Date
import java.util.*

@IntegrationTest
@AutoConfigureMockMvc
internal class AuthenticationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("회원가입 시 올바른 데이터로 요청되었을 경우 OK 를 반환해야 한다")
    fun processRegistration_success() {
        val registrationRequestDto = RegistrationRequestDto(
            username = "testUsername",
            password = "password",
            passwordConfirm = "password",
            korFirstName = "테",
            korLastName = "스트",
            engFirstName = "TE",
            engLastName = "ST",
            gender = Gender.MALE,
            birthDay = Date(Calendar.getInstance().timeInMillis),
            email = "test@test.com",
            phoneCountry = "KR",
            phone = "010-0000-1111",
            country = "KR"
        )

        val content = objectMapper.writeValueAsString(registrationRequestDto)

        mockMvc.perform(
            post("/api/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk)
    }
}