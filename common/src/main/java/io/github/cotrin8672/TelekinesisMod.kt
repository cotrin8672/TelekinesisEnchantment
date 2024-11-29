package io.github.cotrin8672

import net.minecraft.resources.ResourceLocation

object TelekinesisMod {
    const val MOD_ID: String = "telekinesisenchantment"

    @JvmStatic
    fun init() {
    }

    fun of(id: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, id)
}
