package factory;

import audit.PaymentCallCount;
import enums.BankName;
import enums.Mode;
import service.bank.Bank;
import service.bank.impl.HdfcBank;
import service.bank.impl.IciciBank;
import service.bank.impl.SbiBank;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class BankPaymentRouter {

    public static final Map<BankName, Integer> bankNameWeightageMap;

    static {
        bankNameWeightageMap = new HashMap<>();
        bankNameWeightageMap.put(BankName.ICICI, 30);
        bankNameWeightageMap.put(BankName.HDFC, 70);
        bankNameWeightageMap.put(BankName.SBI, 20);
    }

    public Bank getBankByPaymentMode(Mode mode) {

        Set<Bank> banks = new HashSet<>(List.of(new HdfcBank(), new SbiBank(), new IciciBank()));

        List<Bank> banklist = banks.stream()
                .filter(bank -> bank.canSupportPaymentModes(mode))
                .toList();

//        if (banklist.isEmpty()) {
//            return null;
//        }

        Bank eligibleBank = null;

        for (var bank : banklist) {
            var netSum = PaymentCallCount.paymentCallCountMap.values().stream().mapToLong(AtomicLong::get).reduce(0,
                    Long::sum);
            if (PaymentCallCount.paymentCallCountMap.get(bank.getBankName()).longValue() * 100 / netSum <= bankNameWeightageMap
                    .getOrDefault(bank.getBankName(), 0)) {
                eligibleBank = bank;
                break;
            }
        }
        if (eligibleBank == null) {
            eligibleBank = banklist.get(0);
        }

        PaymentCallCount.paymentCallCountMap.get(eligibleBank.getBankName()).incrementAndGet();
        return eligibleBank;
    }
}
