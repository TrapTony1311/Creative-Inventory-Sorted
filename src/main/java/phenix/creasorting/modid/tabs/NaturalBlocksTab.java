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

public class NaturalBlocksTab implements TabSorter {

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

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output -> {
            List<ItemStack> stacks = output.getDisplayStacks();

            Set<String> present = new HashSet<>();
            for (ItemStack s : stacks) {
                present.add(BuiltInRegistries.ITEM.getKey(s.getItem()).toString());
            }

            for (String id : order) {
                if (!present.contains(id)) {
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

    private static final List<String> NATURAL_BLOCKS = List.of(
        "minecraft:grass_block",
        "minecraft:podzol",
        "minecraft:mycelium",
        "minecraft:dirt_path",
        "minecraft:crimson_nylium",
        "minecraft:warped_nylium",
        "minecraft:mud",
        "minecraft:muddy_mangrove_roots",
        "minecraft:mangrove_roots",

        "minecraft:dirt",
        "minecraft:coarse_dirt",
        "minecraft:rooted_dirt",
        "minecraft:farmland",
        "minecraft:nether_wart_block",
        "minecraft:warped_wart_block",
        "minecraft:mushroom_stem",
        "minecraft:brown_mushroom_block",
        "minecraft:red_mushroom_block",

        "minecraft:pumpkin",
        "minecraft:carved_pumpkin",
        "minecraft:jack_o_lantern",
        "minecraft:honeycomb_block",
        "minecraft:honey_block",
        "minecraft:bee_nest",
        "minecraft:snow",
        "minecraft:snow_block",
        "minecraft:calcite",

        "minecraft:hay_block",
        "minecraft:sponge",
        "minecraft:wet_sponge",
        "minecraft:melon",
        "minecraft:slime_block",
        "minecraft:ice",
        "minecraft:packed_ice",
        "minecraft:blue_ice",
        "minecraft:clay"
    );

    private static List<String> buildFullOrder() {
        List<String> ids = new ArrayList<>();

        ids.addAll(NATURAL_BLOCKS);

        ids.add("minecraft:oak_leaves");
        ids.add("minecraft:spruce_leaves");
        ids.add("minecraft:birch_leaves");
        ids.add("minecraft:jungle_leaves");
        ids.add("minecraft:acacia_leaves");
        ids.add("minecraft:dark_oak_leaves");
        ids.add("minecraft:mangrove_leaves");
        ids.add("minecraft:azalea_leaves");
        ids.add("minecraft:flowering_azalea_leaves");

        ids.add("minecraft:cherry_leaves");
        ids.add("minecraft:pale_oak_leaves");
        ids.add("minecraft:creaking_heart");
        ids.add("minecraft:pale_moss_block");
        ids.add("minecraft:pale_moss_carpet");
        ids.add("minecraft:moss_carpet");
        ids.add("minecraft:moss_block");
        ids.add("minecraft:azalea");
        ids.add("minecraft:flowering_azalea");

        ids.add("minecraft:oak_sapling");
        ids.add("minecraft:spruce_sapling");
        ids.add("minecraft:birch_sapling");
        ids.add("minecraft:jungle_sapling");
        ids.add("minecraft:acacia_sapling");
        ids.add("minecraft:dark_oak_sapling");
        ids.add("minecraft:mangrove_propagule");
        ids.add("minecraft:cherry_sapling");
        ids.add("minecraft:pale_oak_sapling");

        ids.add("minecraft:short_grass");
        ids.add("minecraft:fern");
        ids.add("minecraft:bush");
        ids.add("minecraft:tall_grass");
        ids.add("minecraft:large_fern");
        ids.add("minecraft:dead_bush");
        ids.add("minecraft:firefly_bush");
        ids.add("minecraft:tall_dry_grass");
        ids.add("minecraft:short_dry_grass");

        ids.add("minecraft:dandelion");
        ids.add("minecraft:poppy");
        ids.add("minecraft:red_tulip");
        ids.add("minecraft:orange_tulip");
        ids.add("minecraft:pink_tulip");
        ids.add("minecraft:white_tulip");
        ids.add("minecraft:oxeye_daisy");
        ids.add("minecraft:azure_bluet");
        ids.add("minecraft:lily_of_the_valley");

        ids.add("minecraft:cornflower");
        ids.add("minecraft:blue_orchid");
        ids.add("minecraft:allium");
        ids.add("minecraft:torchflower");
        ids.add("minecraft:closed_eyeblossom");
        ids.add("minecraft:open_eyeblossom");
        ids.add("minecraft:wither_rose");
        ids.add("minecraft:golden_dandelion");
        ids.add("minecraft:flower_pot");

        ids.add("minecraft:lilac");
        ids.add("minecraft:rose_bush");
        ids.add("minecraft:peony");
        ids.add("minecraft:pitcher_plant");
        ids.add("minecraft:sunflower");
        ids.add("minecraft:spore_blossom");
        ids.add("minecraft:pink_petals");
        ids.add("minecraft:wildflowers");
        ids.add("minecraft:leaf_litter");

        ids.add("minecraft:warped_fungus");
        ids.add("minecraft:crimson_fungus");
        ids.add("minecraft:nether_sprouts");
        ids.add("minecraft:crimson_roots");
        ids.add("minecraft:warped_roots");
        ids.add("minecraft:weeping_vines");
        ids.add("minecraft:twisting_vines");
        ids.add("minecraft:nether_wart");
        ids.add("minecraft:dried_ghast");
        
        ids.add("minecraft:red_mushroom");
        ids.add("minecraft:brown_mushroom");
        ids.add("minecraft:glow_lichen");
        ids.add("minecraft:hanging_roots");
        ids.add("minecraft:pale_hanging_moss");
        ids.add("minecraft:resin_clump");
        ids.add("minecraft:chorus_plant");
        ids.add("minecraft:chorus_flower");
        ids.add("minecraft:dragon_egg");
        

        ids.add("minecraft:wheat_seeds");
        ids.add("minecraft:potato");
        ids.add("minecraft:pumpkin_seeds");
        ids.add("minecraft:sweet_berries");
        ids.add("minecraft:cocoa_beans");
        ids.add("minecraft:torchflower_seeds");
        ids.add("minecraft:sugar_cane");
        ids.add("minecraft:vine");
        ids.add("minecraft:bone_meal");
        
        ids.add("minecraft:beetroot_seeds");
        ids.add("minecraft:carrot");
        ids.add("minecraft:melon_seeds");
        ids.add("minecraft:glow_berries");
        ids.add("minecraft:bamboo");
        ids.add("minecraft:pitcher_pod");
        ids.add("minecraft:cactus");
        ids.add("minecraft:cactus_flower");
        ids.add("minecraft:sniffer_egg");

        
        ids.add("minecraft:dried_kelp_block");
        ids.add("minecraft:kelp");
        ids.add("minecraft:sea_pickle");
        ids.add("minecraft:seagrass");
        ids.add("minecraft:lily_pad");
        ids.add("minecraft:big_dripleaf");
        ids.add("minecraft:small_dripleaf");
        ids.add("minecraft:frogspawn");
        ids.add("minecraft:turtle_egg");


        ids.add("minecraft:amethyst_block");
        ids.add("minecraft:budding_amethyst");
        ids.add("minecraft:amethyst_cluster");
        ids.add("minecraft:large_amethyst_bud");
        ids.add("minecraft:medium_amethyst_bud");
        ids.add("minecraft:small_amethyst_bud");
        ids.add("minecraft:cobweb");
        ids.add("minecraft:dripstone_block");
        ids.add("minecraft:pointed_dripstone");

        ids.add("minecraft:sculk");
        ids.add("minecraft:sculk_catalyst");
        ids.add("minecraft:sculk_sensor");
        ids.add("minecraft:calibrated_sculk_sensor");
        ids.add("minecraft:sculk_shrieker");
        ids.add("minecraft:sculk_vein");

        ids.add("minecraft:potent_sulfur");
        ids.add("minecraft:sulfur");
        ids.add("minecraft:sulfur_spike");
        

        addCoralPack(ids, "tube");
        addCoralPack(ids, "fire");
        addCoralPack(ids, "horn");
        addCoralPack(ids, "bubble");
        addCoralPack(ids, "brain");

        addDeadCoralPack(ids, "tube");
        addDeadCoralPack(ids, "fire");
        addDeadCoralPack(ids, "horn");
        addDeadCoralPack(ids, "bubble");
        addDeadCoralPack(ids, "brain");

        return ids;
    }

    private static void addCoralPack(List<String> ids, String name) {
        ids.add("minecraft:" + name + "_coral_block");
        ids.add("minecraft:" + name + "_coral");
        ids.add("minecraft:" + name + "_coral_fan");
    }

    private static void addDeadCoralPack(List<String> ids, String name) {
        ids.add("minecraft:dead_" + name + "_coral_block");
        ids.add("minecraft:dead_" + name + "_coral");
        ids.add("minecraft:dead_" + name + "_coral_fan");
    }
}