package io.github.cotrin8672.mixinimpl

import io.github.cotrin8672.extension.hasEnchantment
import io.github.cotrin8672.extension.plus
import io.github.cotrin8672.extension.times
import io.github.cotrin8672.registry.ModEnchantments
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import net.minecraft.client.Minecraft
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import java.util.function.Consumer

object LootTableMixinImpl {
    @JvmStatic
    fun getRandomItems(
        context: LootContext,
        defaultItems: ObjectArrayList<ItemStack>,
    ): ObjectArrayList<ItemStack> {
        val player = context.getParamOrNull(LootContextParams.THIS_ENTITY)
        if (player !is Player) return defaultItems
        if (player.isCreative) return defaultItems
        if (!player.mainHandItem.hasEnchantment(context.level, ModEnchantments.TELEKINESIS)) return defaultItems

        val leftItems = defaultItems.map {
            player.inventory.add(it)
            it
        }.filter {
            !it.isEmpty
        }

        if (defaultItems.size != leftItems.size)
            spawnParticleLine(player, context)

        return ObjectArrayList.of(*leftItems.toTypedArray())
    }

    @JvmStatic
    fun getRandomItems(
        context: LootContext,
        output: Consumer<ItemStack>,
    ): Consumer<ItemStack> {
        val player = context.getParamOrNull(LootContextParams.ATTACKING_ENTITY)
        if (player !is Player) return output
        if (player.isCreative) return output
        if (!player.mainHandItem.hasEnchantment(context.level, ModEnchantments.TELEKINESIS)) return output

        return Consumer {
            val oldItemStack = it.copy()
            player.inventory.add(it)
            if (!it.isEmpty) {
                output.accept(it)
            }
            if (oldItemStack != it) {
                spawnParticleLine(player, context)
            }
        }
    }

    private fun spawnParticleLine(player: Player, context: LootContext) {
        val playerVec = player.eyePosition * 0.75 + player.position() * 0.25
        val originVec = context.getParamOrNull(LootContextParams.ORIGIN) ?: playerVec
        val distance = player
            .position()
            .distanceTo(originVec)
        val particleCount = (distance * 10).toInt()

        for (i in 0..particleCount) {
            val t = i.toFloat() / particleCount
            val particlePosVec = originVec * t + playerVec * (1 - t)
            Minecraft.getInstance().level?.addParticle(
                ParticleTypes.ENCHANT,
                true,
                particlePosVec.x,
                particlePosVec.y,
                particlePosVec.z,
                0.0, 0.0, 0.0
            )
        }
    }
}
