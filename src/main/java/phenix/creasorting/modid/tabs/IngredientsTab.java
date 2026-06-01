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

public class IngredientsTab implements TabSorter {

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

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
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
                return id.equals("minecraft:ancient_debris") || 
                       id.equals("minecraft:trial_key") || 
                       id.equals("minecraft:ominous_trial_key");
            });

            stacks.sort(byRank);
            output.getSearchTabStacks().sort(byRank);
        });
    }

    private static List<String> buildFullOrder() {
        List<String> ids = new ArrayList<>();

        List<String> ingredientItems = List.of(
            "minecraft:coal",
            "minecraft:charcoal",
            "minecraft:quartz",
            "minecraft:diamond",
            "minecraft:netherite_ingot",
            "minecraft:netherite_scrap",
            "minecraft:raw_copper",
            "minecraft:raw_iron",
            "minecraft:raw_gold",
            "minecraft:flint",
            "minecraft:bone",
            "minecraft:bone_meal",
            "minecraft:emerald",
            "minecraft:lapis_lazuli",
            "minecraft:amethyst_shard",
            "minecraft:copper_ingot",
            "minecraft:iron_ingot",
            "minecraft:gold_ingot",
            "minecraft:clay_ball",
            "minecraft:feather",
            "minecraft:string",
            "minecraft:ink_sac",
            "minecraft:glow_ink_sac",
            
            "minecraft:wheat",
            "minecraft:copper_nugget",
            "minecraft:iron_nugget",
            "minecraft:gold_nugget",
            "minecraft:blaze_rod",
            "minecraft:breeze_rod",
            "minecraft:stick",
            "minecraft:bowl",
            "minecraft:leather",
            "minecraft:rabbit_hide",
            "minecraft:brown_egg",
            "minecraft:blue_egg",
            "minecraft:egg",
            "minecraft:fire_charge",
            "minecraft:wind_charge",
            "minecraft:snowball",
            "minecraft:paper",
            "minecraft:book",
            "minecraft:honeycomb",


            "minecraft:brick",
            "minecraft:nether_brick",
            "minecraft:resin_brick",
            "minecraft:ender_pearl",
            "minecraft:ender_eye",
            "minecraft:heart_of_the_sea",
            "minecraft:nautilus_shell",
            "minecraft:prismarine_shard",
            "minecraft:prismarine_crystals",
            "minecraft:turtle_scute",
            "minecraft:armadillo_scute",
            "minecraft:resin_clump",


            "minecraft:echo_shard",
            "minecraft:disc_fragment_5",
            "minecraft:heavy_core",
            "minecraft:nether_star",
            "minecraft:shulker_shell",
            "minecraft:popped_chorus_fruit",
            "minecraft:dragon_breath",
            "minecraft:experience_bottle",
            "minecraft:glass_bottle",
            "minecraft:redstone",
            "minecraft:glowstone_dust",
            "minecraft:gunpowder",
            "minecraft:sugar",
            "minecraft:brown_mushroom",
            "minecraft:spider_eye",
            "minecraft:fermented_spider_eye",
            "minecraft:nether_wart",
            "minecraft:ghast_tear",
            "minecraft:blaze_powder",
            "minecraft:glistering_melon_slice",
            "minecraft:golden_carrot",
            "minecraft:pufferfish",
            "minecraft:rabbit_foot",
            "minecraft:phantom_membrane",
            "minecraft:turtle_helmet",
            "minecraft:slime_ball",
            "minecraft:magma_cream",
            "minecraft:white_dye",
            "minecraft:light_gray_dye",
            "minecraft:gray_dye",
            "minecraft:black_dye",
            "minecraft:firework_star",
            "minecraft:brown_dye",
            "minecraft:yellow_dye",
            "minecraft:orange_dye",
            "minecraft:red_dye",
            "minecraft:lime_dye",
            "minecraft:green_dye",
            "minecraft:cyan_dye",
            "minecraft:light_blue_dye",
            "minecraft:globe_banner_pattern",
            "minecraft:blue_dye",
            "minecraft:purple_dye",
            "minecraft:magenta_dye",
            "minecraft:pink_dye",
            "minecraft:field_masoned_banner_pattern",
            "minecraft:bordure_indented_banner_pattern",
            "minecraft:flower_banner_pattern",
            "minecraft:creeper_banner_pattern",
            "minecraft:skull_banner_pattern",
            "minecraft:mojang_banner_pattern",
            "minecraft:piglin_banner_pattern",
            "minecraft:flow_banner_pattern",
            "minecraft:guster_banner_pattern"
        );

        ids.addAll(ingredientItems);
        return ids;
    }
}