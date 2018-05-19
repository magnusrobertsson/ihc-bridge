package com.jayway.ihc.ihcweb.model

class SecurityAlarm(ihc: Ihc, id: Int, name: String, outputResourceId: Int) : Device(ihc, id, name) {
    //Characteristic.SecuritySystemCurrentState.STAY_ARM = 0;
    //Characteristic.SecuritySystemCurrentState.AWAY_ARM = 1;
    //Characteristic.SecuritySystemCurrentState.NIGHT_ARM = 2;
    //Characteristic.SecuritySystemCurrentState.DISARMED = 3;
    //Characteristic.SecuritySystemCurrentState.ALARM_TRIGGERED = 4;

    private var targetState: Int = 3
    private var currentState: Int = 3

    fun getCurrentState(): Int {
        return currentState
    }

    fun setTargetState(state: Int) {
        println("Target state for security alarm is now $state")
        targetState = state
        currentState = state
    }

    fun getTargetState(): Int {
        return targetState
    }
}