package me.therealfickle.rabid.init.misc;

import me.therealfickle.rabid.data.tags.RabidItemTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

import static me.therealfickle.rabid.Rabid.id;

public interface RabidToolMaterials {

    Identifier BASE_ENTITY_INTERACTION_RANGE_ID = id("base_entity_interaction_range");


    ToolMaterial SFA = new ToolMaterial(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            1561, // Durability
            8.0F, // Speed
            3.0F, // Attack Damage Bonus
            13, // Enchantment Value
            RabidItemTags.SFA_TOOL_MATERIALS
    );


    static Item.Properties applyCommonProperties(Item.Properties properties, ToolMaterial material) {
        return properties.durability(material.durability()).repairable(material.repairItems()).enchantable(material.enchantmentValue());
    }

    @SuppressWarnings("deprecation")
    static Item.Properties applyGlaiveProperties(Item.Properties properties, ToolMaterial material, float damage, float speed) {
        HolderGetter<Block> holderGetter = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return applyCommonProperties(properties, material)
                .component(
                        DataComponents.TOOL,
                        new Tool(
                                List.of(
                                        Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
                                        Tool.Rule.overrideSpeed(holderGetter.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                                        Tool.Rule.overrideSpeed(holderGetter.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
                                ),
                                1.0F,
                                2,
                                false
                        )
                )
                .attributes(createGlaiveAttributes(material, damage, speed, 0.5f))
                .component(DataComponents.WEAPON, new Weapon(1));
    }

    static ItemAttributeModifiers createGlaiveAttributes(ToolMaterial material, float damage, float speed, float reach) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, damage + material.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, reach, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }
}
