package me.okexox.hades.data;

import java.util.HashMap;

public class PlayerViolations {
    HashMap<String,Integer> violations = new HashMap<>();

    public int addViolation(String detection) {
        Integer violation = violations.get(detection);
        if(violations.get(detection) != null) {
            violation++;
            violations.replace(detection, violation);
            return violation;
        } else {
            violations.put(detection, 1);
            return 1;
        }
    }
}
