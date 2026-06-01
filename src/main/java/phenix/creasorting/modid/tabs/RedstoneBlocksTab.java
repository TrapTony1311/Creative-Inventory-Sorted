package phenix.creasorting.modid.tabs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RedstoneBlocksTab implements TabSorter {

    @Override
    public void register() {
        List<String> order = buildFullOrder();

        Map<String, Integer> rank = new HashMap<>();
        for (int i = 0; i < order.size(); i++) {
            rank.put(order.get(i), i);
        }

        Comparator<ItemStack> byRank = Comparator.comparingInt(stack -> {
            String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
            return rank.getOrDefault(id, Integer.MAX_VALUE);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(output -> {
            List<ItemStack> stacks = output.getDisplayStacks();

            Set<String> present = new HashSet<>();
            for (ItemStack s : stacks) {
                present.add(BuiltInRegistries.ITEM.getKey(s.getItem()).toString());
            }

            for (String id : order) {
                if (!id.equals("minecraft:air") && !present.contains(id)) {
                    Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(id));
                    if (item != Items.AIR) {
                        stacks.add(new ItemStack(item));
                    }
                }
            }

            stacks.removeIf(stack -> {
                String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
                return !rank.containsKey(id);
            });

            stacks.sort(byRank);
            output.getSearchTabStacks().sort(byRank);
        });
    }

    private static List<String> buildFullOrder() {
        List<String> ids = new ArrayList<>();

        List<String> items = List.of(
            "minecraft:redstone_block",
            "minecraft:redstone_torch",
            "minecraft:redstone",
            "minecraft:repeater",
            "minecraft:comparator",
            "minecraft:target",
            "minecraft:redstone_lamp",
            "minecraft:tnt",
            "minecraft:redstone_ore",
            "minecraft:light_weighted_pressure_plate",
            "minecraft:heavy_weighted_pressure_plate",
            "minecraft:stone_pressure_plate",
            "minecraft:polished_blackstone_pressure_plate",
            "minecraft:stone_button",
            "minecraft:polished_blackstone_button",
            "minecraft:lever",
            "minecraft:tripwire_hook",
            "minecraft:string",
            "minecraft:piston",
            "minecraft:sticky_piston",
            "minecraft:slime_block",
            "minecraft:honey_block",
            "minecraft:dispenser",
            "minecraft:dropper",
            "minecraft:observer",
            "minecraft:crafter",
            "minecraft:hopper",
            "minecraft:sculk_sensor",
            "minecraft:calibrated_sculk_sensor",
            "minecraft:sculk_shrieker",
            "minecraft:white_wool", // Defaulting to white wool unless you want a different one
            "minecraft:daylight_detector",
            "minecraft:lightning_rod",
            "minecraft:lectern",
            "minecraft:chest",
            "minecraft:trapped_chest",
            "minecraft:rail",
            "minecraft:powered_rail",
            "minecraft:detector_rail",
            "minecraft:activator_rail",
            "minecraft:minecart",
            "minecraft:hopper_minecart",
            "minecraft:chest_minecart",
            "minecraft:furnace_minecart",
            "minecraft:tnt_minecart",
            "minecraft:jukebox",
            "minecraft:note_block",
            "minecraft:chiseled_bookshelf",
            "minecraft:oak_shelf",
            "minecraft:barrel",
            "minecraft:composter",
            "minecraft:cauldron",
            "minecraft:oak_chest_boat",
            "minecraft:command_block"
        );

        ids.addAll(items);
        return ids;
    }
}