package net.endkind.enderCore.core;

import net.endkind.enderCore.api.IEnderLogger;
import net.endkind.enderCore.utils.AnsiTextFormatter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EnderLogger implements IEnderLogger {
    public String prefix;
    private final Logger logger;

    public EnderLogger(String prefix, String hex, boolean useArrow) {
        this.logger = this.getLogger(prefix, hex, useArrow);
    }

    public EnderLogger(String prefix, String hex) {
        this.logger = this.getLogger(prefix, hex, true);
    }

    public EnderLogger(String prefix) {
        this.logger = this.getLogger(prefix, "#6400d4", true);
    }

    private Logger getLogger(String prefix, String hex, boolean useArrow) {
        this.prefix = this.getPrefix(prefix, hex, useArrow);

        StringBuilder sb = new StringBuilder();

        sb.append("\u001B[1D");
        sb.append(this.prefix);

        sb.append(AnsiTextFormatter.RESET);

        return Logger.getLogger(sb.toString());
    }

    private String getPrefix(String prefix, String hex, boolean useArrow) {
        StringBuilder sb = new StringBuilder();

        sb.append(AnsiTextFormatter.textColor(hex));
        sb.append(prefix);

        if (useArrow) {
            sb.append(AnsiTextFormatter.textColor("#777"));
            sb.append(" »");
        }

        return sb.toString();
    }

    private StringBuilder getStringBuilder() {
        StringBuilder sb = new StringBuilder("\u001B[2D ");
        sb.append(AnsiTextFormatter.RESET);

        return sb;
    }

    public void info(String... msg) {
        StringBuilder sb = this.getStringBuilder();

        for (String s : msg) {
            sb.append(s);
        }

        this.logger.info(sb.toString());
    }

    @Override
    public void warning(String... msg) {
        StringBuilder sb = this.getStringBuilder();

        for (String s : msg) {
            sb.append(s);
        }

        this.logger.warning(sb.toString());
    }

    @Override
    public void error(String... msg) {
        StringBuilder sb = this.getStringBuilder();

        for (String s : msg) {
            sb.append(s);
        }

        this.logger.severe(sb.toString());
    }

    @Override
    public void debug(String... msg) {
        StringBuilder sb = this.getStringBuilder();

        for (String s : msg) {
            sb.append(s);
        }

        this.logger.info(sb.toString());
    }

    @Override
    public void log(Level level, String... msg) {
        StringBuilder sb = this.getStringBuilder();

        for (String s : msg) {
            sb.append(s);
        }

        this.logger.log(level, sb.toString());
    }
}
