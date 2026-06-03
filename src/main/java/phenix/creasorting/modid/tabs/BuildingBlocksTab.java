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

public class BuildingBlocksTab implements TabSorter {

    private enum Family { OVERWORLD, NETHER, BAMBOO }
    private record Wood(String type, Family family) {}

    private static final List<Wood> WOODS = List.of(
        new Wood("dark_oak", Family.OVERWORLD),
        new Wood("spruce",   Family.OVERWORLD),
        new Wood("oak",      Family.OVERWORLD),
        new Wood("bamboo",   Family.BAMBOO),
        new Wood("birch",    Family.OVERWORLD),
        new Wood("pale_oak", Family.OVERWORLD),
        new Wood("cherry",   Family.OVERWORLD),
        new Wood("jungle",   Family.OVERWORLD),
        new Wood("acacia",   Family.OVERWORLD),
        new Wood("mangrove", Family.OVERWORLD),
        new Wood("crimson",  Family.NETHER),
        new Wood("warped",   Family.NETHER)
    );

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
        "minecraft:terracotta",
        "minecraft:dripstone_block",
        "minecraft:pointed_dripstone",
        "minecraft:moss_block",
        "minecraft:pale_moss_block",

        "minecraft:clay",
        "minecraft:calcite",
        "minecraft:snow_block",
        "minecraft:ice",
        "minecraft:packed_ice",
        "minecraft:blue_ice",
        "minecraft:tinted_glass",
        "minecraft:glass",
        "minecraft:glass_pane"
    );

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

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(output -> {
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

    private static List<String> buildFullOrder() {
        List<String> ids = new ArrayList<>();
        ids.addAll(NATURAL_BLOCKS);
        for (Wood w : WOODS) addCore(ids, w);
        
        addStoneMatrix(ids);
        addDeepslateAndTuffMatrix(ids);
        addNetherAndEndMatrix(ids);
        addQuartzAndSandstoneMatrix(ids);
        addOresAndSpecialMatrix(ids);
        addResourceBlocksMatrix(ids);
        addCopperAndUtilityMatrix(ids);
        return ids;
    }

    private static void addStoneMatrix(List<String> ids) {
        ids.add("minecraft:granite");
        ids.add("minecraft:granite_stairs");
        ids.add("minecraft:granite_slab");
        ids.add("minecraft:granite_wall");
        ids.add("minecraft:polished_granite");
        ids.add("minecraft:polished_granite_stairs");
        ids.add("minecraft:polished_granite_slab");
        ids.add("minecraft:mossy_stone_bricks");
        ids.add("minecraft:mossy_cobblestone");

        ids.add("minecraft:diorite");
        ids.add("minecraft:diorite_stairs");
        ids.add("minecraft:diorite_slab");
        ids.add("minecraft:diorite_wall");
        ids.add("minecraft:polished_diorite");
        ids.add("minecraft:polished_diorite_stairs");
        ids.add("minecraft:polished_diorite_slab");
        ids.add("minecraft:mossy_stone_brick_stairs");
        ids.add("minecraft:mossy_cobblestone_stairs");

        ids.add("minecraft:andesite");
        ids.add("minecraft:andesite_stairs");
        ids.add("minecraft:andesite_slab");
        ids.add("minecraft:andesite_wall");
        ids.add("minecraft:polished_andesite");
        ids.add("minecraft:polished_andesite_stairs");
        ids.add("minecraft:polished_andesite_slab");
        ids.add("minecraft:mossy_stone_brick_slab");
        ids.add("minecraft:mossy_cobblestone_slab");

        ids.add("minecraft:cobblestone");
        ids.add("minecraft:cobblestone_stairs");
        ids.add("minecraft:cobblestone_slab");
        ids.add("minecraft:cobblestone_wall");
        ids.add("minecraft:stone");
        ids.add("minecraft:stone_stairs");
        ids.add("minecraft:stone_slab");
        ids.add("minecraft:mossy_stone_brick_wall");
        ids.add("minecraft:mossy_cobblestone_wall");
    }

    private static void addDeepslateAndTuffMatrix(List<String> ids) {
        ids.add("minecraft:stone_bricks");
        ids.add("minecraft:stone_brick_stairs");
        ids.add("minecraft:stone_brick_slab");
        ids.add("minecraft:stone_brick_wall");
        ids.add("minecraft:chiseled_stone_bricks");
        ids.add("minecraft:cracked_stone_bricks");
        ids.add("minecraft:gravel");
        ids.add("minecraft:smooth_stone");
        ids.add("minecraft:smooth_stone_slab");

        ids.add("minecraft:tuff_bricks");
        ids.add("minecraft:tuff_brick_stairs");
        ids.add("minecraft:tuff_brick_slab");
        ids.add("minecraft:tuff_brick_wall");
        ids.add("minecraft:chiseled_tuff_bricks");
        ids.add("minecraft:polished_tuff");
        ids.add("minecraft:polished_tuff_stairs");
        ids.add("minecraft:polished_tuff_slab");
        ids.add("minecraft:polished_tuff_wall");

        ids.add("minecraft:tuff");
        ids.add("minecraft:tuff_stairs");
        ids.add("minecraft:tuff_slab");
        ids.add("minecraft:tuff_wall");
        ids.add("minecraft:chiseled_tuff");
        ids.add("minecraft:cobbled_deepslate");
        ids.add("minecraft:cobbled_deepslate_stairs");
        ids.add("minecraft:cobbled_deepslate_slab");
        ids.add("minecraft:cobbled_deepslate_wall");

        ids.add("minecraft:polished_deepslate");
        ids.add("minecraft:polished_deepslate_stairs");
        ids.add("minecraft:polished_deepslate_slab");
        ids.add("minecraft:polished_deepslate_wall");
        ids.add("minecraft:cracked_deepslate_bricks");
        ids.add("minecraft:deepslate_bricks");
        ids.add("minecraft:deepslate_brick_stairs");
        ids.add("minecraft:deepslate_brick_slab");
        ids.add("minecraft:deepslate_brick_wall");

        ids.add("minecraft:deepslate_tiles");
        ids.add("minecraft:deepslate_tile_stairs");
        ids.add("minecraft:deepslate_tile_slab");
        ids.add("minecraft:deepslate_tile_wall");
        ids.add("minecraft:cracked_deepslate_tiles");
        ids.add("minecraft:chiseled_deepslate");
        ids.add("minecraft:deepslate");
        ids.add("minecraft:reinforced_deepslate");
        ids.add("minecraft:smooth_basalt");
    }

    private static void addNetherAndEndMatrix(List<String> ids) {
        ids.add("minecraft:blackstone");
        ids.add("minecraft:blackstone_stairs");
        ids.add("minecraft:blackstone_slab");
        ids.add("minecraft:blackstone_wall");
        ids.add("minecraft:chiseled_polished_blackstone");
        ids.add("minecraft:gilded_blackstone");
        ids.add("minecraft:bedrock");
        ids.add("minecraft:basalt");
        ids.add("minecraft:polished_basalt");

        ids.add("minecraft:polished_blackstone");
        ids.add("minecraft:polished_blackstone_stairs");
        ids.add("minecraft:polished_blackstone_slab");
        ids.add("minecraft:polished_blackstone_wall");
        ids.add("minecraft:cracked_polished_blackstone_bricks");
        ids.add("minecraft:polished_blackstone_bricks");
        ids.add("minecraft:polished_blackstone_brick_stairs");
        ids.add("minecraft:polished_blackstone_brick_slab");
        ids.add("minecraft:polished_blackstone_brick_wall");

        ids.add("minecraft:nether_bricks");
        ids.add("minecraft:nether_brick_stairs");
        ids.add("minecraft:nether_brick_slab");
        ids.add("minecraft:nether_brick_wall");
        ids.add("minecraft:cracked_nether_bricks");
        ids.add("minecraft:chiseled_nether_bricks");
        ids.add("minecraft:nether_brick_fence");
        ids.add("minecraft:obsidian");
        ids.add("minecraft:crying_obsidian");
        
        ids.add("minecraft:red_nether_bricks");
        ids.add("minecraft:red_nether_brick_stairs");
        ids.add("minecraft:red_nether_brick_slab");
        ids.add("minecraft:red_nether_brick_wall");
        ids.add("minecraft:soul_sand");
        ids.add("minecraft:soul_soil");
        ids.add("minecraft:netherrack");
        ids.add("minecraft:magma_block");
        ids.add("minecraft:shroomlight");

        ids.add("minecraft:bricks");
        ids.add("minecraft:brick_stairs");
        ids.add("minecraft:brick_slab");
        ids.add("minecraft:brick_wall");
        ids.add("minecraft:packed_mud");
        ids.add("minecraft:mud_bricks");
        ids.add("minecraft:mud_brick_stairs");
        ids.add("minecraft:mud_brick_slab");
        ids.add("minecraft:mud_brick_wall");

        ids.add("minecraft:resin_bricks");
        ids.add("minecraft:resin_brick_stairs");
        ids.add("minecraft:resin_brick_slab");
        ids.add("minecraft:resin_brick_wall");
        ids.add("minecraft:chiseled_resin_bricks");
        ids.add("minecraft:resin_block");
        ids.add("minecraft:dark_prismarine");
        ids.add("minecraft:dark_prismarine_stairs");
        ids.add("minecraft:dark_prismarine_slab");

        ids.add("minecraft:prismarine");
        ids.add("minecraft:prismarine_stairs");
        ids.add("minecraft:prismarine_slab");
        ids.add("minecraft:prismarine_wall");
        ids.add("minecraft:sea_lantern");
        ids.add("minecraft:prismarine_bricks");
        ids.add("minecraft:prismarine_brick_stairs");
        ids.add("minecraft:prismarine_brick_slab");
        ids.add("minecraft:dried_kelp_block");
    }

    private static void addQuartzAndSandstoneMatrix(List<String> ids) {
        ids.add("minecraft:quartz_block");
        ids.add("minecraft:quartz_stairs");
        ids.add("minecraft:quartz_slab");
        ids.add("minecraft:chiseled_quartz_block");
        ids.add("minecraft:quartz_bricks");
        ids.add("minecraft:smooth_quartz");
        ids.add("minecraft:smooth_quartz_stairs");
        ids.add("minecraft:smooth_quartz_slab");
        ids.add("minecraft:quartz_pillar");

        ids.add("minecraft:sandstone");
        ids.add("minecraft:smooth_sandstone");
        ids.add("minecraft:sand");
        ids.add("minecraft:cut_sandstone");
        ids.add("minecraft:bone_block");
        ids.add("minecraft:cut_red_sandstone");
        ids.add("minecraft:red_sand");
        ids.add("minecraft:smooth_red_sandstone");
        ids.add("minecraft:red_sandstone");
        ids.add("minecraft:sandstone_stairs");
        ids.add("minecraft:smooth_sandstone_stairs");
        ids.add("minecraft:chiseled_sandstone");
        ids.add("minecraft:sandstone_wall");
        ids.add("minecraft:glowstone");
        ids.add("minecraft:red_sandstone_wall");
        ids.add("minecraft:chiseled_red_sandstone");
        ids.add("minecraft:smooth_red_sandstone_stairs");
        ids.add("minecraft:red_sandstone_stairs");
        ids.add("minecraft:sandstone_slab");
        ids.add("minecraft:smooth_sandstone_slab");
        ids.add("minecraft:cut_sandstone_slab");
        ids.add("minecraft:verdant_froglight");
        ids.add("minecraft:ochre_froglight");
        ids.add("minecraft:pearlescent_froglight");
        ids.add("minecraft:cut_red_sandstone_slab");
        ids.add("minecraft:smooth_red_sandstone_slab");
        ids.add("minecraft:red_sandstone_slab");

        ids.add("minecraft:end_stone_bricks");
        ids.add("minecraft:end_stone_brick_stairs");
        ids.add("minecraft:end_stone_brick_slab");
        ids.add("minecraft:end_stone_brick_wall");
        ids.add("minecraft:end_stone");
        ids.add("minecraft:purpur_block");
        ids.add("minecraft:purpur_stairs");
        ids.add("minecraft:purpur_slab");
        ids.add("minecraft:purpur_pillar");
    }

    private static void addOresAndSpecialMatrix(List<String> ids) {
        ids.add("minecraft:coal_ore");
        ids.add("minecraft:iron_ore");
        ids.add("minecraft:copper_ore");
        ids.add("minecraft:gold_ore");
        ids.add("minecraft:redstone_ore");
        ids.add("minecraft:emerald_ore");
        ids.add("minecraft:lapis_ore");
        ids.add("minecraft:diamond_ore");
        ids.add("minecraft:nether_quartz_ore");

        ids.add("minecraft:deepslate_coal_ore");
        ids.add("minecraft:deepslate_iron_ore");
        ids.add("minecraft:deepslate_copper_ore");
        ids.add("minecraft:deepslate_gold_ore");
        ids.add("minecraft:deepslate_redstone_ore");
        ids.add("minecraft:deepslate_emerald_ore");
        ids.add("minecraft:deepslate_lapis_ore");
        ids.add("minecraft:deepslate_diamond_ore");
        ids.add("minecraft:nether_gold_ore");
    }

    private static void addResourceBlocksMatrix(List<String> ids) {
        ids.add("minecraft:iron_block");
        ids.add("minecraft:gold_block");
        ids.add("minecraft:diamond_block");
        ids.add("minecraft:netherite_block");
        ids.add("minecraft:coal_block");
        ids.add("minecraft:redstone_block");
        ids.add("minecraft:lapis_block");
        ids.add("minecraft:emerald_block");
        ids.add("minecraft:amethyst_block");

        ids.add("minecraft:raw_iron_block");
        ids.add("minecraft:raw_copper_block");
        ids.add("minecraft:raw_gold_block");
        ids.add("minecraft:ancient_debris");
        ids.add("minecraft:suspicious_gravel");
        ids.add("minecraft:suspicious_sand");
        ids.add("minecraft:sculk");
        ids.add("minecraft:sculk_catalyst");
        ids.add("minecraft:budding_amethyst");
    }

    private static void addCopperAndUtilityMatrix(List<String> ids) {
        ids.add("minecraft:copper_block");
        ids.add("minecraft:exposed_copper");
        ids.add("minecraft:weathered_copper");
        ids.add("minecraft:oxidized_copper");
        ids.add("minecraft:vault");
        ids.add("minecraft:waxed_oxidized_copper");
        ids.add("minecraft:waxed_weathered_copper");
        ids.add("minecraft:waxed_exposed_copper");
        ids.add("minecraft:waxed_copper_block");
        

        ids.add("minecraft:cut_copper");
        ids.add("minecraft:exposed_cut_copper");
        ids.add("minecraft:weathered_cut_copper");
        ids.add("minecraft:oxidized_cut_copper");
        ids.add("minecraft:trial_spawner");
        ids.add("minecraft:waxed_oxidized_cut_copper");
        ids.add("minecraft:waxed_weathered_cut_copper");
        ids.add("minecraft:waxed_exposed_cut_copper");
        ids.add("minecraft:waxed_cut_copper");
        

        ids.add("minecraft:cut_copper_stairs");
        ids.add("minecraft:exposed_cut_copper_stairs");
        ids.add("minecraft:weathered_cut_copper_stairs");
        ids.add("minecraft:oxidized_cut_copper_stairs");
        ids.add("minecraft:spawner");
        ids.add("minecraft:waxed_oxidized_cut_copper_stairs");
        ids.add("minecraft:waxed_weathered_cut_copper_stairs");
        ids.add("minecraft:waxed_exposed_cut_copper_stairs");
        ids.add("minecraft:waxed_cut_copper_stairs");
        

        ids.add("minecraft:cut_copper_slab");
        ids.add("minecraft:exposed_cut_copper_slab");
        ids.add("minecraft:weathered_cut_copper_slab");
        ids.add("minecraft:oxidized_cut_copper_slab");
        ids.add("minecraft:creaking_heart");
        ids.add("minecraft:waxed_oxidized_cut_copper_slab");
        ids.add("minecraft:waxed_weathered_cut_copper_slab");
        ids.add("minecraft:waxed_exposed_cut_copper_slab");
        ids.add("minecraft:waxed_cut_copper_slab");
        
        
        ids.add("minecraft:copper_trapdoor");
        ids.add("minecraft:exposed_copper_trapdoor");
        ids.add("minecraft:weathered_copper_trapdoor");
        ids.add("minecraft:oxidized_copper_trapdoor");
        ids.add("minecraft:iron_trapdoor");
        ids.add("minecraft:waxed_oxidized_copper_trapdoor");
        ids.add("minecraft:waxed_weathered_copper_trapdoor");
        ids.add("minecraft:waxed_exposed_copper_trapdoor");
        ids.add("minecraft:waxed_copper_trapdoor");
        

        ids.add("minecraft:chiseled_copper");
        ids.add("minecraft:exposed_chiseled_copper");
        ids.add("minecraft:weathered_chiseled_copper");
        ids.add("minecraft:oxidized_chiseled_copper");
        ids.add("minecraft:lodestone");
        ids.add("minecraft:waxed_oxidized_chiseled_copper");
        ids.add("minecraft:waxed_weathered_chiseled_copper");
        ids.add("minecraft:waxed_exposed_chiseled_copper");
        ids.add("minecraft:waxed_chiseled_copper");
        

        ids.add("minecraft:copper_grate");
        ids.add("minecraft:exposed_copper_grate");
        ids.add("minecraft:weathered_copper_grate");
        ids.add("minecraft:oxidized_copper_grate");
        ids.add("minecraft:scaffolding");
        ids.add("minecraft:waxed_oxidized_copper_grate");
        ids.add("minecraft:waxed_weathered_copper_grate");
        ids.add("minecraft:waxed_exposed_copper_grate");
        ids.add("minecraft:waxed_copper_grate");
        

        ids.add("minecraft:copper_bulb");
        ids.add("minecraft:exposed_copper_bulb");
        ids.add("minecraft:weathered_copper_bulb");
        ids.add("minecraft:oxidized_copper_bulb");
        ids.add("minecraft:redstone_lamp");
        ids.add("minecraft:waxed_oxidized_copper_bulb");
        ids.add("minecraft:waxed_weathered_copper_bulb");
        ids.add("minecraft:waxed_exposed_copper_bulb");
        ids.add("minecraft:waxed_copper_bulb");
        

        ids.add("minecraft:copper_door");
        ids.add("minecraft:exposed_copper_door");
        ids.add("minecraft:weathered_copper_door");
        ids.add("minecraft:oxidized_copper_door");
        ids.add("minecraft:iron_door");
        ids.add("minecraft:waxed_oxidized_copper_door");
        ids.add("minecraft:waxed_weathered_copper_door");
        ids.add("minecraft:waxed_exposed_copper_door");
        ids.add("minecraft:waxed_copper_door");

        ids.add("minecraft:copper_bars");
        ids.add("minecraft:exposed_copper_bars");
        ids.add("minecraft:weathered_copper_bars");
        ids.add("minecraft:oxidized_copper_bars");
        ids.add("minecraft:iron_bars");
        ids.add("minecraft:waxed_oxidized_copper_bars");
        ids.add("minecraft:waxed_weathered_copper_bars");
        ids.add("minecraft:waxed_exposed_copper_bars");
        ids.add("minecraft:waxed_copper_bars");

        ids.add("minecraft:copper_chain");
        ids.add("minecraft:exposed_copper_chain");
        ids.add("minecraft:weathered_copper_chain");
        ids.add("minecraft:oxidized_copper_chain");
        ids.add("minecraft:iron_chain");
        ids.add("minecraft:waxed_oxidized_copper_chain");
        ids.add("minecraft:waxed_weathered_copper_chain");
        ids.add("minecraft:waxed_exposed_copper_chain");
        ids.add("minecraft:waxed_copper_chain");
        
    }

    private static void addCore(List<String> ids, Wood w) {
        String t = w.type();
        switch (w.family()) {
            case OVERWORLD -> {
                ids.add("minecraft:" + t + "_log");
                ids.add("minecraft:" + t + "_wood");
                ids.add("minecraft:stripped_" + t + "_log");
                ids.add("minecraft:stripped_" + t + "_wood");
                ids.add("minecraft:" + t + "_planks");
                ids.add("minecraft:" + t + "_stairs");
                ids.add("minecraft:" + t + "_slab");
                ids.add("minecraft:" + t + "_fence");
                ids.add("minecraft:" + t + "_fence_gate");

                ids.add("minecraft:" + t + "_leaves");
                if (t.equals("mangrove")) {
                    ids.add("minecraft:mangrove_propagule");
                } else {
                    ids.add("minecraft:" + t + "_sapling");
                }

                ids.add("minecraft:" + t + "_door");
                ids.add("minecraft:" + t + "_shelf");
                ids.add("minecraft:" + t + "_trapdoor");
                ids.add("minecraft:" + t + "_sign");
                ids.add("minecraft:" + t + "_hanging_sign");
                ids.add("minecraft:" + t + "_pressure_plate");
                ids.add("minecraft:" + t + "_button");
            }
            case NETHER -> {
                ids.add("minecraft:" + t + "_stem");
                ids.add("minecraft:" + t + "_hyphae");
                ids.add("minecraft:stripped_" + t + "_stem");
                ids.add("minecraft:stripped_" + t + "_hyphae");
                ids.add("minecraft:" + t + "_planks");
                ids.add("minecraft:" + t + "_stairs");
                ids.add("minecraft:" + t + "_slab");
                ids.add("minecraft:" + t + "_fence");
                ids.add("minecraft:" + t + "_fence_gate");

                if (t.equals("crimson")) {
                    ids.add("minecraft:nether_wart_block");
                } else if (t.equals("warped")) {
                    ids.add("minecraft:warped_wart_block");
                }
                ids.add("minecraft:" + t + "_fungus");
                
                ids.add("minecraft:" + t + "_door");
                ids.add("minecraft:" + t + "_shelf");
                ids.add("minecraft:" + t + "_trapdoor");
                ids.add("minecraft:" + t + "_sign");
                ids.add("minecraft:" + t + "_hanging_sign");
                ids.add("minecraft:" + t + "_pressure_plate");
                ids.add("minecraft:" + t + "_button");
            }
            case BAMBOO -> {
                ids.add("minecraft:bamboo_block");
                ids.add("minecraft:stripped_bamboo_block");
                ids.add("minecraft:bamboo_planks");
                ids.add("minecraft:bamboo_stairs");
                ids.add("minecraft:bamboo_slab");
                ids.add("minecraft:bamboo_mosaic");
                ids.add("minecraft:bamboo_mosaic_stairs");
                ids.add("minecraft:bamboo_mosaic_slab");
                ids.add("minecraft:bamboo_fence");
                ids.add("minecraft:bamboo_fence_gate");

                ids.add("minecraft:bamboo_block");
                ids.add("minecraft:bamboo");
                
                ids.add("minecraft:bamboo_door");
                ids.add("minecraft:bamboo_shelf");
                ids.add("minecraft:bamboo_trapdoor");
                ids.add("minecraft:bamboo_sign");
                ids.add("minecraft:bamboo_hanging_sign");
                ids.add("minecraft:bamboo_pressure_plate");
                ids.add("minecraft:bamboo_button");
            }
        }
    }
}