package bank;

import bank.card.CardType;
import bank.card.ICard;

public interface IBank {

//    IBankResponse request(ClientRequisites costumer, double sum);
    IBankResponse queue(ClientRequisites costumer, BankRequest request);
    IBankResponse requestBalance(ClientRequisites costumer);
    IBankResponse showCardHistory(ClientRequisites costumer);
    IBankResponse showCredit(ClientRequisites costumer);

    ICard initNewCard(CardType type);
    String getBankName();
}
