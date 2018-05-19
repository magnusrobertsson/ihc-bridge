package com.jayway.ihc.ihcweb.internal

import com.jayway.ihc.ihcweb.model.Ihc

class DummyIhc : Ihc {
    val booleanValues = mutableMapOf(0x1ae5b to true)

    override fun getBooleanValue(resourceId: Int): Boolean {
        val value = booleanValues.get(resourceId)
        if (value == null) {
            return false
        } else {
            return value
        }
        //return checkNotNull(value) { "Resource $resourceId does not exist" }
    }

    override fun setBooleanValue(resourceId: Int, value: Boolean) {
        booleanValues.set(resourceId, value)
    }
}