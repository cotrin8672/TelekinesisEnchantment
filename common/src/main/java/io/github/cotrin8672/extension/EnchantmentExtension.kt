package io.github.cotrin8672.extension

import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.Level

fun ItemStack.hasEnchantment(
    level: Level,
    enchantment: ResourceKey<Enchantment>,
): Boolean {
    return this.enchantments.getLevel(
        level.registryAccess().asGetterLookup().get(
            enchantment.registryKey(),
            enchantment
        ).get()
    ) != 0
}
