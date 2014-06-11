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
    
    SUCCESS_SIGN_REMOVE("success-sign-remove", "&aSign succesfully removed!"),
    CHANGE_CLASS("change-class", "&aYour class has been changed."),
    CLASS_FULL("class-full", "&cThis class is full. Select another class."),
    ARENA_FULL("arena-full", "&cSorry. Currently the arena is full. Wait ..."),
    GAME_RUNNING("game-running", "&cSorry. Now comes the match arena. Wait ..."),
    CLASS_DOES_NOT_EXIT("class-does-not-exit", "&cClass does not exist!"),
    NO_PERMISSION("no-permission", "&cYou do not have permission"),
    FIRST_TEAM_SET_LOBBY("first-team-set-lobby", "&aClick on the block where you want to put the lobby of the first team."),
    FIRST_TEAM_SET_SPAWN("first-team-set-spawn", "&aClick on the block where you want to put the spawnpoint of the first team."),
    SECOND_TEAM_SET_LOBBY("second-team-set-lobby", "&aClick on the block where you want to put the lobby of the second team."),
    SECOND_TEAM_SET_SPAWN("second-team-set-spawn", "&aClick on the block where you want to put the spawnpoint of the second team."),
    PUCK_SET_SPAWN("puck-set-spawn", "&aClick on the block where you want to put spawnpoint for puck."),
    START_CREATE_ARENA("start-create-arena", "&aWe begin to build the arena. Enter the name of the arena (Chat) . If you want to exit write to /hockey and clicked "),
    SECOND_TEAM_EMPTY_GATES("second-team-empty-gates", "&cGates for second team not stored!"),
    FIRST_TEAM_EMPTY_GATES("first-team-empty-gates", "&cGates for first team not stored!"),
    ARENA_DOES_NOT_EXIT("arena-does-not-exit", "&cArena does not exist!"),
    ARENA_SAVED("arena-saved", "&aArena succesfully saved!"),
    ARENA_NAME_IS_TAKEN("arena-name-is-taken", "&cThis name is already taken. Please choose another."),
    SELECT_THE_FIRST_TEAM("select-the-first-team", "&aSelect the first team."),
    SELECT_THE_SECOND_TEAM("select-the-second-team", "&aSelect the second team."), 
    PUCK_NAME("puck-name", "&2Puck"), 
    TEAM_DOES_NOT_EXIT("team-does-not-exit", "&cTeam does not exist!"),
    CREATE_ARENA_CANCELLED("create-arena-cancelled", "&aCreation of arena was cancelled."),
    PLAYER_NOT_READY("player-not-ready", "&cYou're not ready. Choose your class."),
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
    ICON_ARENA_LEAVE("icon-arena-leave", "&2Leave"),
    START_CREATE_TEAM("start-create-team", "&aEnter the name of new team (Chat). "),
    SELECT_TEAM_COLOR("select-team-color", "&aSelect the team color."),
    TEAM_NAME_IS_TAKEN("team-name-is-taken", "&cThis name is already taken. Please choose another."),
    TEAM_SAVED("team-saved", "&aTeam succesfully saved!"),
    NO_COMMANDS("no-commands", "&aYou are in a hockey-match - use first &e/hockey &aand click "),
    ARENA_MANAGER("arena-manager", "Arena manager"),
    TEAM_MANAGER("team-manager", "Team manager"),
    ICON_CREATE_ARENA("icon-create-arena", "&aCreate arena"),
    ICON_DELETE_ARENA("icon-delete-arena", "&cDelete arena"),
    ICON_STOP_ARENA("icon-stop-arena", "&eStop arena"), 
    ICON_CREATE_TEAM("icon-create-team", "&aCreate team"),
    ICON_DELETE_TEAM("icon-delete-team", "&cDelete team"),
    ICON_NEXT_STAGE("icon-next-stage", "&3Next stage"),
    SET_FIRST_GATES("set-first-gates", "&aInstall the gate for the first team. After setting write /hockey and select "),
    SET_SECOND_GATES("set-second-gates", "&aInstall the gate for the second team. After setting write /hockey and select "),
    ICON_CANCEL("icon-cancel", "&cCancel"),
    CREATE_TEAM_CANCELLED("create-team-cancelled", "&aCreation of team was cancelled."),
    ARENA_DELETED("arena-deleted", "&aArena succesfully deleted!"),
    TEAM_DELETED("team-deleted", "&aTeam succesfully deleted!");

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