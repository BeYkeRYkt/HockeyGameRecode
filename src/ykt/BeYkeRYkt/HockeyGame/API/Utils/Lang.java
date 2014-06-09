package ykt.BeYkeRYkt.HockeyGame.API.Utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * An enum for requesting strings from the language file.
 */
public enum Lang {
    TITLE("title-name", "&4[&fHockeyGame&4]:"),
    AUTHOR_OF_TRANSLATION("author-translation", "&aAuthor of translation: "),
    PLUGIN_RESTARTED("plugin-restarted", "&aReloaded was successful!"),
    AUTHOR_TRANSLATE("author-translate", "Google Translate"),
    GATE_STORED("gate-stored", "&aGate stored."),
    HOCKEY_STICK("hockey-stick", "&l&2Hockey Stick"),
    SUCCESS_SIGN_CREATE("success-sign-create", "&aSign succesfully created!"),
    ARENA_SUCCESS_CREATE("arena-success-create", "&aArena succesfully created!"),
    SUCCESS_SIGN_REMOVE("success-sign-remove", "&aSign succesfully removed!"),
    CHANGE_CLASS("change-class", "&aYour class has been changed."),
    CLASS_FULL("class-full", "&4This class is full. Select another class."),
    ARENA_FULL("arena-full", "&4Sorry. Currently the arena is full. Wait ..."),
    GAME_RUNNING("game-running", "&4Sorry. Now comes the match arena. Wait ..."),
    CLASS_DOES_NOT_EXIT("class-does-not-exit", "&4Class does not exist!"),
    NO_PERMISSION("no-permission", "&4You do not have permission"),
    FIRST_TEAM_SET_LOBBY("first-team-set-lobby", "&aSpecify the lobby for the first team. Command: &e/harena setlobby"),
    FIRST_TEAM_SET_SPAWN("first-team-set-spawn", "&aSpecify the spawn point for the first team. Command: &e/harena setspawn"),
    SECOND_TEAM_SET_LOBBY("second-team-set-lobby", "&aSpecify the lobby for the second team. Command: &e/harena setlobby"),
    SECOND_TEAM_SET_SPAWN("second-team-set-spawn", "&aSpecify the spawn point for the second team. Command: &e/harena setspawn"),
    PUCK_SET_SPAWN("puck-set-spawn", "&aSpecify spawn point for puck. Command: &e/harena setpuckspawn"),
    START_CREATE_ARENA("start-create-arena", "&aWe begin to build the arena. Enter the name of the arena (Chat) . If you want to exit write to /harena cancel."),
    SECOND_TEAM_EMPTY_GATES("second-team-empty-gates", "&4Gates for second team not stored!"),
    FIRST_TEAM_EMPTY_GATES("first-team-empty-gates", "&4Gates for first team not stored!"),
    SECOND_TEAM_LOBBY_NULL("second-team-lobby-null", "&4Lobby for second team not stored!"),
    FIRST_TEAM_LOBBY_NULL("first-team-lobby-null", "&4Lobby for first team not stored!"),
    SECOND_TEAM_SPAWN_NULL("second-team-spawn-null", "&4Spawnpoint for second team not stored!"),
    FIRST_TEAM_SPAWN_NULL("first-team-spawn-null", "&4Spawnpoint for first team not stored!"),
    PUCK_SPAWN_NULL("puck-spawn-null", "&4Spawnpoint for puck not stored!"),
    ARENA_DOES_NOT_EXIT("arena-does-not-exit", "&4Arena does not exist!"),
    SET_GATES("set-gates", "&aIts time to install the gates. Stand on the position where you want to put a 'gate' and write &e/harena setfirstgate &aand &e/harena setsecondgate. &aWhen finished write &e/harena save"),
    ARENA_SAVED("arena-saved", "&aArena succesfully saved!"),
    ARENA_NAME_IS_TAKEN("arena-name-is-taken", "&4This name is already taken. Please choose another."),
    ENTER_NAME_THE_FIRST_TEAM("enter-name-the-first-team", "&aEnter the name of the first team."),
    ENTER_NAME_THE_SECOND_TEAM("enter-name-the-second-team", "&aEnter the name of the second team."), 
    PUCK_NAME("puck-name", "&2Puck"), 
    TEAM_DOES_NOT_EXIT("team-does-not-exit", "&4Team does not exist!"),
    CREATE_ARENA_CANCELLED("create-arena-cancelled", "&aCreation of arena was cancelled."),
    PLAYER_NOT_READY("player-not-ready", "&4You're not ready. Choose your class."),
    PLAYER_READY("player-is-ready", "&7 is ready!"),
    MATCH_CONTINUES("match-continues", "&aGame continues! Puck respawned!"),
    SCORED_GOAL("scored-goal", "&a scored goal of gates "), 
    TEAM_WIN("team-win", "&a won this match!"),
    TIE("tie", "&7Match ended in a tie."),
    RESULT("result", "&6Results: "),
    GAME_STARTED("game-started", "&aMatch started!"),
    INV_RESTORED("inv-restored", "&aYour inventory is restored!"),
    INV_SAVED("inv-save", "&aYour inventory is saved!"),
    PLAYER_JOIN_ARENA("player-join-arena", "&6 joined to arena "),
    PLAYER_LEAVE_ARENA("player-leave-arena", "&6 leave to arena "),
    ICON_CHANGE_LANG("icon-change-lang", "&2Change language"),
    AVAILABLE_LANGUAGES("available-languages", "&aAvailable languages:"), 
    ICON_ARENAS("icon-arenas", "&6Arenas"),
    ICON_JOIN_CLICK("icon-join-click", "&fClick here to join to arena."),
    ICON_LEAVE_CLICK("icon-leave-click", "&fClick here to leave to arena."),
    AVAILABLE_ARENAS("available-arenas", "&aAvailable arenas:"),
    ICON_RELOAD("icon-reload", "&6Reload plugin"), 
    PLAYERS("players", "&aPlayers: "),
    TEAMS("teams", "&aTeams: "),
    ICON_ARENA_LEAVE("icon-arena-leave", "&2Leave");

    private String path;
    private String def;
    private static YamlConfiguration LANG;

    /**
     * Lang enum constructor.
     *
     * @param path The string path.
     * @param start The default string.
     */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    /**
     * Set the {@code YamlConfiguration} to use.
     *
     * @param config The config to set.
     */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    @Override
    public String toString() {
        if (this == TITLE)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    /**
     * Get the default value of the path.
     *
     * @return The default value of the path.
     */
    public String getDefault() {
        return this.def;
    }

    /**
     * Get the path to the string.
     *
     * @return The path to the string.
     */
    public String getPath() {
        return this.path;
    }
}