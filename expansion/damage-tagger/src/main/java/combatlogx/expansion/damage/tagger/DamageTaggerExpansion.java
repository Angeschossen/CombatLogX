package combatlogx.expansion.damage.tagger;

import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.expansion.Expansion;
import com.github.sirblobman.combatlogx.api.expansion.ExpansionConfigurationManager;

import combatlogx.expansion.damage.tagger.listener.ListenerDamage;

public final class DamageTaggerExpansion extends Expansion {
    public DamageTaggerExpansion(ICombatLogX plugin) {
        super(plugin);
    }

    @Override
    public void onLoad() {
        ExpansionConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.saveDefault("config.yml");
    }

    @Override
    public void onEnable() {
        new ListenerDamage(this).register();
    }

    @Override
    public void onDisable() {
        // Do Nothing
    }

    @Override
    public void reloadConfig() {
        ExpansionConfigurationManager configurationManager = getConfigurationManager();
        configurationManager.reload("config.yml");
    }
}