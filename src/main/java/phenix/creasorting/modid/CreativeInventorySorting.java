
package phenix.creasorting.modid;

import java.util.List;

import net.fabricmc.api.ModInitializer;
import phenix.creasorting.modid.tabs.BuildingBlocksTab;
import phenix.creasorting.modid.tabs.ColoredBlocksTab;
import phenix.creasorting.modid.tabs.CombatTab;
import phenix.creasorting.modid.tabs.FoodAndDrinksTab;
import phenix.creasorting.modid.tabs.FunctionalBlocksTab;
import phenix.creasorting.modid.tabs.IngredientsTab;
import phenix.creasorting.modid.tabs.NaturalBlocksTab;
import phenix.creasorting.modid.tabs.RedstoneBlocksTab;
import phenix.creasorting.modid.tabs.SpawnEggsTab;
import phenix.creasorting.modid.tabs.TabSorter;
import phenix.creasorting.modid.tabs.ToolsAndUtilitiesTab;

public class CreativeInventorySorting implements ModInitializer {
    public static final String MOD_ID = "creative-inventory-sorting";

    private static final List<TabSorter> TABS = List.of(
        new BuildingBlocksTab(),
        new ColoredBlocksTab(),
        new NaturalBlocksTab(),
		new FunctionalBlocksTab(),
		new RedstoneBlocksTab(),
		new ToolsAndUtilitiesTab(),
		new CombatTab(),
		new FoodAndDrinksTab(),
		new IngredientsTab(),
		new SpawnEggsTab()
    );

    @Override
    public void onInitialize() {
        for (TabSorter tab : TABS) {
            tab.register();
        }
    }
}