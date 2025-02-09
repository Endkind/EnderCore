package net.endkind.enderCore.platform.papermc;

import net.endkind.enderCore.api.IEnderPlugin;
import net.endkind.enderCore.core.EnderLogger;
import net.endkind.enderCore.utils.AnsiTextFormatter;
import net.endkind.enderCore.utils.EnderColor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class EnderPlugin extends JavaPlugin implements IEnderPlugin {
    protected EnderLogger logger = new EnderLogger(getPluginMeta().getName());
    protected FileConfiguration config;

    @Override
    public void onEnable() {
        this.onCoreEnable();
        this.onPluginEnable();
    }

    @Override
    public void onDisable() {
        this.onCoreDisable();
        this.onPluginDisable();
    }

    @Override
    public void onCoreEnable() {
        this.loadConfig();
        this.logger = this.getEnderLogger();
    }

    @Override
    public void onCoreDisable() {}

    public Component getPrefix(String prefix, String hex, boolean useArrow) {
        int[] rgb = EnderColor.hexToRgb(hex);

        Component msg = Component.text("").color(TextColor.color(255, 255, 255))
                .append(Component.text(prefix).color(TextColor.color(rgb[0], rgb[1], rgb[2])));

        if (useArrow) {
            msg = msg.append(Component.text(" »").color(TextColor.color(119, 119, 119)));
        }

        return msg.append(Component.text(" ").color(TextColor.color(255, 255, 255)));
    }

    @Override
    public void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        this.config = getConfig();

        this.config.options().copyDefaults(true);
        saveConfig();

        if (this.getRequiredConfigVersion() != this.config.getInt("version")) {
            logger.error("Config version musst be " + this.getRequiredConfigVersion());
        }
    }

    @Override
    public EnderLogger getEnderLogger() {
        String prefix = config.getString("prefix.name");
        String color = config.getString("prefix.color");
        boolean useArrow = config.getBoolean("prefix.useArrow");

        this.logger = new EnderLogger(prefix, color, useArrow);
        return this.logger;
    }

    @Override
    public int getRequiredConfigVersion() {
        InputStream configStream = getResource("config.yml");

        FileConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(configStream, StandardCharsets.UTF_8));

        return config.getInt("version");
    }

    @Override
    public Component genMessage(Component... msg) {
        String prefix = config.getString("prefix.name");
        String color = config.getString("prefix.color");
        boolean useArrow = config.getBoolean("prefix.useArrow");

        Component message = getPrefix(prefix, color, useArrow);

        for (Component c : msg) {
            message = message.append(c);
        }

        return message;
    }
}
