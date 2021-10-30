package net.lilydev.vortex.block.machine;

import net.lilydev.vortex.block.entity.VortexBlockEntity;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class VortexBlock extends BlockWithEntity {
    public VortexBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VortexBlockEntity(pos, state);
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
                VortexBlockEntity gateway = (VortexBlockEntity) this.createBlockEntity(pos, state);
                if (gateway != null) {
                    MinecraftServer server = player.getServer();
                    if (server != null) {
                        ServerWorld vortex = server.getWorld(RegistryKey.of(Registry.WORLD_KEY, new Identifier("vortex", "vortex")));
                        player.moveToWorld(vortex);
                    }
                }
            }
        }
    }
}
