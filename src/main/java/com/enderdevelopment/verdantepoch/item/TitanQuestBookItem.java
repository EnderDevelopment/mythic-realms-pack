package com.enderdevelopment.verdantepoch.item;

import com.enderdevelopment.verdantepoch.gui.QuestBookScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * The Verdant Epoch quest book. Right-click it to open the quest line that
 * guides the player from their first spell to the defeat of the Titan.
 */
public class TitanQuestBookItem extends Item {

    public TitanQuestBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_BOOK_PAGE_TURN,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);
            return TypedActionResult.success(stack);
        }
        openGui();
        return TypedActionResult.success(stack);
    }

    @Environment(EnvType.CLIENT)
    private static void openGui() {
        MinecraftClient client = MinecraftClient.getInstance();
        client.setScreen(new QuestBookScreen());
    }
}
