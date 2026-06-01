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

public class SpawnEggsTab implements TabSorter {

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

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(output -> {
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

        List<String> eggItems = List.of(
            "minecraft:cow_spawn_egg",
            "minecraft:sheep_spawn_egg",
            "minecraft:pig_spawn_egg",
            "minecraft:chicken_spawn_egg",
            "minecraft:cat_spawn_egg",
            "minecraft:wolf_spawn_egg",
            "minecraft:rabbit_spawn_egg",
            "minecraft:bee_spawn_egg",
            "minecraft:fox_spawn_egg",
            "minecraft:mooshroom_spawn_egg",
            "minecraft:horse_spawn_egg",
            "minecraft:donkey_spawn_egg",
            "minecraft:mule_spawn_egg",
            "minecraft:camel_spawn_egg",
            "minecraft:goat_spawn_egg",
            "minecraft:frog_spawn_egg",
            "minecraft:tadpole_spawn_egg",
            "minecraft:cod_spawn_egg",
            "minecraft:panda_spawn_egg",
            "minecraft:ocelot_spawn_egg",
            "minecraft:parrot_spawn_egg",
            "minecraft:snow_golem_spawn_egg",
            "minecraft:polar_bear_spawn_egg",
            "minecraft:axolotl_spawn_egg",
            "minecraft:salmon_spawn_egg",
            "minecraft:pufferfish_spawn_egg",
            "minecraft:tropical_fish_spawn_egg",
            "minecraft:sniffer_spawn_egg",
            "minecraft:llama_spawn_egg",
            "minecraft:trader_llama_spawn_egg",
            "minecraft:copper_golem_spawn_egg",
            "minecraft:armadillo_spawn_egg",
            "minecraft:turtle_spawn_egg",
            "minecraft:squid_spawn_egg",
            "minecraft:glow_squid_spawn_egg",
            "minecraft:dolphin_spawn_egg",
            "minecraft:allay_spawn_egg",
            "minecraft:villager_spawn_egg",
            "minecraft:wandering_trader_spawn_egg",
            "minecraft:iron_golem_spawn_egg",
            "minecraft:witch_spawn_egg",
            "minecraft:bat_spawn_egg",
            "minecraft:guardian_spawn_egg",
            "minecraft:elder_guardian_spawn_egg",
            "minecraft:nautilus_spawn_egg",
            "minecraft:vex_spawn_egg",
            "minecraft:vindicator_spawn_egg",
            "minecraft:pillager_spawn_egg",
            "minecraft:ravager_spawn_egg",
            "minecraft:evoker_spawn_egg",
            "minecraft:silverfish_spawn_egg",
            "minecraft:zombie_spawn_egg",
            "minecraft:zombie_villager_spawn_egg",
            "minecraft:zombie_nautilus_spawn_egg",
            "minecraft:cave_spider_spawn_egg",
            "minecraft:spider_spawn_egg",
            "minecraft:creeper_spawn_egg",
            "minecraft:breeze_spawn_egg",
            "minecraft:creaking_spawn_egg",
            "minecraft:slime_spawn_egg",
            "minecraft:zombie_horse_spawn_egg",
            "minecraft:husk_spawn_egg",
            "minecraft:drowned_spawn_egg",
            "minecraft:piglin_spawn_egg",
            "minecraft:piglin_brute_spawn_egg",
            "minecraft:hoglin_spawn_egg",
            "minecraft:blaze_spawn_egg",
            "minecraft:ghast_spawn_egg",
            "minecraft:magma_cube_spawn_egg",
            "minecraft:skeleton_horse_spawn_egg",
            "minecraft:camel_husk_spawn_egg",
            "minecraft:bogged_spawn_egg",
            "minecraft:strider_spawn_egg",
            "minecraft:zombified_piglin_spawn_egg",
            "minecraft:zoglin_spawn_egg",
            "minecraft:wither_skeleton_spawn_egg",
            "minecraft:happy_ghast_spawn_egg",
            "minecraft:phantom_spawn_egg",
            "minecraft:skeleton_spawn_egg",
            "minecraft:parched_spawn_egg",
            "minecraft:stray_spawn_egg",
            "minecraft:shulker_spawn_egg",
            "minecraft:endermite_spawn_egg",
            "minecraft:enderman_spawn_egg",
            "minecraft:ender_dragon_spawn_egg",
            "minecraft:wither_spawn_egg",
            "minecraft:warden_spawn_egg"
        );

        ids.addAll(eggItems);
        return ids;
    }
}