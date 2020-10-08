package net.thejuggernaut.housecam.api.settings;

import java.io.Serializable;

public class Setting implements Serializable {
    String name;
    String connection;
    int FPS;
    int minCount;
    boolean motion;
    int blur;
    boolean debug;
    int bufferBefore,bufferAfter;
    int noMoveRefreshCount;
    Zone[] zones;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public int getFps() {
        return FPS;
    }

    public void setFps(int fps) {
        this.FPS = fps;
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public boolean isMotion() {
        return motion;
    }

    public void setMotion(boolean motion) {
        this.motion = motion;
    }

    public int getBlur() {
        return blur;
    }

    public void setBlur(int blur) {
        this.blur = blur;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getBufferBefore() {
        return bufferBefore;
    }

    public void setBufferBefore(int bufferBefore) {
        this.bufferBefore = bufferBefore;
    }

    public int getBufferAfter() {
        return bufferAfter;
    }

    public void setBufferAfter(int bufferAfter) {
        this.bufferAfter = bufferAfter;
    }

    public int getNoMoveRefreshCount() {
        return noMoveRefreshCount;
    }

    public void setNoMoveRefreshCount(int noMoveRefreshCount) {
        this.noMoveRefreshCount = noMoveRefreshCount;
    }

    public Zone[] getZones() {
        return zones;
    }

    public void setZones(Zone[] zones) {
        this.zones = zones;
    }

}
