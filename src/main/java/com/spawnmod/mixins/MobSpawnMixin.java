package com.spawnmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.Impl;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionType;

@Mixin(HostileEntity.class)
public class MobSpawnMixin {
	private static final Identifier WITHER_SKELETON_ID = new Identifier("minecraft:wither_skeleton");
	private static final Identifier SKELETON_ID = new Identifier("minecraft:skeleton");

	private static final RegistryKey<DimensionType> THE_NETHER = of("the_nether");
	private static final Impl<DimensionType> DIMENSION_REG = BuiltinRegistries.createWrapperLookup()
			.getOptionalWrapper(RegistryKeys.DIMENSION_TYPE).get();

	private static final DimensionType NETHER_DIMENSION = DIMENSION_REG.getOptional(THE_NETHER).get().comp_349();
	private static final EntityType<?> WITHER_SKELETON_ENTITY = Registries.ENTITY_TYPE.get(WITHER_SKELETON_ID);
	private static final EntityType<?> SKELETON_ENTITY = Registries.ENTITY_TYPE.get(SKELETON_ID);

	@Inject(method = "canSpawnIgnoreLightLevel", at = @At("RETURN"), cancellable = true)
	private static void injectSpawn1(EntityType<? extends HostileEntity> entityType, WorldAccess worldAccess,
			SpawnReason spawnReason, BlockPos blockPos, Random random, CallbackInfoReturnable<Boolean> cir) {
		DimensionType dt = worldAccess.getDimension();

		if ((entityType.equals(SKELETON_ENTITY) || entityType.equals(SKELETON_ENTITY)) && dt.equals(NETHER_DIMENSION)
				&& !worldAccess.isClient())
			cir.setReturnValue(true);
	}

	@Inject(method = "canSpawnInDark", at = @At("RETURN"), cancellable = true)
	private static void injectSpawn2(EntityType<? extends HostileEntity> entityType,
			ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random,
			CallbackInfoReturnable<Boolean> cir) {

		DimensionType dt = serverWorldAccess.getDimension();

		if ((entityType.equals(SKELETON_ENTITY) || entityType.equals(WITHER_SKELETON_ENTITY))
				&& dt.equals(NETHER_DIMENSION) && !serverWorldAccess.isClient())
			cir.setReturnValue(true);

	}

	@Inject(method = "isSpawnDark", at = @At("RETURN"), cancellable = true)
	private static void injectSpawn3(ServerWorldAccess serverWorldAccess, BlockPos blockPos, Random random,
			CallbackInfoReturnable<Boolean> cir) {
		DimensionType dt = serverWorldAccess.getDimension();
		if (dt.equals(NETHER_DIMENSION))
			cir.setReturnValue(true);
	}

	private static RegistryKey<DimensionType> of(String string) {
		return RegistryKey.of(RegistryKeys.DIMENSION_TYPE, new Identifier(string));
	}
}
