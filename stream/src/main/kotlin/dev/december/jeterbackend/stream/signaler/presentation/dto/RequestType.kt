package dev.december.jeterbackend.stream.signaler.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

enum class RequestType {
  //MANDATORY
  @JsonProperty("candidate") CANDIDATE,
  @JsonProperty("keepalive") KEEPALIVE,
  @JsonProperty("offer") OFFER,
  @JsonProperty("answer") ANSWER,
  ///////////
  @JsonProperty("leave") LEAVE,
  @JsonProperty("new") NEW,
  @JsonProperty("start") START,
  @JsonProperty("toggle_mute") TOGGLE_MUTE,
  @JsonProperty("expired") EXPIRED,
  @JsonProperty("error") ERROR,
}