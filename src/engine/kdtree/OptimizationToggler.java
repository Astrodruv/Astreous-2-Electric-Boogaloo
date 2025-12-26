package engine.kdtree;

import engine.Settings;
import ui.display.DisplayManager;

public class OptimizationToggler {

    public static void toggle() {
        Settings.optimized = !Settings.optimized;

        if(Settings.optimized)
        {
            DisplayManager.addMessage("Activated KD Optimization");
        }
       else
        {
            DisplayManager.addMessage("Disabled KD Optimization");
        }
    }

    public static boolean isOptimized() {
        return Settings.optimized;
    }
}
