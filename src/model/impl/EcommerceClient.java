package model.impl;

import enums.Mode;
import model.Clients;

import java.util.HashMap;
import java.util.Map;

public class EcommerceClient implements Clients {

    private final String clientId;
    private final Map<Mode, Boolean> supportedPaymentModes;

    public EcommerceClient(String clientId) {
        this.clientId = clientId;
        this.supportedPaymentModes = new HashMap<>();
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Map<Mode, Boolean> getSupportedPaymentModes() {
        return supportedPaymentModes;
    }
}
