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

public class FunctionalBlocksTab implements TabSorter {

    private enum Family { OVERWORLD, NETHER, BAMBOO }
    private record Wood(String type, Family family) {}

    private static final List<Wood> WOODS = List.of(
        new Wood("pale_oak", Family.OVERWORLD),
        new Wood("birch",    Family.OVERWORLD),
        new Wood("oak",      Family.OVERWORLD),
        new Wood("spruce",   Family.OVERWORLD),
        new Wood("dark_oak", Family.OVERWORLD),
        new Wood("acacia",   Family.OVERWORLD),
        new Wood("mangrove", Family.OVERWORLD),
        new Wood("crimson",  Family.NETHER),
        new Wood("cherry",   Family.OVERWORLD),
        new Wood("jungle",   Family.OVERWORLD),
        new Wood("bamboo",   Family.BAMBOO),
        new Wood("warped",   Family.NETHER)
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

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(output -> {
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
                
                // If it's a painting, skip removal logic entirely
                if (id.equals("minecraft:painting")) {
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

        List<String> col1And2 = List.of(
            "minecraft:crafting_table", "minecraft:furnace",
            "minecraft:chest", "minecraft:ender_chest",
            "minecraft:bookshelf", "minecraft:chiseled_bookshelf",
            "minecraft:enchanting_table", "minecraft:lectern",
            "minecraft:barrel", "minecraft:loom",
            "minecraft:cartography_table", "minecraft:fletching_table",
            "minecraft:jukebox", "minecraft:note_block",
            "minecraft:bee_nest", "minecraft:beehive",
            "minecraft:ladder", "minecraft:scaffolding",
            "minecraft:campfire", "minecraft:soul_campfire",
            "minecraft:torch", "minecraft:soul_torch",
            "minecraft:copper_torch", "minecraft:redstone_torch"
        );

        int woodBlockIndex = 0;
        for (Wood w : WOODS) {
            List<String> woodSet = getWoodSet(w);
            
            ids.add("minecraft:air");
            ids.add("minecraft:air");
            for (int i = 0; i < 7; i++) {
                ids.add(woodSet.get(i));
            }

            if (woodBlockIndex < col1And2.size()) {
                ids.add(col1And2.get(woodBlockIndex++));
                ids.add(col1And2.get(woodBlockIndex++));
            } else {
                ids.add("minecraft:air");
                ids.add("minecraft:air");
            }
            for (int i = 7; i < 14; i++) {
                ids.add(woodSet.get(i));
            }
        }

        while (woodBlockIndex < col1And2.size()) {
            ids.add(col1And2.get(woodBlockIndex++));
            ids.add(col1And2.get(woodBlockIndex++));
            for (int i = 0; i < 7; i++) {
                ids.add("minecraft:air");
            }
        }

        List<String> utilityLine = List.of(
            "minecraft:smithing_table", "minecraft:anvil", "minecraft:chipped_anvil", "minecraft:damaged_anvil",
            "minecraft:blast_furnace", "minecraft:smoker", "minecraft:lodestone", "minecraft:stonecutter", "minecraft:grindstone"
        );
        ids.addAll(utilityLine);

        List<String> utilityLine2 = List.of(
            "minecraft:composter", "minecraft:decorated_pot", "minecraft:flower_pot", "minecraft:bell",
            "minecraft:beacon", "minecraft:cauldron", "minecraft:brewing_stand", "minecraft:respawn_anchor", "minecraft:armor_stand"
        );
        ids.addAll(utilityLine2);

        List<String> col9 = List.of(
            "minecraft:conduit", "minecraft:iron_door", "minecraft:iron_bars", 
            "minecraft:iron_chain", "minecraft:end_rod", "minecraft:lantern", "minecraft:soul_lantern"
        );

        addCopperMatrixRow(ids, "chest", col9.get(0));
        addCopperMatrixRow(ids, "door", col9.get(1));
        addCopperMatrixRow(ids, "bars", col9.get(2));
        addCopperMatrixRow(ids, "iron_chain", col9.get(3));
        addCopperMatrixRow(ids, "lightning_rod", col9.get(4));
        addCopperMatrixRow(ids, "lantern", col9.get(5));
        addCopperMatrixRow(ids, "golem_statue", col9.get(6));

        List<String> newItems = List.of(
            "minecraft:player_head", "minecraft:zombie_head", "minecraft:creeper_head",
            "minecraft:skeleton_skull", "minecraft:wither_skeleton_skull", "minecraft:piglin_head",
            "minecraft:dragon_head", "minecraft:dragon_egg", "minecraft:end_crystal",
            "minecraft:infested_stone", "minecraft:infested_cobblestone", "minecraft:infested_stone_bricks",
            "minecraft:infested_mossy_stone_bricks", "minecraft:infested_cracked_stone_bricks", "minecraft:infested_chiseled_stone_bricks",
            "minecraft:infested_deepslate", "minecraft:end_portal_frame", "minecraft:ender_eye",
            "minecraft:spawner", "minecraft:trial_spawner", "minecraft:vault", "minecraft:glow_item_frame", "minecraft:item_frame",
            "minecraft:painting"
        );
        ids.addAll(newItems);

        return ids;
    }

    private static void addCopperMatrixRow(List<String> ids, String type, String col9Item) {
        if (type.equals("lightning_rod")) {
            ids.add("minecraft:lightning_rod");
            ids.add("minecraft:exposed_lightning_rod");
            ids.add("minecraft:weathered_lightning_rod");
            ids.add("minecraft:oxidized_lightning_rod");
            ids.add("minecraft:waxed_oxidized_lightning_rod");
            ids.add("minecraft:waxed_weathered_lightning_rod");
            ids.add("minecraft:waxed_exposed_lightning_rod");
            ids.add("minecraft:waxed_lightning_rod");
        } else if (type.equals("bars") || type.equals("iron_chain") || type.equals("golem_statue")) {
            String cleanType = type.equals("iron_chain") ? "chain" : type;
            ids.add("minecraft:copper_" + cleanType);
            ids.add("minecraft:exposed_copper_" + cleanType);
            ids.add("minecraft:weathered_copper_" + cleanType);
            ids.add("minecraft:oxidized_copper_" + cleanType);
            ids.add("minecraft:waxed_oxidized_copper_" + cleanType);
            ids.add("minecraft:waxed_weathered_copper_" + cleanType);
            ids.add("minecraft:waxed_exposed_copper_" + cleanType);
            ids.add("minecraft:waxed_copper_" + cleanType);
        } else {
            ids.add("minecraft:copper_" + type);
            ids.add("minecraft:exposed_copper_" + type);
            ids.add("minecraft:weathered_copper_" + type);
            ids.add("minecraft:oxidized_copper_" + type);
            ids.add("minecraft:waxed_oxidized_copper_" + type);
            ids.add("minecraft:waxed_weathered_copper_" + type);
            ids.add("minecraft:waxed_exposed_copper_" + type);
            ids.add("minecraft:waxed_copper_" + type);
        }
        ids.add("minecraft:air");
        ids.add(col9Item);
    }

    private static List<String> getWoodSet(Wood w) {
        List<String> woodItems = new ArrayList<>();
        String t = w.type();

        if (w.family() == Family.BAMBOO) {
            woodItems.add("minecraft:bamboo_door");
            woodItems.add("minecraft:bamboo_fence");
            woodItems.add("minecraft:bamboo_fence_gate");
            woodItems.add("minecraft:bamboo_sign");
            woodItems.add("minecraft:bamboo_hanging_sign");
            woodItems.add("minecraft:bamboo_pressure_plate");
            woodItems.add("minecraft:bamboo_button");
        } else {
            woodItems.add("minecraft:" + t + "_door");
            woodItems.add("minecraft:" + t + "_fence");
            woodItems.add("minecraft:" + t + "_fence_gate");
            woodItems.add("minecraft:" + t + "_sign");
            woodItems.add("minecraft:" + t + "_hanging_sign");
            woodItems.add("minecraft:" + t + "_pressure_plate");
            woodItems.add("minecraft:" + t + "_button");
        }

        woodItems.addAll(woodItems);
        return woodItems;
    }
}