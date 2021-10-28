package net.lilydev.vortex.block.machine;

import net.lilydev.vortex.block.entity.GatewayManipulatorCoreBlockEntity;
import net.lilydev.vortex.util.GatewayModuleType;
import net.lilydev.vortex.util.module.PocketDimensionModuleData;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GatewayManipulatorCoreBlock extends BlockWithEntity {
    public GatewayManipulatorCoreBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GatewayManipulatorCoreBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return super.getTicker(world, state, type);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof EnderPearlEntity pearl) {
            if (pearl.getOwner() instanceof PlayerEntity player) {
                GatewayManipulatorCoreBlockEntity gateway = (GatewayManipulatorCoreBlockEntity) this.createBlockEntity(pos, state);
                if (gateway != null) {
                    if (gateway.isModulePresent(GatewayModuleType.POCKET_DIMENSION)) {
                        PocketDimensionModuleData data = (PocketDimensionModuleData) gateway.getModuleData(GatewayModuleType.POCKET_DIMENSION);

                        MinecraftServer server = player.getServer();
                        if (server != null) {
                            ServerWorld pocket = server.getWorlds()
                            player.teleport(data.location().getX(), data.location().getY(), data.location().getZ());
                        }
                    }
                }
            }
        }
    }
}
