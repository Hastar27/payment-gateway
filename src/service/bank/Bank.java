package service.bank;

import enums.BankName;
import enums.Mode;

public interface Bank {

    BankName getBankName();

    boolean processTransaction();

    boolean canSupportPaymentModes(Mode mode);
}
