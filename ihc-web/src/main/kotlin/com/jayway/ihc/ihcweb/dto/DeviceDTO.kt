package com.jayway.ihc.ihcweb.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.*

/**
 * Types:
 * dimmable-light, binary-light, motion-sensor, leak-sensor, contact-sensor, smoke-sensor
 */
@JsonInclude(Include.NON_NULL)
data class DeviceDTO(
        val id: Int,
        val name: String,
        val type: String,
        val on: Boolean? = null,
        val dim: Int? = null,
        val currentState: Int? = null,
        val targetState: Int? = null)
