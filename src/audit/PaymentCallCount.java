package audit;

import enums.BankName;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PaymentCallCount {
    public static Map<BankName, AtomicLong> paymentCallCountMap = new ConcurrentHashMap<>();
    static {
        paymentCallCountMap.put(BankName.HDFC, new AtomicLong(1L));
        paymentCallCountMap.put(BankName.SBI, new AtomicLong(1L));
        paymentCallCountMap.put(BankName.ICICI, new AtomicLong(1L));
    }
}
