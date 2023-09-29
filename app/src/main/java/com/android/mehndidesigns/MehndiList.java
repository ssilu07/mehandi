package com.android.mehndidesigns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MehndiList {

    private final String[] CASUAL = new String[169];
    private final String[] ARM = new String[51];
    private final String[] BACK = new String[273];
    private final String[] BRIDAL = new String[250];
    private final String[] EID = new String[42];
    private final String[] FEET = new String[124];
    private final String[] FRONT = new String[169];
    private final String[] GOL = new String[78];
    private final String[] FINGER = new String[108];
    private final String[] SPECIAL = new String[250];
    private final String[] OTHER = new String[42];

    public MehndiList() {
        for (int i = 0; i < 169; i++) {
            int num = i + 1;
            CASUAL[i] = "front (" + num + ").webp";
        }
        for (int i = 0; i < 51; i++) {
            int num = i + 1;
            ARM[i] = "arm (" + num + ").webp";
        }
        for (int i = 0; i < 273; i++) {
            int num = i + 1;
            BACK[i] = "back (" + num + ").webp";
        }
        for (int i = 0; i < 250; i++) {
            int num = i + 1;
            BRIDAL[i] = "bridal (" + num + ").webp";
        }
        for (int i = 0; i < 42; i++) {
            int num = i + 1;
            EID[i] = "eid (" + num + ").webp";
        }
        for (int i = 0; i < 124; i++) {
            int num = i + 1;
            FEET[i] = "feet (" + num + ").webp";
        }
        for (int i = 0; i < 108; i++) {
            int num = i + 1;
            FINGER[i] = "finger (" + num + ").webp";
        }
        for (int i = 0; i < 169; i++) {
            int num = i + 1;
            FRONT[i] = "front (" + num + ").webp";
        }
        for (int i = 0; i < 78; i++) {
            int num = i + 1;
            GOL[i] = "gol (" + num + ").webp";
        }
        for (int i = 0; i < 250; i++) {
            int num = i + 1;
            SPECIAL[i] = "bridal (" + num + ").webp";
        }
        for (int i = 0; i < 42; i++) {
            int num = i + 1;
            OTHER[i] = "eid (" + num + ").webp";
        }
    }

    public String[] getArm() {
        return ARM;
    }

    public String[] getBridal() {
        return BRIDAL;
    }

    public String[] getBack() {
        return BACK;
    }

    public String[] getEid() {
        return EID;
    }

    public String[] getFeet() {
        return FEET;
    }

    public String[] getFront() {
        return FRONT;
    }

    public String[] getGol() {
        return GOL;
    }

    public String[] getFinger() {
        return FINGER;
    }

    public String[] getCasual() {
        return CASUAL;
    }

    public String[] getSpecial() {
        return SPECIAL;
    }

    public String[] getOther() {
        return OTHER;
    }

    public List<String> getSlider() {
        List<String> sliders = new ArrayList<>();
        List<String> slider = Arrays.asList(BACK);
        Collections.shuffle(slider);
        for (int i = 0; i < 6; i++) {
            sliders.add(slider.get(i));
        }
        return sliders;
    }
}
