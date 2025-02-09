package net.endkind.enderCore.api;

import net.endkind.enderCore.core.EnderLogger;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandExecutor;

public interface IEnderPlugin {
    void onCoreEnable();
    void onCoreDisable();
    void onPluginEnable();
    void onPluginDisable();
    Component getPrefix(String prefix, String hex, boolean useArrow);
    int getRequiredConfigVersion();
    void loadConfig();
    void reload();
    EnderLogger getEnderLogger();
    Component genMessage(Component... msg);
    void registerBukkitCommand(String name, CommandExecutor cmdExecutor);
}
