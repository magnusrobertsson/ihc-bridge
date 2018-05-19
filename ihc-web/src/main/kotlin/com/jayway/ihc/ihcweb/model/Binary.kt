package com.jayway.ihc.ihcweb.model

abstract class Binary(ihc: Ihc, id: Int, name: String) : Device(ihc, id, name) {
    abstract fun setOn(on: Boolean)
    abstract fun isOn(): Boolean
}