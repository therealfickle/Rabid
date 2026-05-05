package me.therealfickle.rabid.item.crafting;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.therealfickle.rabid.init.RabidBlocks;
import me.therealfickle.rabid.init.RabidRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ShapedReconstructorRecipe implements ReconstructorRecipe {
    final ShapedRecipePattern pattern;
    final ItemStack result;
    final String group;
    final ReconstructingBookCategory category;
    final int assemblyTime;
    final boolean showNotification;
    @Nullable
    private PlacementInfo placementInfo;

    public ShapedReconstructorRecipe(String string, ReconstructingBookCategory category, ShapedRecipePattern shapedRecipePattern, ItemStack itemStack, int assemblyTime, boolean bl) {
        this.group = string;
        this.category = category;
        this.pattern = shapedRecipePattern;
        this.result = itemStack;
        this.assemblyTime = assemblyTime;
        this.showNotification = bl;
    }

    public ShapedReconstructorRecipe(String string, ReconstructingBookCategory craftingBookCategory, ShapedRecipePattern shapedRecipePattern, ItemStack itemStack, int assemblyTime) {
        this(string, craftingBookCategory, shapedRecipePattern, itemStack, assemblyTime, true);
    }

    @Override
    public RecipeSerializer<? extends ShapedReconstructorRecipe> getSerializer() {
        return RabidRecipes.RECONSTRUCTING_SHAPED;
    }

    @Override
    public String group() {
        return this.group;
    }

    @Override
    public ReconstructingBookCategory category() {
        return this.category;
    }

    @Override
    public int getAssemblyTime() {
        return assemblyTime;
    }

    @VisibleForTesting
    public List<Optional<Ingredient>> getIngredients() {
        return this.pattern.ingredients();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.createFromOptionals(this.pattern.ingredients());
        }

        return this.placementInfo;
    }

    @Override
    public boolean showNotification() {
        return this.showNotification;
    }

    public boolean matches(CraftingInput craftingInput, Level level) {
        return this.pattern.matches(craftingInput);
    }

    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        return this.result.copy();
    }

    public int getWidth() {
        return this.pattern.width();
    }

    public int getHeight() {
        return this.pattern.height();
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new ShapedCraftingRecipeDisplay(
                        this.pattern.width(),
                        this.pattern.height(),
                        this.pattern.ingredients().stream().map(optional -> optional.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE)).toList(),
                        new SlotDisplay.ItemStackSlotDisplay(this.result),
                        new SlotDisplay.ItemSlotDisplay(RabidBlocks.MATTER_RECONSTRUCTOR.asItem())
                )
        );
    }

    public static class Serializer implements RecipeSerializer<ShapedReconstructorRecipe> {
        public final MapCodec<ShapedReconstructorRecipe> codec;
        public static final StreamCodec<RegistryFriendlyByteBuf, ShapedReconstructorRecipe> STREAM_CODEC = StreamCodec.of(
                ShapedReconstructorRecipe.Serializer::toNetwork, ShapedReconstructorRecipe.Serializer::fromNetwork
        );

        public Serializer(int defaultTime) {
            codec = RecordCodecBuilder.mapCodec(instance ->
                    instance
                            .group(
                                    Codec.STRING.optionalFieldOf("group", "").forGetter(shapedRecipe -> shapedRecipe.group),
                                    ReconstructingBookCategory.CODEC.fieldOf("category").orElse(ReconstructingBookCategory.MISC).forGetter(shapedRecipe -> shapedRecipe.category),
                                    ShapedRecipePattern.MAP_CODEC.forGetter(shapedRecipe -> shapedRecipe.pattern),
                                    ItemStack.STRICT_CODEC.fieldOf("result").forGetter(shapedRecipe -> shapedRecipe.result),
                                    Codec.INT.fieldOf("assembly_time").orElse(defaultTime).forGetter(ReconstructorRecipe::getAssemblyTime),
                                    Codec.BOOL.optionalFieldOf("show_notification", true).forGetter(shapedRecipe -> shapedRecipe.showNotification)
                            )
                            .apply(instance, ShapedReconstructorRecipe::new)
            );
        }

        @Override
        public MapCodec<ShapedReconstructorRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapedReconstructorRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ShapedReconstructorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String string = buffer.readUtf();
            ReconstructingBookCategory craftingBookCategory = buffer.readEnum(ReconstructingBookCategory.class);
            ShapedRecipePattern shapedRecipePattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
            ItemStack itemStack = ItemStack.STREAM_CODEC.decode(buffer);
            int assemblyTime = buffer.readInt();
            boolean bl = buffer.readBoolean();
            return new ShapedReconstructorRecipe(string, craftingBookCategory, shapedRecipePattern, itemStack, assemblyTime, bl);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapedReconstructorRecipe shapedRecipe) {
            buffer.writeUtf(shapedRecipe.group);
            buffer.writeEnum(shapedRecipe.category);
            ShapedRecipePattern.STREAM_CODEC.encode(buffer, shapedRecipe.pattern);
            ItemStack.STREAM_CODEC.encode(buffer, shapedRecipe.result);
            buffer.writeInt(shapedRecipe.assemblyTime);
            buffer.writeBoolean(shapedRecipe.showNotification);
        }
    }
}
