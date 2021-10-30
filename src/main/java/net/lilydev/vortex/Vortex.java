package net.lilydev.vortex;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.lilydev.vortex.block.entity.VortexBlockEntity;
import net.lilydev.vortex.block.machine.VortexBlock;
import net.lilydev.vortex.fluid.OilFluid;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class Vortex implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Vortex");

    public static class Fluids {
        private static final HashMap<String, Fluid> fluids = new HashMap<>();

        private static Fluid add(String name, Fluid fluid) {
            fluids.put(name, fluid);
            return fluid;
        }

        public static final Fluid OIL_STILL = add("oil", new OilFluid.Still());
        public static final Fluid OIL_FLOWING = add("oil_flowing", new OilFluid.Flowing());

        public static void register() {
            LOGGER.info("Registering fluids!");
            fluids.forEach((name, fluid) -> {
                LOGGER.debug("Registering fluid '" + name + "'...");
                Registry.register(Registry.FLUID, new Identifier("vortex", name), fluid);
            });
        }
    }

    public static class Items {
        private static final HashMap<String, Item> items = new HashMap<>();

        private static Item add(String name, Item item) {
            items.put(name, item);
            return item;
        }

        public static final BucketItem BUCKET_OIL = (BucketItem) add("bucket_oil", new BucketItem(Fluids.OIL_STILL, new Item.Settings().recipeRemainder(net.minecraft.item.Items.BUCKET).maxCount(1)));

        public static void register() {
            LOGGER.info("Registering items!");
            items.forEach((name, item) -> {
                LOGGER.debug("Registering item '" + name + "'...");
                Registry.register(Registry.ITEM, new Identifier("vortex", name), item);
            });
        }
    }

    public static class Blocks {
        private static final HashMap<String, Block> blocks = new HashMap<>();

        private static Block add(String name, Block block) {
            blocks.put(name, block);
            return block;
        }

        public static final FluidBlock OIL = (FluidBlock) add("oil", new FluidBlock((FlowableFluid) Fluids.OIL_STILL, FabricBlockSettings.copy(net.minecraft.block.Blocks.WATER).resistance(0)){});
        public static final VortexBlock VORTEX = (VortexBlock) add("vortex", new VortexBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.END_GATEWAY)));
        
        public static void register() {
            LOGGER.info("Registering blocks!");
            blocks.forEach((name, block) -> {
                LOGGER.debug("Registering block '" + name + "'...");
                Registry.register(Registry.BLOCK, new Identifier("vortex", name), block);
            });
        }
    }

    public static class BlockEntities {
        private static final HashMap<String, BlockEntityType<? extends BlockEntity>> blockEntities = new HashMap<>();

        private static <T extends BlockEntity> BlockEntityType<T> add(String name, BlockEntityType<T> type) {
            blockEntities.put(name, type);
            return type;
        }

        public static BlockEntityType<VortexBlockEntity> VORTEX = add("vortex_blockentity", FabricBlockEntityTypeBuilder.create(VortexBlockEntity::new, Blocks.VORTEX).build(null));

        public static void register() {
            LOGGER.info("Registering block entities!");
            blockEntities.forEach((name, type) -> {
                LOGGER.debug("Registering block entity '" + name + "'...");
                Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("vortex", name), type);
            });
        }
    }

    @Override
    public void onInitialize() {
        Items.register();
        Blocks.register();
        BlockEntities.register();
        Fluids.register();
    }
}
