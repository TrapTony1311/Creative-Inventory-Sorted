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

        List<String> mainBlocks = List.of(
            "minecraft:crafting_table", "minecraft:furnace", "minecraft:chest", "minecraft:ender_chest",
            "minecraft:enchanting_table", "minecraft:lectern", "minecraft:chiseled_bookshelf", "minecraft:bookshelf",
            "minecraft:loom", "minecraft:cartography_table", "minecraft:fletching_table", "minecraft:barrel",
            "minecraft:note_block", "minecraft:jukebox", "minecraft:campfire", "minecraft:soul_campfire",
            "minecraft:decorated_pot", "minecraft:flower_pot", "minecraft:composter", "minecraft:bee_nest",
            "minecraft:beehive", "minecraft:scaffolding", "minecraft:ladder", "minecraft:torch",
            "minecraft:soul_torch", "minecraft:copper_torch", "minecraft:redstone_torch"
        );
        ids.addAll(mainBlocks);

        List<String> utilityLine = List.of(
            "minecraft:smithing_table", "minecraft:anvil", "minecraft:chipped_anvil", "minecraft:damaged_anvil",
            "minecraft:blast_furnace", "minecraft:smoker", "minecraft:lodestone", "minecraft:stonecutter", "minecraft:grindstone"
        );
        ids.addAll(utilityLine);

        List<String> dynamicBlocks = List.of(
            "minecraft:respawn_anchor", "minecraft:beacon", "minecraft:conduit", "minecraft:cauldron",
            "minecraft:brewing_stand", "minecraft:bell", "minecraft:armor_stand", "minecraft:item_frame", "minecraft:glow_item_frame"
        );
        ids.addAll(dynamicBlocks);

        addCopperMatrixRowInMiddle(ids, "chest", "minecraft:redstone_lamp");
        addCopperMatrixRowInMiddle(ids, "lightning_rod", "minecraft:end_rod");
        addCopperMatrixRowInMiddle(ids, "lantern", "minecraft:lantern");
        addCopperMatrixRowInMiddle(ids, "golem_statue", "minecraft:soul_lantern");

        List<String> newItems = List.of(
            "minecraft:player_head", "minecraft:zombie_head", "minecraft:creeper_head",
            "minecraft:skeleton_skull", "minecraft:wither_skeleton_skull", "minecraft:piglin_head",
            "minecraft:dragon_head", "minecraft:dragon_egg", "minecraft:end_crystal",
            "minecraft:infested_stone", "minecraft:infested_cobblestone", "minecraft:infested_stone_bricks",
            "minecraft:infested_mossy_stone_bricks", "minecraft:infested_cracked_stone_bricks", "minecraft:infested_chiseled_stone_bricks",
            "minecraft:infested_deepslate", "minecraft:suspicious_sand", "minecraft:suspicious_gravel", "minecraft:end_portal_frame", "minecraft:ender_eye",
            "minecraft:spawner", "minecraft:trial_spawner", "minecraft:vault", "minecraft:painting"
        );
        ids.addAll(newItems);

        return ids;
    }

    private static void addCopperMatrixRowInMiddle(List<String> ids, String copperType, String coreItem) {
        String base = copperType.equals("lightning_rod") ? "" : "copper_";
        String type = copperType.equals("lightning_rod") ? "lightning_rod" : copperType;

        ids.add("minecraft:" + base + type);
        ids.add("minecraft:exposed_" + base + type);
        ids.add("minecraft:weathered_" + base + type);
        ids.add("minecraft:oxidized_" + base + type);
        
        ids.add(coreItem);
        
        ids.add("minecraft:waxed_oxidized_" + base + type);
        ids.add("minecraft:waxed_weathered_" + base + type);
        ids.add("minecraft:waxed_exposed_" + base + type);
        ids.add("minecraft:waxed_" + base + type);
    }
}