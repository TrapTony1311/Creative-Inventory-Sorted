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

public class ColoredBlocksTab implements TabSorter {

    private static final List<String> COLORS = List.of(
        "white", "yellow", "orange", "red", "pink", "magenta", "purple", "blue",
        "light_gray", "gray", "black", "brown", "green", "lime", "light_blue", "cyan"
    );

    private static final List<String> BANNERS_ROW_2 = List.of(
        "cyan", "light_blue", "lime", "green", "brown", "black", "gray", "light_gray"
    );

    private static final List<String> TYPES = List.of(
        "wool", "carpet", "terracotta", "glazed_terracotta", 
        "concrete_powder", "concrete", "stained_glass", 
        "stained_glass_pane", "candle", "bed", "shulker_box"
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

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COLORED_BLOCKS).register(output -> {
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

        List<String> column9 = new ArrayList<>();
        column9.add("minecraft:loom");
        for (int i = 0; i < 8; i++) {
            column9.add("minecraft:" + COLORS.get(i) + "_banner");
        }
        for (int i = 0; i < 8; i++) {
            column9.add("minecraft:" + BANNERS_ROW_2.get(i) + "_banner");
        }
        column9.add("minecraft:candle");
        column9.add("minecraft:glass");
        column9.add("minecraft:tinted_glass");
        column9.add("minecraft:glass_pane");
        column9.add("minecraft:shulker_box");

        int col9Index = 0;

        for (String type : TYPES) {
            for (int i = 0; i < 8; i++) {
                ids.add(getMatId(type, COLORS.get(i)));
            }
            if (col9Index < column9.size()) {
                ids.add(column9.get(col9Index++));
            }

            for (int i = 8; i < 16; i++) {
                ids.add(getMatId(type, COLORS.get(i)));
            }
            if (col9Index < column9.size()) {
                ids.add(column9.get(col9Index++));
            }
        }

        while (col9Index < column9.size()) {
            for (int i = 0; i < 8; i++) {
                ids.add("minecraft:air");
            }
            ids.add(column9.get(col9Index++));
        }

        return ids;
    }

    private static String getMatId(String type, String color) {
        return switch (type) {
            case "wool", "carpet", "concrete", "concrete_powder", "bed", "shulker_box" -> 
                "minecraft:" + color + "_" + type;
            case "terracotta" -> 
                "minecraft:" + color + "_terracotta";
            case "glazed_terracotta" -> 
                "minecraft:" + color + "_glazed_terracotta";
            case "stained_glass" -> 
                "minecraft:" + color + "_stained_glass";
            case "stained_glass_pane" -> 
                "minecraft:" + color + "_stained_glass_pane";
            case "candle" -> 
                "minecraft:" + color + "_candle";
            default -> "minecraft:air";
        };
    }
}