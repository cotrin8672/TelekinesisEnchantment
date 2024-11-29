package io.github.cotrin8672.neoforge.registry

import com.mojang.serialization.MapCodec
import io.github.cotrin8672.TelekinesisMod
import io.github.cotrin8672.neoforge.enchantment.EnchantmentLootTableEffect
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.neoforged.neoforge.registries.RegistryBuilder

object ModRegistries {
    val ENCHANTMENT_LOOT_TABLE_EFFECT_TYPE_KEY: ResourceKey<Registry<MapCodec<EnchantmentLootTableEffect>>> =
        ResourceKey.createRegistryKey(TelekinesisMod.of("enchantment_loot_table_effect"))

    val ENCHANTMENT_LOOT_TABLE_EFFECT_TYPE: Registry<MapCodec<EnchantmentLootTableEffect>> =
        RegistryBuilder(ENCHANTMENT_LOOT_TABLE_EFFECT_TYPE_KEY)
            .sync(true)
            .create()
}
