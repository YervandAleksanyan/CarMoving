package com.task.carmoving.utils.path

class EPointF(var x: Float, var y: Float) {

    fun plus(factor: Float, ePointF: EPointF): EPointF {
        return EPointF(x + factor * ePointF.x, y + factor * ePointF.y)
    }

    operator fun plus(ePointF: EPointF): EPointF {
        return plus(1.0f, ePointF)
    }

    fun minus(factor: Float, ePointF: EPointF): EPointF {
        return EPointF(x - factor * ePointF.x, y - factor * ePointF.y)
    }

    operator fun minus(ePointF: EPointF): EPointF {
        return minus(1.0f, ePointF)
    }

    fun scaleBy(factor: Float): EPointF {
        return EPointF(factor * x, factor * y)
    }
}
