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

public class ToolsAndUtilitiesTab implements TabSorter {

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

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {
            List<ItemStack> stacks = output.getDisplayStacks();

            Set<String> present = new HashSet<>();
            for (ItemStack s : stacks) {
                present.add(BuiltInRegistries.ITEM.getKey(s.getItem()).toString());
            }

            for (String id : order) {
                // Skip adding duplicates manually for fireworks and goat horns since vanilla generates them
                if (!id.equals("minecraft:air") && 
                    !id.equals("minecraft:firework_rocket") && 
                    !id.equals("minecraft:goat_horn") && 
                    !present.contains(id)) {
                    
                    Item item = BuiltInRegistries.ITEM.getValue(Identifier.parse(id));
                    if (item != Items.AIR) {
                        stacks.add(new ItemStack(item));
                    }
                }
            }

            stacks.removeIf(stack -> {
                String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
                // Never remove firework rockets or goat horns from this tab
                if (id.equals("minecraft:firework_rocket") || id.equals("minecraft:goat_horn")) {
                    return false;
                }
                return !rank.containsKey(id);
            });

            stacks.sort(byRank);
            output.getSearchTabStacks().sort(byRank);
        });
    }

    private static List<String> buildFullOrder() {
        List<String> ids = new ArrayList<>();

        // The 5-column utility list to weave into the grid
        List<String> sideUtilities = List.of(
            "minecraft:fishing_rod", "minecraft:carrot_on_a_stick", "minecraft:warped_fungus_on_a_stick", "minecraft:compass", "minecraft:recovery_compass",
            "minecraft:debug_stick", "minecraft:flint_and_steel", "minecraft:shears", "minecraft:clock", "minecraft:spyglass",
            "minecraft:name_tag", "minecraft:lead", "minecraft:saddle", "minecraft:golden_dandelion", "minecraft:writable_book", 
            "minecraft:elytra", "minecraft:firework_rocket", "minecraft:firework_rocket", "minecraft:firework_rocket", "minecraft:map",
            "minecraft:turtle_helmet", "minecraft:brush", "minecraft:totem_of_undying", "minecraft:ender_pearl", "minecraft:bucket",
            "minecraft:water_bucket", "minecraft:lava_bucket", "minecraft:powder_snow_bucket", "minecraft:milk_bucket", "minecraft:sulfur_cube_bucket",
            "minecraft:cod_bucket", "minecraft:salmon_bucket", "minecraft:tropical_fish_bucket", "minecraft:pufferfish_bucket", "minecraft:axolotl_bucket"
        );

        List<String> tiers = List.of("netherite", "diamond", "golden", "iron", "copper", "stone", "wooden");

        int utilityIndex = 0;
        for (String tier : tiers) {
            ids.add("minecraft:" + tier + "_pickaxe");
            ids.add("minecraft:" + tier + "_axe");
            ids.add("minecraft:" + tier + "_shovel");
            ids.add("minecraft:" + tier + "_hoe");

            for (int i = 0; i < 5; i++) {
                if (utilityIndex < sideUtilities.size()) {
                    ids.add(sideUtilities.get(utilityIndex++));
                } else {
                    ids.add("minecraft:air");
                }
            }
        }

        // Bundles line 1 + Tadpole bucket
        List<String> bundlesRow1 = List.of("white", "light_gray", "gray", "black", "brown", "red", "orange", "yellow");
        for (String color : bundlesRow1) {
            ids.add("minecraft:" + color + "_bundle");
        }
        ids.add("minecraft:tadpole_bucket");

        // Bundles line 2 + Normal bundle
        List<String> bundlesRow2 = List.of("lime", "green", "cyan", "blue", "light_blue", "purple", "magenta", "pink");
        for (String color : bundlesRow2) {
            ids.add("minecraft:" + color + "_bundle");
        }
        ids.add("minecraft:bundle");

        // Harness / Raft row 1
        ids.add("minecraft:white_harness");
        ids.add("minecraft:light_gray_harness");
        ids.add("minecraft:gray_harness");
        ids.add("minecraft:black_harness");
        ids.add("minecraft:brown_harness");
        ids.add("minecraft:red_harness");
        ids.add("minecraft:orange_harness");
        ids.add("minecraft:yellow_harness");
        ids.add("minecraft:bamboo_raft");

        // Harness / Raft row 2
        ids.add("minecraft:lime_harness");
        ids.add("minecraft:green_harness");
        ids.add("minecraft:cyan_harness");
        ids.add("minecraft:light_blue_harness");
        ids.add("minecraft:blue_harness");
        ids.add("minecraft:purple_harness");
        ids.add("minecraft:magenta_harness");
        ids.add("minecraft:pink_harness");
        ids.add("minecraft:bamboo_chest_raft");

        // Wood type boats
        List<String> woodTypes = List.of("pale_oak", "birch", "oak", "spruce", "dark_oak", "mangrove", "acacia", "jungle", "cherry");
        for (String wood : woodTypes) {
            ids.add("minecraft:" + wood + "_boat");
        }
        for (String wood : woodTypes) {
            ids.add("minecraft:" + wood + "_chest_boat");
        }

        // Rails and Minecarts row
        List<String> railAndCarts = List.of(
            "minecraft:rail", "minecraft:powered_rail", "minecraft:detector_rail", "minecraft:activator_rail",
            "minecraft:minecart", "minecraft:hopper_minecart", "minecraft:chest_minecart", "minecraft:furnace_minecart", "minecraft:tnt_minecart"
        );
        ids.addAll(railAndCarts);

        // --- NEW CONTENT STARTS HERE ---

        // Goat Horn placeholder entry so the custom sorting sequence registers its position
        ids.add("minecraft:goat_horn");

        // Remaining elements sequence
        List<String> endSequence = List.of(
            "minecraft:experience_bottle",
            "minecraft:fire_charge",
            "minecraft:music_disc_13",
            "minecraft:music_disc_cat",
            "minecraft:music_disc_blocks",
            "minecraft:music_disc_chirp",
            "minecraft:music_disc_far",
            "minecraft:music_disc_mall",
            "minecraft:music_disc_mellohi",
            "minecraft:ominous_trial_key",
            "minecraft:wind_charge",
            "minecraft:music_disc_ward",
            "minecraft:music_disc_wait",
            "minecraft:music_disc_5",
            "minecraft:jukebox",
            "minecraft:music_disc_11",
            "minecraft:music_disc_stal",
            "minecraft:music_disc_strad",
            "minecraft:trial_key",
            "minecraft:music_disc_bounce",
            "minecraft:music_disc_creator_music_box",
            "minecraft:music_disc_precipice",
            "minecraft:music_disc_creator",
            "minecraft:music_disc_pigstep",
            "minecraft:music_disc_otherside",
            "minecraft:music_disc_relic",
            "minecraft:music_disc_tears",
            "minecraft:music_disc_lava_chicken"
        );
        ids.addAll(endSequence);

        return ids;
    }
}