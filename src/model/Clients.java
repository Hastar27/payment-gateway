package model;

import enums.Mode;

import java.util.Map;

public interface Clients {
    String getClientId();

    Map<Mode, Boolean> getSupportedPaymentModes();
}
