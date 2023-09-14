package com.dnlab.dway.common.annotation.validation

import jakarta.validation.constraints.Pattern

@Pattern(regexp = "^\\S*$", message = "해당 필드에는 공백이 포함될 수 없습니다.")
annotation class NoWhitespace()
