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

public class CombatTab implements TabSorter {

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

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> {
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



            stacks.sort(byRank);
            output.getSearchTabStacks().sort(byRank);
        });
    }

    private static List<String> buildFullOrder() {
        List<String> ids = new ArrayList<>();

        // The 2-column utility block list to fill slots 8 and 9
        List<String> skippedUtilities = List.of(
            "minecraft:totem_of_undying", "minecraft:enchanted_golden_apple",
            "minecraft:golden_carrot", "minecraft:golden_apple",
            "minecraft:mace", "minecraft:trident",
            "minecraft:bow", "crossbow", // Vanilla bow/crossbow are just base identifiers
            "minecraft:shield", "minecraft:fishing_rod",
            "minecraft:ender_pearl", "minecraft:flint_and_steel",
            "minecraft:wind_charge", "minecraft:snowball"
        );

        List<String> tiers = List.of("wooden", "stone", "copper", "iron", "golden", "diamond", "netherite");

        int utilityIndex = 0;
        for (String tier : tiers) {
            String weaponPrefix = "minecraft:" + tier;
            String armorPrefix = "minecraft:" + tier;
            
            // Handle unique tier prefix mappings
            if (tier.equals("stone")) {
                armorPrefix = "minecraft:chainmail";
            } else if (tier.equals("wooden")) {
                armorPrefix = "minecraft:leather";
            }

            // Add weapons
            ids.add(weaponPrefix + "_sword");
            ids.add(weaponPrefix + "_axe");
            ids.add(weaponPrefix + "_spear");
            
            // Add armor pieces (corrected vanilla registry names for chest/legs)
            ids.add(armorPrefix + "_helmet");
            ids.add(armorPrefix + "_chestplate");
            ids.add(armorPrefix + "_leggings");
            ids.add(armorPrefix + "_boots");

            // Weave in the 2 skipped slots to hit column 8 and 9
            for (int i = 0; i < 2; i++) {
                if (utilityIndex < skippedUtilities.size()) {
                    String utilId = skippedUtilities.get(utilityIndex++);
                    ids.add(utilId.contains(":") ? utilId : "minecraft:" + utilId);
                } else {
                    ids.add("minecraft:air");
                }
            }
        }

        // Horse armor + extra utilities segment
        List<String> baseUtilities = List.of(
            "minecraft:leather_horse_armor", "minecraft:copper_horse_armor", "minecraft:iron_horse_armor",
            "minecraft:golden_horse_armor", "minecraft:diamond_horse_armor", "minecraft:netherite_horse_armor",
            "minecraft:turtle_helmet", "minecraft:fire_charge", "minecraft:lava_bucket", "minecraft:wolf_armor"
        );
        ids.addAll(baseUtilities);

        // Nautilus armors row sequence
        List<String> nautilusArmors = List.of(
            "minecraft:copper_nautilus_armor", "minecraft:iron_nautilus_armor", 
            "minecraft:golden_nautilus_armor", "minecraft:diamond_nautilus_armor", 
            "minecraft:netherite_nautilus_armor"
        );
        ids.addAll(nautilusArmors);

        // Heavy explosives sequence
        ids.add("minecraft:tnt");
        ids.add("minecraft:tnt_minecart");
        ids.add("minecraft:end_crystal");

        return ids;
    }
}