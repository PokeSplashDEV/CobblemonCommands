package org.pokesplash.cobblemoncommands.config;

public class Booster {
    private int rate;
    private int duration;

    public Booster(int rate, int duration) {
        this.rate = rate;
        this.duration = duration;
    }

    public int getRate() {
        return rate;
    }

    public int getDuration() {
        return duration;
    }
}
