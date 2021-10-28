package net.lilydev.vortex.block.entity;

import net.lilydev.vortex.Vortex;
import net.lilydev.vortex.util.GatewayModuleData;
import net.lilydev.vortex.util.GatewayModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

public class GatewayManipulatorCoreBlockEntity extends BlockEntity {
    private final HashMap<GatewayModuleType, GatewayModuleData> modules;

    public GatewayManipulatorCoreBlockEntity(BlockPos pos, BlockState state) {
        super(Vortex.BlockEntities.GATEWAY_MANIPULATOR, pos, state);
        this.modules = new HashMap<>();
    }

    public GatewayModuleData getModuleData(GatewayModuleType type) {
        return this.modules.get(type);
    }

    public void addModule(GatewayModuleType type, GatewayModuleData data) {
        this.modules.remove(type);

        this.modules.put(type, data);
    }

    public void removeModule(GatewayModuleType type) {
        this.modules.remove(type);
    }

    public boolean isModulePresent(GatewayModuleType type) {
        return this.modules.containsKey(type);
    }
}