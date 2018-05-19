package com.jayway.ihc.ihcweb

import com.jayway.ihc.ihcweb.dto.*
import com.jayway.ihc.ihcweb.model.*
import com.jayway.ihc.ihcweb.model.DomainConversions.toDeviceDTO
import org.openhab.binding.ihc.ws.IhcClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class IhcController(val ihcClient: IhcClient, val deviceRepository: DeviceRepository) {

    @GetMapping("/devices")
    fun getDevices(): DeviceListDTO {
        return DeviceListDTO(deviceRepository.findAll().map(::toDeviceDTO))
    }

    @GetMapping("/devices/{deviceId}")
    fun getDevice(@PathVariable("deviceId") deviceId: Int): DeviceDTO {
        return toDeviceDTO(deviceRepository.findById(deviceId))
    }

    @PutMapping("/devices/{deviceId}/on")
    fun setDeviceOn(@PathVariable("deviceId") deviceId: Int, @RequestBody param: SetBinaryLightDTO): ResponseEntity<Unit> {
        val device = deviceRepository.findById(deviceId)
        if (device is Binary) {
            device.setOn(param.on)
        } else {
            IllegalArgumentException("Device $deviceId is not a binary light")
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PutMapping("/devices/{deviceId}/dim")
    fun setDeviceDim(@PathVariable("deviceId") deviceId: Int, @RequestBody param: SetDimmableLightDTO): ResponseEntity<Unit> {
        val device = deviceRepository.findById(deviceId)
        if (device is DimmableLight) {
            device.setDim(param.dim)
        } else {
            IllegalArgumentException("Device $deviceId is not a dimmable light")
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PutMapping("/devices/{deviceId}/targetState")
    fun setDeviceTargetState(@PathVariable("deviceId") deviceId: Int, @RequestBody param: SetTargetStateDTO): ResponseEntity<Unit> {
        val device = deviceRepository.findById(deviceId)
        if (device is SecurityAlarm) {
            device.setTargetState(param.targetState)
        } else {
            IllegalArgumentException("Device $deviceId is not a security alarm")
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}