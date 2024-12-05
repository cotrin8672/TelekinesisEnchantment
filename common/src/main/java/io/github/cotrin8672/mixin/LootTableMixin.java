package io.github.cotrin8672.mixin;

import io.github.cotrin8672.mixinimpl.LootTableMixinImpl;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(LootTable.class)
public abstract class LootTableMixin {
    @Shadow
    public abstract void getRandomItemsRaw(LootContext context, Consumer<ItemStack> output);

    @Shadow
    @Final
    private Optional<ResourceLocation> randomSequence;

    @Inject(
            method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;",
            at = @At("TAIL"),
            cancellable = true
    )
    private void telekinesisenchantment$getRandomItems(
            LootContext context,
            CallbackInfoReturnable<ObjectArrayList<ItemStack>> cir
    ) {
        ObjectArrayList<ItemStack> result = LootTableMixinImpl.getRandomItems(context, cir.getReturnValue());
        cir.setReturnValue(result);
        cir.cancel();
    }

    @Inject(
            method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void telekinesisenchantment$getRandomItems(
            LootParams params,
            long seed,
            Consumer<ItemStack> output,
            CallbackInfo ci
    ) {
        LootContext context = new LootContext.Builder(params).withOptionalRandomSeed(seed).create(this.randomSequence);
        Consumer<ItemStack> customConsumer = LootTableMixinImpl.getRandomItems(context, output);
        getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), customConsumer));
        ci.cancel();
    }
}
