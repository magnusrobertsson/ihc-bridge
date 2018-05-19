package com.jayway.ihc.ihcweb.model

class DeviceRepository(val ihc: Ihc) {
    private val dimmableLights = listOf(
            DimmableLight(ihc, 1, "Hall", 0x1a95b, 0x1aa5b),
            DimmableLight(ihc, 2, "Kök", 0x19f5b, 0x1a05b),
            DimmableLight(ihc, 3, "Matplats", 0x1ae5b, 0x1af5b),
            DimmableLight(ihc, 4, "Köksbänk", 0x1b35b, 0x1b45b)
    )

    private val binaryLights = listOf(
            BinaryLight(ihc, 11, "Under kökskåp", 0x1885b)
    )

    private val securityAlarms = listOf(
            SecurityAlarm(ihc, 21,"Säkerhetslarm", 0x3e40a)
    )

    private val smokeAlarms = listOf(
            SmokeAlarm(ihc, 22, "Brandlarm", 0x10e5b)
    )

    private val all = dimmableLights + binaryLights + securityAlarms + smokeAlarms

    fun findById(deviceId: Int): Device {
        val found = all.find { device -> device.id == deviceId }
        return checkNotNull(found) { "Device $deviceId not found" }
    }

    fun findAll(): List<Device> {
        return all
    }
}