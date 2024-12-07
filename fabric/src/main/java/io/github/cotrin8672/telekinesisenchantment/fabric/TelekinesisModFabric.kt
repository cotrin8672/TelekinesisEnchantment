package io.github.cotrin8672.telekinesisenchantment.fabric

import io.github.cotrin8672.telekinesisenchantment.TelekinesisMod.init
import net.fabricmc.api.ModInitializer

class TelekinesisModFabric : ModInitializer {
    override fun onInitialize() {
        init()
    }
}
