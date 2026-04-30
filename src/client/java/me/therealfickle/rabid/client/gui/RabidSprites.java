package me.therealfickle.rabid.client.gui;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.Map;

import static me.therealfickle.rabid.Rabid.id;
import static me.therealfickle.rabid.client.RabidClient.renderAsFuel;

public interface RabidSprites {

    Identifier FUEL_EMPTY = id("hud/fuel_empty");
    Identifier FUEL_HALF = id("hud/fuel_half");
    Identifier FUEL = id("hud/fuel");

    Identifier HUNGER_FUEL_EMPTY = id("hud/hunger_fuel_empty");
    Identifier HUNGER_FUEL_HALF = id("hud/hunger_fuel_half");
    Identifier HUNGER_FUEL = id("hud/hunger_fuel");

    Identifier HEAT_BAR_PROGRESS = id("hud/heat_bar_progress");
    Identifier HEAT_BAR_BACKGROUND = id("hud/heat_bar_background");

    Identifier APPLESKIN_SPRITES = id("textures/gui/appleskin_icons.png");

    Identifier FOOD_EMPTY_HUNGER = Identifier.withDefaultNamespace("hud/food_empty_hunger");
    Identifier FOOD_HALF_HUNGER = Identifier.withDefaultNamespace("hud/food_half_hunger");
    Identifier FOOD_FULL_HUNGER = Identifier.withDefaultNamespace("hud/food_full_hunger");
    Identifier FOOD_EMPTY = Identifier.withDefaultNamespace("hud/food_empty");
    Identifier FOOD_HALF = Identifier.withDefaultNamespace("hud/food_half");
    Identifier FOOD_FULL = Identifier.withDefaultNamespace("hud/food_full");

    Identifier HUNGER_OUTLINE_SPRITE = Identifier.fromNamespaceAndPath("appleskin", "tooltip_hunger_outline");
    Identifier FUEL_HUNGER_OUTLINE = id("tooltip_hunger_outline");

    Map<Identifier, Identifier> FUEL_REMAP = Map.of(
            HUNGER_OUTLINE_SPRITE, FUEL_HUNGER_OUTLINE,
            FOOD_EMPTY_HUNGER, FUEL_EMPTY,
            FOOD_EMPTY, FUEL_EMPTY,
            FOOD_HALF_HUNGER, FUEL_HALF,
            FOOD_HALF, FUEL_HALF,
            FOOD_FULL_HUNGER, FUEL,
            FOOD_FULL, FUEL
    );


    static Identifier getFuelTexture(Identifier texture, @Nullable ItemStack itemStack) {
        var fuelTexture = FUEL_REMAP.get(texture);
        return (itemStack == null || renderAsFuel(itemStack)) && fuelTexture != null ? fuelTexture : texture;
    }

}
