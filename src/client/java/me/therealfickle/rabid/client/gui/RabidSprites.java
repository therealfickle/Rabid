package me.therealfickle.rabid.client.gui;

import net.minecraft.resources.Identifier;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidSprites {
    Identifier FUEL_EMPTY = id("hud/fuel_empty");
    Identifier FUEL_HALF = id("hud/fuel_half");
    Identifier FUEL = id("hud/fuel");

    Identifier HUNGER_FUEL_EMPTY = id("hud/hunger_fuel_empty");
    Identifier HUNGER_FUEL_HALF = id("hud/hunger_fuel_half");
    Identifier HUNGER_FUEL = id("hud/hunger_fuel");

    Identifier HEAT_BAR_PROGRESS = id("hud/heat_bar_progress");
    Identifier HEAT_BAR_BACKGROUND = id("hud/heat_bar_background");

}
