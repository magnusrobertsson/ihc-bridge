package com.jayway.ihc.ihcweb.model

class BinaryLight(ihc: Ihc, id: Int, name: String, val outputResourceId: Int) : Binary(ihc, id, name) {
    override fun setOn(on: Boolean) {
        println("Set binary light to $on")
        ihc.setBooleanValue(outputResourceId, on)
    }

    override fun isOn(): Boolean {
        return ihc.getBooleanValue(outputResourceId)
    }
}