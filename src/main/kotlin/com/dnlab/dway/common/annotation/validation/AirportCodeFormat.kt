package com.dnlab.dway.common.annotation.validation

import jakarta.validation.constraints.Pattern

@Pattern(regexp = "^[A-Z]{3}\$")
annotation class AirportCodeFormat
