package net.lilydev.vortex.util.module;

import net.lilydev.vortex.util.GatewayModuleData;
import net.minecraft.util.math.BlockPos;

public record PocketDimensionModuleData(BlockPos location) implements GatewayModuleData {}