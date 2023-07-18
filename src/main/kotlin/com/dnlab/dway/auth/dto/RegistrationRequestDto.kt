package com.dnlab.dway.auth.dto

import com.dnlab.dway.auth.domain.Gender
import jakarta.validation.constraints.Pattern
import java.sql.Date


data class RegistrationRequestDto(
        @Pattern(regexp = "^[A-Za-z0-9]{6,20}\$")
        val username: String,

        @Pattern(regexp = "^(?!([A-Za-z]+|[~!@#$%^&*()_+=]+|[0-9]+)\$)[A-Za-z\\d~!@#\$%^&*()_+=]{10,}\$")
        val password: String,

        @Pattern(regexp = "^(?!([A-Za-z]+|[~!@#$%^&*()_+=]+|[0-9]+)\$)[A-Za-z\\d~!@#\$%^&*()_+=]{10,}\$")
        val passwordConfirm: String,

        @Pattern(regexp = "^[가-힣]{1,2}\$")
        val korFirstName: String,

        @Pattern(regexp = "^[가-힣]{1,10}\$")
        val korLastName: String,

        @Pattern(regexp = "^[A-Z]{2,20}\$")
        val engFirstName: String,

        @Pattern(regexp = "^[A-Z]{2,20}\$")
        val engLastName: String,

        val gender: Gender,

        val birthDay: Date,

        @Pattern(regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$\n")
        val email: String,

        @Pattern(regexp = "^[A-Z]{2}\$")
        val phoneCountry: String,

        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{3,4}\$\n")
        val phone: String,

        @Pattern(regexp = "^[A-Z]{2}\$")
        val country: String
)
