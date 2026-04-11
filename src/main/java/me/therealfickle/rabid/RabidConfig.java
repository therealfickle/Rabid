package me.therealfickle.rabid;

import me.fzzyhmstrs.fzzy_config.config.Config;

import static me.therealfickle.rabid.Rabid.MODID;
import static me.therealfickle.rabid.Rabid.id;

public class RabidConfig extends Config {
    public RabidConfig() {
        super(id(MODID));
    }

    public boolean HELRCIsFickleOnly = true;
}
