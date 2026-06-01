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

public class FoodAndDrinksTab implements TabSorter {

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

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output -> {
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

            // ONLY remove the golden dandelion, keeping the rest of vanilla's default items
            stacks.removeIf(stack -> {
                String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
                return id.equals("minecraft:golden_dandelion");
            });

            stacks.sort(byRank);
            output.getSearchTabStacks().sort(byRank);
        });
    }

    private static List<String> buildFullOrder() {
        List<String> ids = new ArrayList<>();

        List<String> foodItems = List.of(
            "minecraft:apple",
            "minecraft:potato",
            "minecraft:carrot",
            "minecraft:sweet_berries",
            "minecraft:beef",
            "minecraft:porkchop", // Vanilla ID for raw porkchop is just porkchop
            "minecraft:mutton",   // Vanilla ID for raw mutton is just mutton
            "minecraft:chicken",  // Vanilla ID for raw chicken is just chicken
            "minecraft:rabbit",   // Vanilla ID for raw rabbit is just rabbit
            "minecraft:golden_apple",
            "minecraft:baked_potato",
            "minecraft:golden_carrot",
            "minecraft:glow_berries",
            "minecraft:cooked_beef", // Vanilla ID for steak is cooked_beef
            "minecraft:cooked_porkchop",
            "minecraft:cooked_mutton",
            "minecraft:cooked_chicken",
            "minecraft:cooked_rabbit",
            "minecraft:enchanted_golden_apple",
            "minecraft:poisonous_potato",
            "minecraft:cake",
            "minecraft:bread",
            "minecraft:cookie",
            "minecraft:pumpkin_pie",
            "minecraft:melon_slice",
            "minecraft:chorus_fruit",
            "minecraft:dried_kelp",
            "minecraft:rotten_flesh",
            "minecraft:spider_eye",
            "minecraft:beetroot",
            "minecraft:cod",        // Vanilla ID for raw cod is just cod
            "minecraft:cooked_cod",
            "minecraft:salmon",     // Vanilla ID for raw salmon is just salmon
            "minecraft:cooked_salmon",
            "minecraft:tropical_fish",
            "minecraft:pufferfish",
            "minecraft:mushroom_stew",
            "minecraft:rabbit_stew",
            "minecraft:beetroot_soup"
        );

        ids.addAll(foodItems);
        return ids;
    }
}