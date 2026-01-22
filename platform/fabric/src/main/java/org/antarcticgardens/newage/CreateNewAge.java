package org.antarcticgardens.newage;

import com.simibubi.create.content.contraptions.ContraptionMovementSetting;
import com.simibubi.create.content.fluids.tank.BoilerHeaters;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.content.energiser.EnergisingRecipe;
import org.antarcticgardens.newage.content.generation.magnets.MagnetPlacementHelper;
import org.antarcticgardens.newage.tools.RecipeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.antarcticgardens.newage.content.heat.heater.HeaterBlock.STRENGTH;

public class CreateNewAge implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("create_new_age");

	public static final String MOD_ID = "create_new_age";

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

	public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
			new ResourceLocation(MOD_ID, "tab"));
	
	private static int magnetPlacementHelperId;

	@Override
	public void onInitialize() {
		LOGGER.info("Hello 1.20.1 Create!");

		registerCreativeTab();

		NewAgeBlocks.load();
		NewAgeBlockEntityTypes.load();
		NewAgeItems.load();

		magnetPlacementHelperId = PlacementHelpers.register(new MagnetPlacementHelper());

		REGISTRATE.register();
		NewAgeConfig.getCommon();

		BoilerHeaters.registerHeater(NewAgeBlocks.HEATER.get(), (level, pos, state) -> state.getValue(STRENGTH).ordinal() - 1);

		EnergisingRecipe.type = RecipeTool.createIRecipeTypeInfo("energising", new ProcessingRecipeSerializer<>(EnergisingRecipe::new));

		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation("create_new_age","ore_thorium")));
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation("create_new_age","magnetite")));

		ContraptionMovementSetting.register(NewAgeBlocks.ELECTRICAL_CONNECTOR.get(), () -> ContraptionMovementSetting.UNMOVABLE);

		var containerContainer = FabricLoader.getInstance().getModContainer("create_new_age");

		if (containerContainer.isEmpty()) {
			return;
		}

		ModContainer modContainer = containerContainer.get();
		ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation(MOD_ID, "create_new_age_monkey_edition"), modContainer, Component.translatable("create_new_age.monkey_edition"), ResourcePackActivationType.NORMAL);
	}
	
	public static int getMagnetPlacementHelperId() {
		return magnetPlacementHelperId;
	}

	private void registerCreativeTab() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
				new ResourceLocation(MOD_ID, "tab"),
				FabricItemGroup.builder()
						.icon(NewAgeBlocks.GENERATOR_COIL::asStack)
						.title(Component.translatable("tab." + MOD_ID + ".tab"))
						.build());
	}
}