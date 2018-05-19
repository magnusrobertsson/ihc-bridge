package com.jayway.ihc.ihcweb.model

interface Ihc {
    fun getBooleanValue(resourceId: Int): Boolean
    fun setBooleanValue(resourceId: Int, value: Boolean): Unit
}