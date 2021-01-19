package com.github.sirblobman.combatlogx.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import com.github.sirblobman.api.utility.Validate;
import com.github.sirblobman.combatlogx.CombatPlugin;

public abstract class CombatListener implements Listener {
    private final CombatPlugin plugin;
    public CombatListener(CombatPlugin plugin) {
        this.plugin = Validate.notNull(plugin, "plugin must not be null!");
    }

    public CombatPlugin getPlugin() {
        return this.plugin;
    }

    public void register() {
        CombatPlugin plugin = getPlugin();
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(this, plugin);
    }
}
