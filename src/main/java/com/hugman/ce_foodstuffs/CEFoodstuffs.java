package com.hugman.ce_foodstuffs;

import com.hugman.ce_foodstuffs.config.CEFConfig;
import com.hugman.ce_foodstuffs.init.CEFBlocks;
import com.hugman.ce_foodstuffs.init.CEFEffects;
import com.hugman.ce_foodstuffs.init.CEFItems;
import com.hugman.ce_foodstuffs.init.data.CEFLootTables;
import com.hugman.dawn.api.util.ModData;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CEFoodstuffs implements ModInitializer {
	public static final ModData MOD_DATA = new ModData("ce_foodstuffs");
	public static final Logger LOGGER = LogManager.getLogger();
	public static final CEFConfig CONFIG = AutoConfig.register(CEFConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new)).getConfig();

	@Override
	public void onInitialize() {
		CEFBlocks.init();
		CEFEffects.init();
		CEFItems.init();
		CEFLootTables.addToVanillaTables();
		MOD_DATA.registerCreators();
	}
}
