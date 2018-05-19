package com.jayway.ihc.ihcweb.model

class Outlet(ihc: Ihc, id: Int, name: String, val outputResourceId: Int) : Binary(ihc, id, name) {
    override fun setOn(on: Boolean) {
        println("Set outlet to $on")
        ihc.setBooleanValue(outputResourceId, on)
    }

    override fun isOn(): Boolean {
        return ihc.getBooleanValue(outputResourceId)
    }
}