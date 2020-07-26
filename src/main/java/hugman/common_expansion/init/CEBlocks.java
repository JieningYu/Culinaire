package hugman.common_expansion.init;

import hugman.common_expansion.CommonExpansion;
import hugman.common_expansion.objects.block.MilkCauldronBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CEBlocks {
	public static final Block MILK_CAULDRON = register("milk_cauldron", new MilkCauldronBlock(FabricBlockSettings.of(Material.METAL, MaterialColor.STONE).requiresTool().strength(2.0F).nonOpaque()));

	private static Block register(String name, Block block) {
		return Registry.register(Registry.BLOCK, CommonExpansion.id(name), block);
	}
}
