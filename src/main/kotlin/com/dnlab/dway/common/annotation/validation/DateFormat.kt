package com.dnlab.dway.common.annotation.validation

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.Pattern

@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
@Pattern(regexp = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
annotation class DateFormat
