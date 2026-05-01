package me.therealfickle.rabid;

import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedByte;

import static me.therealfickle.rabid.Rabid.MODID;
import static me.therealfickle.rabid.Rabid.id;

public class RabidConfig extends Config {
    public RabidConfig() {
        super(id(MODID));
    }

    public boolean HELRCIsFickleOnly = true;
    public boolean fickleImmuneToHunger = true;

    public MatterReconstructorSection matterReconstructor = new MatterReconstructorSection();

    public static class MatterReconstructorSection extends ConfigSection {
        public ValidatedByte maxFuelStorage = new ValidatedByte((byte) 2, Byte.MAX_VALUE, (byte) 1);
        public ValidatedByte fuelItemValue = new ValidatedByte((byte) 2, Byte.MAX_VALUE, (byte) 1);
        public ValidatedByte fuelPerRecipe = new ValidatedByte((byte) 1, Byte.MAX_VALUE, (byte) 1);

        public boolean hasSpaceForFuel(int fuelLevel) {
            return fuelLevel + fuelItemValue.get() <= maxFuelStorage.get();
        }

    }
}
