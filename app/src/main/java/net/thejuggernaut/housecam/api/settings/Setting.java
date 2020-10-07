package net.thejuggernaut.housecam.api.settings;

import java.io.Serializable;

public class Setting implements Serializable {
    String name;
    String connection;
    int fps;
    int minCount;
    boolean motion;
    int blur;
    boolean debug;
    int bufferBefore,bufferAfter;
    int noMoveRefreshCount;
    Zone[] zones;
}
