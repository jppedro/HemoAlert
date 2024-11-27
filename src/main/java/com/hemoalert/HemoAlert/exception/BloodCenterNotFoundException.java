package com.hemoalert.HemoAlert.exception;

public class BloodCenterNotFoundException extends RuntimeException {
    public BloodCenterNotFoundException(String message) {
        super(message);
    }
}