package com.jayway.ihc.ihcweb.internal

import com.jayway.ihc.ihcweb.model.Ihc
import org.openhab.binding.ihc.ws.IhcClient
import org.openhab.binding.ihc.ws.datatypes.WSBooleanValue

class SoapClientIhc(val client: IhcClient) : Ihc {
    override fun getBooleanValue(resourceId: Int): Boolean {
        val value = client.getResourceValueInformation(resourceId)
        if (value is WSBooleanValue)
            return value.isValue
        else
            throw IllegalArgumentException("Resource $resourceId is not a boolean")
    }

    override fun setBooleanValue(resourceId: Int, value: Boolean): Unit {
        val arg = WSBooleanValue()
        arg.resourceID = resourceId
        arg.isValue = value
        client.resourceUpdate(arg)
    }
}