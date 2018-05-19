package com.jayway.ihc.ihcweb.model

import com.jayway.ihc.ihcweb.dto.DeviceDTO

object DomainConversions {
    fun toDeviceDTO(device: Device): DeviceDTO {
        val dto = when (device) {
            is DimmableLight -> DeviceDTO(device.id, device.name, "dimmable-light", on = device.isOn(), dim = device.getDim())
            is BinaryLight -> DeviceDTO(device.id, device.name, "binary-light", on = device.isOn())
            is Outlet -> DeviceDTO(device.id, device.name, "outlet", on = device.isOn())
            is SecurityAlarm -> DeviceDTO(device.id, device.name, "security-alarm", currentState = device.getCurrentState(), targetState = device.getTargetState())
            is SmokeAlarm -> DeviceDTO(device.id, device.name, "smoke-alarm", currentState = device.getCurrentState())
            else -> null // Impossible
        }
        return checkNotNull(dto)
    }

}