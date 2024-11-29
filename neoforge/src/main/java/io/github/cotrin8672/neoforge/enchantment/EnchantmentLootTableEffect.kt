package io.github.cotrin8672.neoforge.enchantment

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import io.github.cotrin8672.neoforge.registry.ModRegistries
import net.minecraft.world.level.storage.loot.LootTable
import java.util.function.Function

interface EnchantmentLootTableEffect {
    companion object {
        val CODEC: Codec<EnchantmentLootTableEffect> = ModRegistries.ENCHANTMENT_LOOT_TABLE_EFFECT_TYPE
            .byNameCodec()
            .dispatch(EnchantmentLootTableEffect::codec, Function.identity())
    }

    fun apply(lootTable: LootTable)

    fun codec(): MapCodec<out EnchantmentLootTableEffect>
}

