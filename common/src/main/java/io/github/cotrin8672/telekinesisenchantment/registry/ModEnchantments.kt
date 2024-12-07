package io.github.cotrin8672.telekinesisenchantment.registry

import io.github.cotrin8672.telekinesisenchantment.TelekinesisMod
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.enchantment.Enchantment

object ModEnchantments {
    val TELEKINESIS: ResourceKey<Enchantment> =
        ResourceKey.create(Registries.ENCHANTMENT, TelekinesisMod.of("telekinesis"))
}
