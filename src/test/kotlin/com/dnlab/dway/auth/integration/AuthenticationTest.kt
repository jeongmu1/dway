package com.dnlab.dway.auth.integration

import com.dnlab.dway.auth.domain.Gender
import com.dnlab.dway.auth.dto.request.RegistrationRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.util.*

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AuthenticationTest : FunSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    init {
        this.test("회원가입 시 올바른 데이터로 요청되었을 경우 OK 를 반환해야 한다") {
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

            val result = mockMvc.perform(
                post("/api/auth/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            ).andReturn()

            result.response.status shouldBe 200
        }
    }
}
