package net.endkind.enderCore.api;

import java.util.logging.Level;

public interface IEnderLogger {
    void info(String... msg);
    void warning(String... msg);
    void error(String... msg);
    void debug(String... msg);
    void log(Level level, String... msg);
}
