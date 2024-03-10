package com.kotyk.timelapse.enumeration

enum Framerate {

    TWENTY_FOUR(24),
    THIRTY(30),
    SIXTY(60)

    final double value

    Framerate(double value) {
        this.value = value
    }
}
