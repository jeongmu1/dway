package com.dnlab.dway.auth.dto.request

import com.dnlab.dway.auth.domain.Gender
import com.dnlab.dway.common.annotation.validation.DateFormat
import com.dnlab.dway.common.annotation.validation.NoWhitespace
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import java.time.LocalDate


data class RegistrationRequestDto(
        @Pattern(regexp = "^[A-Za-z0-9]{6,20}\$")
        @NoWhitespace
        val username: String,

        @Pattern(regexp = "^(?!([A-Za-z]+|[~!@#$%^&*()_+=]+|[0-9]+)\$)[A-Za-z\\d~!@#\$%^&*()_+=]{10,}\$")
        val password: String,

        val passwordConfirm: String,

        @Pattern(regexp = "^[가-힣]{1,2}\$")
        @NoWhitespace
        val korFirstName: String,

        @Pattern(regexp = "^[가-힣]{1,10}\$")
        @NoWhitespace
        val korLastName: String,

        @Pattern(regexp = "^[A-Z]{2,20}\$")
        @NoWhitespace
        val engFirstName: String,

        @Pattern(regexp = "^[A-Z]{2,20}\$")
        @NoWhitespace
        val engLastName: String,

        @NotNull
        val gender: Gender,

        @NotNull
        @DateFormat
        val birthDay: LocalDate,

        @Pattern(regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")
        val email: String,

        @Pattern(regexp = "^[A-Z]{2}\$")
        val phoneCountry: String,

        @Pattern(regexp = "^(\\d{2,3})-?\\d{3,4}-\\d{4}\$")
        val phone: String,

        @Pattern(regexp = "^[A-Z]{2}\$")
        val country: String
)
