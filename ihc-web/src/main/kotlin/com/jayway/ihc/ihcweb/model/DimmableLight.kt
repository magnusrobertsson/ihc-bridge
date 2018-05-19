package com.jayway.ihc.ihcweb.model

class DimmableLight(ihc: Ihc, id: Int, name: String, val dimUpResourceId: Int, val dimDownResourceId: Int) : Binary(ihc, id, name) {

    var virtualOn: Boolean = false
    var virtualLevel: Int = 0

    override fun setOn(on: Boolean) {
        println("Set dimmable light to $on")
        //dim(dimDownResourceId, virtualLevel)
        virtualOn = on
    }

    override fun isOn(): Boolean {
        // TODO How to determine this?
        //return ihc.getBooleanValue(dimUpResourceId)
        return virtualOn
    }

    fun setDim(level: Int) {
        when {
            level < virtualLevel -> dim(dimDownResourceId, virtualLevel - level)
            level > virtualLevel -> dim(dimUpResourceId, level - virtualLevel)
            else -> println("Already same value")
        }
        virtualLevel = level
        if (level == 0) {
            virtualOn = false
        } else {
            virtualOn = true
        }
    }

    fun getDim(): Int {
        return virtualLevel
    }

    private fun dim(resourceId: Int, amount: Int) {
        println("Dim $resourceId with $amount...")
    }
}