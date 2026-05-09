package me.therealfickle.rabid.datagen.data.tags;

import me.therealfickle.rabid.data.tags.CBlockTags;
import me.therealfickle.rabid.data.tags.CItemTags;
import me.therealfickle.rabid.data.tags.RabidItemTags;
import me.therealfickle.rabid.init.RabidItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ItemTagsProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, BlockTagProvider blockTags) {
        super(output, registriesFuture, blockTags);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // Mod Tags
        valueLookupBuilder(RabidItemTags.ADDITIONAL_FICKLE_FUELS).add(Items.OMINOUS_BOTTLE);
        valueLookupBuilder(RabidItemTags.MATTER_RECONSTRUCTOR_FUELS).add(RabidItems.POLONIUM_PELLET);
        valueLookupBuilder(RabidItemTags.SFA_TOOL_MATERIALS).add(RabidItems.SFA_INGOT);


        // Vanilla Tags
        valueLookupBuilder(ItemTags.BEACON_PAYMENT_ITEMS).add(RabidItems.SFA_INGOT);
        valueLookupBuilder(ItemTags.SWORDS).add(RabidItems.SFA_GLAIVE);


        // Common Tags
        valueLookupBuilder(ConventionalItemTags.STORAGE_BLOCKS).forceAddTag(CItemTags.STORAGE_BLOCK_SFA);
        valueLookupBuilder(CItemTags.POLONIUM).add(RabidItems.POLONIUM_PELLET);

        valueLookupBuilder(CItemTags.POLONIUM_NUGGETS).add(RabidItems.POLONIUM_NUGGET);
        valueLookupBuilder(ConventionalItemTags.NUGGETS).forceAddTag(CItemTags.POLONIUM_NUGGETS);

        valueLookupBuilder(CItemTags.SFA_INGOTS).add(RabidItems.SFA_INGOT);
        valueLookupBuilder(ConventionalItemTags.INGOTS).forceAddTag(CItemTags.SFA_INGOTS);

        valueLookupBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(RabidItems.SFA_GLAIVE);
        valueLookupBuilder(ConventionalItemTags.MELEE_WEAPONS_TOOLS).add(RabidItems.SFA_GLAIVE);
        valueLookupBuilder(ConventionalItemTags.RANGED_WEAPON_TOOLS).add(RabidItems.HERACLES);
        valueLookupBuilder(ConventionalItemTags.RANGED_WEAPONS_TOOLS).add(RabidItems.HERACLES);


        // Tag Copying
        copy(CBlockTags.CRATES, CItemTags.CRATES);
        copy(CBlockTags.STORAGE_BLOCK_SFA, CItemTags.STORAGE_BLOCK_SFA);
        copy(CBlockTags.PLAYER_WORKSTATIONS_MATTER_RECONSTRUCTORS, CItemTags.PLAYER_WORKSTATIONS_MATTER_RECONSTRUCTORS);
    }
}
