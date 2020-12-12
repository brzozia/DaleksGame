package controller;

import game.utils.Direction;
import static game.utils.Direction.*;
import java.util.Map;


public class KeyBindings {
    //class made for easier remapping buttons
    public static String useTeleport = "t";
    public static String useTeleportNumerical = "5";
    public static String useBomb = "b";
    public static String useReset = "r";


    //newer version of ke.getText().matches("[1-4|6-9]")
    private static final Map<String, Direction> moveControls = Map.of(
            "1", SOUTHWEST,
            "2", SOUTH,
            "3", SOUTHEAST,
            "4", WEST,
            "6", EAST,
            "7", NORTHWEST,
            "8", NORTH,
            "9", NORTHEAST
    );

    public static boolean isMovementKey(String key) {
        return moveControls.containsKey(key);
    }

    public static Direction keyToDirection(String key) {
        if(!isMovementKey(key)) throw new RuntimeException("This button isn't mapped to movement direction");
        return moveControls.get(key);
    }

    public static boolean isBombKey(String key) {
        return useBomb.equals(key);
    }

    public static boolean isTeleportKey(String key) {
        return useTeleport.equals(key) || useTeleportNumerical.equals(key);
    }

    public static boolean isResetKey(String key) {
        return useReset.equals(key);
    }

}
