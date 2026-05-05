package me.therealfickle.rabid.client.gui.screens;

import me.therealfickle.rabid.block.entity.MatterReconstructorBlockEntity;
import me.therealfickle.rabid.client.mixin.GhostSlotsAccessor;
import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidItems;
import me.therealfickle.rabid.inventory.MatterReconstructorMenu;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.recipebook.PlaceRecipeHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;

import java.util.List;

import static me.therealfickle.rabid.init.RabidRecipes.*;

public class MatterReconstructorRecipeBookComponent extends RecipeBookComponent<MatterReconstructorMenu> {

    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            Identifier.withDefaultNamespace("recipe_book/furnace_filter_enabled"),
            Identifier.withDefaultNamespace("recipe_book/furnace_filter_disabled"),
            Identifier.withDefaultNamespace("recipe_book/furnace_filter_enabled_highlighted"),
            Identifier.withDefaultNamespace("recipe_book/furnace_filter_disabled_highlighted")
    );

    private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
            new RecipeBookComponent.TabInfo(SearchRecipeBookCategory.RABID_RECONSTRUCTING),
            new RecipeBookComponent.TabInfo(RabidBlocks.SFA_CRATE.asItem(), RECONSTRUCTING_BUILDING),
            new RecipeBookComponent.TabInfo(RabidItems.SFA_INGOT, RECONSTRUCTING_MATERIALS),
            new RecipeBookComponent.TabInfo(RabidItems.EXPERIMENTAL_HELR_CALLER, RECONSTRUCTING_EQUIPMENT),
            new RecipeBookComponent.TabInfo(RabidBlocks.FICKLE_PLUSH.asItem(), RECONSTRUCTING_MISC)
    );

    public MatterReconstructorRecipeBookComponent(MatterReconstructorMenu menu) {
        super(menu, TABS);
    }

    @Override
    protected WidgetSprites getFilterButtonTextures() {
        return FILTER_SPRITES;
    }

    @Override
    protected boolean isCraftingSlot(Slot slot) {
        return menu.getCraftingSlots().contains(slot);
    }

    @Override
    protected void fillGhostRecipe(GhostSlots rawGhostSlots, RecipeDisplay recipeDisplay, ContextMap contextMap) {
        var ghostSlots = (GhostSlotsAccessor) rawGhostSlots;

        ghostSlots.rabid_setResult(menu.getResultSlot(), contextMap, recipeDisplay.result());

        switch (recipeDisplay) {
            case ShapedCraftingRecipeDisplay display:
                List<Slot> list = menu.getCraftingSlots();
                PlaceRecipeHelper.placeRecipe(
                        MatterReconstructorBlockEntity.GRID_WIDTH,
                        MatterReconstructorBlockEntity.GRID_HEIGHT,
                        display.width(),
                        display.height(),
                        display.ingredients(),
                        (slotDisplay, ix, jx, k) -> {
                            Slot slot = list.get(ix);
                            ghostSlots.rabid_setInput(slot, contextMap, slotDisplay);
                        }
                );
                break;
            case ShapelessCraftingRecipeDisplay display: {
                List<Slot> list2 = menu.getCraftingSlots();
                int i = Math.min(display.ingredients().size(), list2.size());

                for (int j = 0; j < i; j++) {
                    ghostSlots.rabid_setInput(list2.get(j), contextMap, display.ingredients().get(j));
                }
            }
            default:
        }
    }

    @Override
    protected Component getRecipeFilterName() {
        return Component.literal("I have no idea what this does");
    }

    @Override
    protected void selectMatchingRecipes(RecipeCollection recipeCollection, StackedItemContents stackedItemContents) {
        recipeCollection.selectRecipes(stackedItemContents, recipeDisplay -> true);
    }
}