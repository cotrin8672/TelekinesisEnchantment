package io.github.cotrin8672.extension

import net.minecraft.world.phys.Vec3

operator fun Vec3.times(value: Number): Vec3 {
    return this.multiply(value.toDouble(), value.toDouble(), value.toDouble())
}

operator fun Vec3.plus(value: Vec3): Vec3 {
    return this.add(value)
}
