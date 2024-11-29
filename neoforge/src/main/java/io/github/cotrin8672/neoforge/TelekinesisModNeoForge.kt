package io.github.cotrin8672.neoforge

import io.github.cotrin8672.TelekinesisMod
import io.github.cotrin8672.TelekinesisMod.init
import io.github.cotrin8672.neoforge.registry.ModRegistries
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.level.BlockEvent
import net.neoforged.neoforge.registries.NewRegistryEvent

@EventBusSubscriber(modid = TelekinesisMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
@Mod(TelekinesisMod.MOD_ID)
class TelekinesisModNeoForge {
    init {
        init()
    }

    @SubscribeEvent
    fun registerRegistries(event: NewRegistryEvent) {
        event.register(ModRegistries.ENCHANTMENT_LOOT_TABLE_EFFECT_TYPE)
    }

    @SubscribeEvent
    fun lootTableEvent(event: BlockEvent.BreakEvent) {
        event.state.getDrops()

    }
}
