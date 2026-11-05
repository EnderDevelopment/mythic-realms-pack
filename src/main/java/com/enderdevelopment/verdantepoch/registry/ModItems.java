package com.enderdevelopment.verdantepoch.registry;

import com.enderdevelopment.verdantepoch.VerdantEpoch;
import com.enderdevelopment.verdantepoch.item.FireStaffItem;
import com.enderdevelopment.verdantepoch.item.FrostWandItem;
import com.enderdevelopment.verdantepoch.item.TitanQuestBookItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * Registers all custom items with the game.
 */
public final class ModItems {
    public static final Item TITAN_QUEST_BOOK = new TitanQuestBookItem(
            new FabricItemSettings().maxCount(1));

    public static final Item FIRE_STAFF = new FireStaffItem(
            new FabricItemSettings().maxCount(1));

    public static final Item FROST_WAND = new FrostWandItem(
            new FabricItemSettings().maxCount(1));

    public static final RegistryKey<ItemGroup> GROUP = RegistryKey.of(
            RegistryKeys.ITEM_GROUP, new Identifier(VerdantEpoch.MOD_ID, "main"));

    private ModItems() {
    }

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, GROUP.getValue(), FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.verdantepoch.main"))
                .icon(() -> new ItemStack(TITAN_QUEST_BOOK))
                .build());

        ItemGroupEvents.modifyEntriesEvent(GROUP).register(content -> {
            content.add(TITAN_QUEST_BOOK);
            content.add(FIRE_STAFF);
            content.add(FROST_WAND);
        });

        registerItem("titan_quest_book", TITAN_QUEST_BOOK);
        registerItem("fire_staff", FIRE_STAFF);
        registerItem("frost_wand", FROST_WAND);
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(VerdantEpoch.MOD_ID, name), item);
    }
}
