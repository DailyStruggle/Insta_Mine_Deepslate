package io.github.dailystruggle.insta_mine_deepslate;

import io.github.dailystruggle.insta_mine_deepslate.Listener.MiningDeepslate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Insta_Mine_Deepslate extends JavaPlugin {
    private static Insta_Mine_Deepslate instance;

    public static Insta_Mine_Deepslate getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new MiningDeepslate(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
