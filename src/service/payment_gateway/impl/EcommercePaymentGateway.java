package service.payment_gateway.impl;

import enums.Mode;
import factory.BankPaymentRouter;
import model.Clients;
import security.SecurityManager;
import service.bank.Bank;
import service.payment_gateway.PaymentGateway;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EcommercePaymentGateway implements PaymentGateway {
    private final Map<String, Clients> clients;
    private final BankPaymentRouter bankPaymentRouter;

    public EcommercePaymentGateway() {
        this.clients = new HashMap<>();
        this.bankPaymentRouter = new BankPaymentRouter();
    }

    @Override
    public void addClient(Clients client) {
        clients.put(client.getClientId(), client);
    }

    @Override
    public void removeClient(String clientId) {
        clients.remove(clientId);
    }

    @Override
    public boolean hasClient(String clientId) {
        return clients.containsKey(clientId);
    }

    @Override
    public void listSupportedPaymentModes(String clientId) {
        Clients client = clients.get(clientId);
        System.out.println(client != null
                ? "Supported paymodes for client " + clientId + ": " + client.getSupportedPaymentModes()
                : "Client not found!");
    }

    @Override
    public void addSupportForPaymentMode(String clientId, Mode payMode) {
        Clients client = clients.get(clientId);
        if (client != null) {
            client.getSupportedPaymentModes().put(payMode, true);
        } else {
            System.out.println("Client not found!");
        }
    }

//    @Override
//    public void removePaymentMode(String clientId, Mode paymentMode) {
//        Clients client = clients.get(clientId);
//        if (client != null) {
//            client.getSupportedPaymentModes().remove(paymentMode);
//        } else {
//            System.out.println("Client not found!");
//        }
//    }

    @Override
    public void showDistribution() {
        // if we want to see the configured distribution
        BankPaymentRouter.bankNameWeightageMap.forEach((k, v) -> System.out.println("Current bank distribution: " + k + " : " + v));

        // if we want to see the ratio at which the distribution has happened
//        PaymentCallCount.paymentCallCountMap.forEach((k, v) -> System.out.println("Current bank distribution: " + k + " : " + v));
    }

    @Override
    public void makePayment(String clientId, Mode payMode, String key, String value) {

        if (!hasClient(clientId)) {
            System.out.println("No Client for provided clientId");
            //
        }

        if (Objects.equals(payMode, Mode.NETBANKING)) {
            if (SecurityManager.credentials.get(key) == null || !Objects.equals(SecurityManager.credentials.get(key), value)){
                System.out.println("Bad credentials!!");
                return;
            }
        }
//        if (Objects.equals(payMode, Mode.CARD)) {
//            if (SecurityManager.credentials.get(key) =)
//        }

        Clients client = clients.get(clientId);
        if (client != null) {
            if (client.getSupportedPaymentModes().getOrDefault(payMode, false)) {

                boolean isTransactionSuccessful = processTransaction(payMode);

                System.out.println("Payment " + (isTransactionSuccessful ? "successful" : "failed") +
                        "! Client: " + clientId + ", PaymentMode: " + payMode);
            } else {
                System.out.println("Unsupported paymentMode for the client!");
            }
        } else {
            System.out.println("Client not found!");
        }
    }

    private boolean processTransaction(Mode mode) {
        Bank bank = bankPaymentRouter.getBankByPaymentMode(mode);
        if (bank == null) {
            System.out.println("Payment Mode not supported!!");
            return false;
        }
        return bank.processTransaction();
    }
}
