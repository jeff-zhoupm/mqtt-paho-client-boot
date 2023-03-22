package com.example.mqttpahoclientboot.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum QosEnum {
    Qos0(0),
    Qos1(1),
    Qos2(2);
    private final int value;
}
