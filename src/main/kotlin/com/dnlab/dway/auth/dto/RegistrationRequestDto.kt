package com.dnlab.dway.auth.dto

import com.dnlab.dway.auth.domain.Gender
import jakarta.validation.constraints.Pattern
import java.sql.Date


data class RegistrationRequestDto(
        @Pattern(regexp = "^[A-Za-z0-9]{6,20}\$", message = "양식에 맞지 않는 아이디")
        val username: String,

        @Pattern(regexp = "^(?!([A-Za-z]+|[~!@#$%^&*()_+=]+|[0-9]+)\$)[A-Za-z\\d~!@#\$%^&*()_+=]{10,}\$", message = "양식에 맞지 않는 비밀번호")
        val password: String,

        val passwordConfirm: String,

        @Pattern(regexp = "^[가-힣]{1,2}\$", message = "양식에 맞지 않는 한글 성")
        val korFirstName: String,

        @Pattern(regexp = "^[가-힣]{1,10}\$", message = "양식에 맞지 않는 한글 이름")
        val korLastName: String,

        @Pattern(regexp = "^[A-Z]{2,20}\$", message = "양식에 맞지 않는 영문 성")
        val engFirstName: String,

        @Pattern(regexp = "^[A-Z]{2,20}\$", message = "양식에 맞지 않는 영문 이름")
        val engLastName: String,

        val gender: Gender,

        val birthDay: Date,

        @Pattern(regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$\n", message = "양식에 맞지 않는 이메일")
        val email: String,

        @Pattern(regexp = "^[A-Z]{2}\$", message = "양식에 맞지 않는 나라 코드")
        val phoneCountry: String,

        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{3,4}\$\n", message = "양식에 맞지 않는 전화번호")
        val phone: String,

        @Pattern(regexp = "^[A-Z]{2}\$", message = "양식에 맞지 않는 나라")
        val country: String
)
