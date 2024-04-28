package service.payment_gateway;

import enums.Mode;
import model.Clients;

public interface PaymentGateway {
    void addClient(Clients client);

    void removeClient(String clientId);

    boolean hasClient(String clientId);

    void listSupportedPaymentModes(String clientId);

    void addSupportForPaymentMode(String clientId, Mode payMode);

//    void removePaymentMode(String clientId, Mode payMode);

    void showDistribution();

    void makePayment(String clientId, Mode payMode, String key, String value);

}
