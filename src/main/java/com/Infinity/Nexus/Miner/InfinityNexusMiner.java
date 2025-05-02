package com.Infinity.Nexus.Miner;

import com.Infinity.Nexus.Miner.block.ModBlocksMiner;
import com.Infinity.Nexus.Miner.block.entity.ModBlockEntities;
import com.Infinity.Nexus.Miner.config.Config;
import com.Infinity.Nexus.Miner.item.ModItemsMiner;
import com.Infinity.Nexus.Miner.recipes.ModRecipes;
import com.Infinity.Nexus.Miner.screen.ModMenuTypes;
import com.Infinity.Nexus.Miner.screen.miner.MinerScreen;
import com.Infinity.Nexus.Miner.tab.ModTab;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(InfinityNexusMiner.MOD_ID)
public class InfinityNexusMiner
{
    public static final String MOD_ID = "infinity_nexus_miner";
    public static final Logger LOGGER = LogUtils.getLogger();


    public InfinityNexusMiner(IEventBus modEventBus, ModContainer modContainer) {

        ModBlocksMiner.register(modEventBus);
        ModItemsMiner.register(modEventBus);

        ModTab.register(modEventBus);
        ModRecipes.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::setup);

        modEventBus.addListener(this::registerScreens);


        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // ModMessages.register();
        });
    }
    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.MINER_MENU.get(), MinerScreen::new);
    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            //MenuScreens.register(ModMenuTypes.MINER_MENU.get(), MinerScreen::new);

            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.RED_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.BLUE_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.GREEN_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.YELLOW_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.PURPLE_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.ORANGE_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.WHITE_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.PINK_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.ANCIENT_LIGHT_CRYSTAL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.CHRISTMAS_LIGHT_CRYSTAL.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.RED_CLEAR_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.BLUE_CLEAR_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.GREEN_CLEAR_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.YELLOW_CLEAR_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.PURPLE_CLEAR_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.ORANGE_CLEAR_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.WHITE_CLEAR_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.PINK_CLEAR_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.ANCIENT_CLEAR_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.CHRISTMAS_CLEAR_GLASS.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModBlocksMiner.STRUCTURAL_BLOCK.get(), RenderType.translucent());


        }
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("   §4_____§5_   __§9__________§3_   ______§b_______  __");
        LOGGER.info("  §4/_  _§5/ | / §9/ ____/  _§3/ | / /  _§b/_  __| \\/ /");
        LOGGER.info("   §4/ /§5/  |/ §9/ /_   / /§3/  |/ // /  §b/ /   \\  / ");
        LOGGER.info(" §4_/ /§5/ /|  §9/ __/ _/ /§3/ /|  // /  §b/ /    / /  ");
        LOGGER.info("§4/___§5/_/ |_§9/_/   /___§3/_/ |_/___/ §b/_/    /_/   ");
        LOGGER.info("§b     Infinity Nexus Miner");

    }
}
