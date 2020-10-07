package net.thejuggernaut.housecam.api.settings;

import java.io.Serializable;

public class Zone implements Serializable {
    int x1,x2,y1,y2;
    int threshold;
    int boxJump;
    int smallIgnore;
    int area;
}
