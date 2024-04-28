package service.bank.impl;

import enums.BankName;
import enums.Mode;
import service.bank.Bank;

import java.util.Random;
import java.util.Set;

public class IciciBank implements Bank {

    @Override
    public BankName getBankName() {
        return BankName.ICICI;
    }
    @Override
    public boolean processTransaction() {
        return new Random().nextBoolean();
    }

    @Override
    public boolean canSupportPaymentModes(Mode mode) {
        return Set.of(Mode.CARD, Mode.NETBANKING).contains(mode);
    }
}
