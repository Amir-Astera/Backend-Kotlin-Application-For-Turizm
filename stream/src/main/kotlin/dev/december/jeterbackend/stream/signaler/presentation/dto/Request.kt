package dev.december.jeterbackend.stream.signaler.presentation.dto

import dev.december.jeterbackend.stream.signaler.presentation.dto.RequestType

interface Request {
  val type: RequestType
  val data: Any?
}