package io.github.cotrin8672

import net.minecraft.resources.ResourceLocation

object TelekinesisMod {
    const val MOD_ID: String = "telekinesisenchantment"

    @JvmStatic
    fun init() {
//        BlockEvent.BREAK.register { level, pos, state, player, _ ->
//            if (player.isCreative) return@register EventResult.pass()
//            if (!player.mainHandItem.hasEnchantment(level, ModEnchantments.TELEKINESIS))
//                return@register EventResult.pass()
//            val lootContext = LootParams.Builder(level as ServerLevel).apply {
//                withParameter(LootContextParams.THIS_ENTITY, player)
//                withParameter(LootContextParams.ORIGIN, pos.center)
//                withParameter(LootContextParams.BLOCK_STATE, state)
//                level.getBlockEntity(pos)?.let {
//                    withParameter(LootContextParams.BLOCK_ENTITY, it)
//                }
//
//                withParameter(LootContextParams.TOOL, player.mainHandItem)
//            }
//            val drops = state.getDrops(lootContext)
//            drops.forEach {
//                player.inventory.add(it)
//            }
//            EventResult.pass()
//        }
    }

    fun of(id: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, id)
}
