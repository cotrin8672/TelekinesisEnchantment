package io.github.cotrin8672.fabric

import io.github.cotrin8672.TemplateMod.init
import net.fabricmc.api.ModInitializer

class TemplateModFabric : ModInitializer {
    override fun onInitialize() {
        init()
    }
}
