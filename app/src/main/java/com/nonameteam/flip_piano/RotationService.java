package com.nonameteam.flip_piano;

enum RotationType {
    left, middle, right
}

public class RotationService {
    public static RotationType getCurrentPhoneRotation() {
        return RotationType.middle;
    }
}
