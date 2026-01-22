package org.antarcticgardens.newage;

import com.simibubi.create.api.boiler.BoilerHeater;
import com.simibubi.create.api.contraption.ContraptionMovementSetting;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.createmod.catnip.placement.PlacementHelpers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.antarcticgardens.newage.config.NewAgeConfig;
import org.antarcticgardens.newage.content.energiser.EnergisingRecipe;
import org.antarcticgardens.newage.content.generation.magnets.MagnetPlacementHelper;
import org.antarcticgardens.newage.tools.RecipeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import static org.antarcticgardens.newage.content.heat.heater.HeaterBlock.STRENGTH;

@Mod("create_new_age")
public class CreateNewAge {
    public static final Logger LOGGER = LoggerFactory.getLogger("create_new_age");

	public static final String MOD_ID = "create_new_age";

	public static final CreateRegistrate BASE_REGISTRATE = CreateRegistrate.create(MOD_ID);


	private static DeferredRegister<CreativeModeTab> TAB_REGISTRAR = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
	public static final RegistryObject<CreativeModeTab> tab = TAB_REGISTRAR.register("create_new_age_tab",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("item_group." + MOD_ID + ".tab"))
					.icon(NewAgeBlocks.GENERATOR_COIL::asStack)
					.build()
			);

	public static final CreateRegistrate REGISTRATE = BASE_REGISTRATE.setCreativeTab(tab);


	public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
			new ResourceLocation(MOD_ID, "create_new_age_tab"));
	
	public static IRecipeTypeInfo ENERGISING_RECIPE_TYPE;
	
	private static int magnetPlacementHelperId;

	public CreateNewAge() {
		var modBus = FMLJavaModLoadingContext.get().getModEventBus();
		LOGGER.info("Hello 1.20.1 Create!");

		BASE_REGISTRATE.registerEventListeners(modBus);
		TAB_REGISTRAR.register(modBus);

		NewAgeBlocks.load();
		NewAgeBlockEntityTypes.load();
		NewAgeItems.load();
		NewAgePartialModels.load();

		magnetPlacementHelperId = PlacementHelpers.register(new MagnetPlacementHelper());
		
		NewAgeConfig.getCommon();

		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientIniter::onInitializeClient);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::generalSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerDatapack);

		RecipeTool.register_type.register(modBus);
		RecipeTool.register.register(modBus);

		try {
			ENERGISING_RECIPE_TYPE = RecipeTool.createIRecipeTypeInfo("energising", new ProcessingRecipeSerializer<>(EnergisingRecipe::new));
		} catch (Exception e) {
			LOGGER.error("Exception", e);
		}
	}

	public static int getMagnetPlacementHelperId() {
		return magnetPlacementHelperId;
	}
	
	public void registerDatapack(final AddPackFindersEvent event) {
		if (event.getPackType() == PackType.SERVER_DATA) {
			Path path = ModList.get().getModFileById("create_new_age").getFile().findResource("resourcepacks/create_new_age_monkey_edition");
			Pack builtinDataPack = Pack.readMetaAndCreate(
					"create_new_age:create_new_age_monkey_edition",
					Component.translatable("create_new_age.monkey_edition"),
					false,
					(a) -> new PathPackResources(a, path, false),
					PackType.SERVER_DATA,
					Pack.Position.TOP,
					PackSource.create((arg) -> Component.translatable("pack.nameAndSource", arg, Component.translatable("pack.source.builtin")).withStyle(ChatFormatting.GRAY), false)
			);

			event.addRepositorySource((packConsumer) -> packConsumer.accept(builtinDataPack));
		}
	}

	private void generalSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			BoilerHeater.REGISTRY.register(NewAgeBlocks.HEATER.get(), (level, pos, state) -> state.getValue(STRENGTH).ordinal() - 1);
			ContraptionMovementSetting.REGISTRY.register(NewAgeBlocks.ELECTRICAL_CONNECTOR.get(), () -> ContraptionMovementSetting.UNMOVABLE);
			for (Runnable doLater : NewAgeBlocks.doLater) {
				doLater.run();
			}
		});
	}
}