package me.therealfickle.rabid.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidSprites {
    ResourceLocation FUEL_EMPTY = id("hud/fuel_empty");
    ResourceLocation FUEL_HALF = id("hud/fuel_half");
    ResourceLocation FUEL = id("hud/fuel");

    ResourceLocation HUNGER_FUEL_EMPTY = id("hud/hunger_fuel_empty");
    ResourceLocation HUNGER_FUEL_HALF = id("hud/hunger_fuel_half");
    ResourceLocation HUNGER_FUEL = id("hud/hunger_fuel");

    ResourceLocation HEAT_BAR = id("hud/heat_bar");



}
