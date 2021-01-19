package com.github.sirblobman.combatlogx.api.utility;

import java.text.DecimalFormat;
import java.util.Arrays;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.github.sirblobman.api.language.LanguageManager;
import com.github.sirblobman.api.nms.EntityHandler;
import com.github.sirblobman.api.nms.MultiVersionHandler;
import com.github.sirblobman.api.utility.MessageUtility;
import com.github.sirblobman.combatlogx.api.ICombatLogX;
import com.github.sirblobman.combatlogx.api.ICombatManager;

public final class PlaceholderHelper {
    public static String getTimeLeft(ICombatLogX plugin, Player player) {
        ICombatManager combatManager = plugin.getCombatManager();
        int secondsLeft = combatManager.getTimerLeftSeconds(player);
        if(secondsLeft > 0) return Integer.toString(secondsLeft);

        LanguageManager languageManager = plugin.getLanguageManager();
        return languageManager.getMessageColored(player, "placeholder.time-left-zero");
    }

    public static String getTimeLeftDecimal(ICombatLogX plugin, Player player) {
        LanguageManager languageManager = plugin.getLanguageManager();
        ICombatManager combatManager = plugin.getCombatManager();
        double millisLeft = combatManager.getTimerLeftMillis(player);
        if(millisLeft <= 0.0D) return languageManager.getMessageColored(player, "placeholder.time-left-zero");

        double secondsLeft = (millisLeft / 1_000.0D);
        DecimalFormat decimalFormat = getDecimalFormat(plugin, player);
        return decimalFormat.format(secondsLeft);
    }

    public static String getInCombat(ICombatLogX plugin, Player player) {
        ICombatManager combatManager = plugin.getCombatManager();
        LanguageManager languageManager = plugin.getLanguageManager();
        boolean inCombat = combatManager.isInCombat(player);
        return languageManager.getMessageColored(player, ("placeholder.status." + (inCombat ? "in-combat" : "not-in-combat")));
    }

    public static String getStatus(ICombatLogX plugin, Player player) {
        ICombatManager combatManager = plugin.getCombatManager();
        LanguageManager languageManager = plugin.getLanguageManager();
        boolean inCombat = combatManager.isInCombat(player);
        return languageManager.getMessageColored(player, ("placeholder.status." + (inCombat ? "fighting" : "idle")));
    }

    public static String getEnemyName(ICombatLogX plugin, Player player) {
        ICombatManager combatManager = plugin.getCombatManager();
        LivingEntity enemy = combatManager.getEnemy(player);
        if(enemy == null) return getUnknownEnemy(plugin, player);

        MultiVersionHandler multiVersionHandler = plugin.getMultiVersionHandler();
        EntityHandler entityHandler = multiVersionHandler.getEntityHandler();
        return entityHandler.getName(enemy);
    }

    public static String getEnemyHealth(ICombatLogX plugin, Player player) {
        ICombatManager combatManager = plugin.getCombatManager();
        LivingEntity enemy = combatManager.getEnemy(player);
        if(enemy == null) return getUnknownEnemy(plugin, player);

        double enemyHealth = enemy.getHealth();
        DecimalFormat decimalFormat = getDecimalFormat(plugin, player);
        return decimalFormat.format(enemyHealth);
    }

    public static String getEnemyHealthRounded(ICombatLogX plugin, Player player) {
        ICombatManager combatManager = plugin.getCombatManager();
        LivingEntity enemy = combatManager.getEnemy(player);
        if(enemy == null) return getUnknownEnemy(plugin, player);

        double enemyHealth = enemy.getHealth();
        long enemyHealthRounded = Math.round(enemyHealth);
        return Long.toString(enemyHealthRounded);
    }

    public static String getEnemyHearts(ICombatLogX plugin, Player player) {
        ICombatManager combatManager = plugin.getCombatManager();
        LivingEntity enemy = combatManager.getEnemy(player);
        if(enemy == null) return getUnknownEnemy(plugin, player);

        double enemyHealth = enemy.getHealth();
        double enemyHearts = (enemyHealth / 2.0D);
        int enemyHeartsRounded = (int) Math.round(enemyHearts);
        if(enemyHeartsRounded > 10) return Integer.toString(enemyHeartsRounded);

        char heartSymbol = '\u2764';
        char[] charArray = new char[enemyHeartsRounded];
        Arrays.fill(charArray, heartSymbol);

        String hearts = new String(charArray);
        return MessageUtility.color("&4" + hearts);
    }

    public static String getUnknownEnemy(ICombatLogX plugin, Player player) {
        LanguageManager languageManager = plugin.getLanguageManager();
        return languageManager.getMessageColored(player, "placeholder.unknown-enemy");
    }

    public static DecimalFormat getDecimalFormat(ICombatLogX plugin, Player player) {
        LanguageManager languageManager = plugin.getLanguageManager();
        String decimalFormatString = languageManager.getMessage(player, "decimal-format");
        return new DecimalFormat(decimalFormatString);
    }
}
