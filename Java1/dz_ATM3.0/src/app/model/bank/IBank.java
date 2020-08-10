package app.model.bank;

import app.IATM;
import app.model.bank.card.CardType;
import app.model.bank.card.ICard;

public interface IBank {

//    IBankResponse request(ClientRequisites costumer, double sum);
    IBankResponse queue(ClientRequisites costumer, IBankRequest request, IATM atm);
    IBankResponse requestBalance(ClientRequisites costumer);
    IBankResponse showCardHistory(ClientRequisites costumer);
    IBankResponse showCredit(ClientRequisites costumer);

    ICard initNewCard(CardType type);
    ICard loadCard(String pathToFile);
    String getBankName();

    void requestTimedOut();
}
