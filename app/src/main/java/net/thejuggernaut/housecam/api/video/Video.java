package net.thejuggernaut.housecam.api.video;

import java.io.Serializable;

public class Video implements Serializable {

    String code;
    int size;
    String image;
    int stamp;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
