//Bukkit
package ykt.BeYkeRYkt.HockeyGame.API.Addons;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

import ykt.BeYkeRYkt.HockeyGame.API.HGAPI;

/**
 * The PluginLogger class is a modified {@link Logger} that prepends all
 * logging calls with the name of the plugin doing the logging. The API for
 * PluginLogger is exactly the same as {@link Logger}.
 *
 * @see Logger
 */
public class AddonLogger extends Logger {
    private String pluginName;

    /**
     * Creates a new PluginLogger that extracts the name from a addon
     *
     * @param context A reference to the addon
     */
    public AddonLogger(Addon context) {
        super(context.getClass().getCanonicalName(), null);
        pluginName = "["+ HGAPI.getPlugin().getName() + ": " + context.getName() + "] ";
        setParent(Bukkit.getServer().getLogger());
        setLevel(Level.ALL);
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(pluginName + logRecord.getMessage());
        super.log(logRecord);
    }

}