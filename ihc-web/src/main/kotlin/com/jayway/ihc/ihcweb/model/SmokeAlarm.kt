package com.jayway.ihc.ihcweb.model

class SmokeAlarm(ihc: Ihc, id: Int, name: String, outputResourceId: Int) : Device(ihc, id, name) {
    private var currentState: Int = 0

    fun getCurrentState(): Int {
        return currentState
    }
}