import enums.Mode;
import model.Clients;
import model.impl.EcommerceClient;
import service.payment_gateway.PaymentGateway;
import service.payment_gateway.impl.EcommercePaymentGateway;


public class Main {
    public static void main(String[] args) {

        PaymentGateway paymentGateway = new EcommercePaymentGateway();

        // Adding clients using Factory Method pattern
        Clients flipkart = new EcommerceClient("Flipkart");
        paymentGateway.addClient(flipkart);

        // Adding supported paymentModes for clients
        paymentGateway.addSupportForPaymentMode("Flipkart", Mode.UPI);
        paymentGateway.addSupportForPaymentMode("Flipkart", Mode.CARD);
        paymentGateway.addSupportForPaymentMode("Flipkart", Mode.NETBANKING);

        // Adding bank distribution
        paymentGateway.showDistribution();

        // Making payments
        paymentGateway.makePayment("Flipkart", Mode.UPI, null, null);
        paymentGateway.makePayment("Flipkart", Mode.CARD, null, null);
        paymentGateway.makePayment("Flipkart", Mode.NETBANKING, "key1", "value");

        //Listed supported payment mode before removal of the client
        paymentGateway.listSupportedPaymentModes("Flipkart");

        // Removing a client
        paymentGateway.removeClient("Flipkart");
        paymentGateway.listSupportedPaymentModes("Flipkart");
        paymentGateway.showDistribution();

    }
}