package dev.december.jeterbackend.stream.signaler.presentation.dto

import dev.december.jeterbackend.stream.signaler.presentation.dto.RequestType

interface Error {
  val requestType: RequestType
  val reason: String
}

