package io.github.cotrin8672.effect

import com.mojang.serialization.MapCodec
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.enchantment.EnchantedItemInUse
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect
import net.minecraft.world.phys.Vec3

@JvmRecord
data class TelekinesisEnchantmentEffect(
    private val _value: Int,
) : EnchantmentEntityEffect {
    override fun codec(): MapCodec<out EnchantmentEntityEffect> {
        TODO("Not yet implemented")
    }

    override fun apply(
        serverLevel: ServerLevel,
        i: Int,
        enchantedItemInUse: EnchantedItemInUse,
        entity: Entity,
        vec3: Vec3,
    ) {
        TODO("Not yet implemented")
    }

}
