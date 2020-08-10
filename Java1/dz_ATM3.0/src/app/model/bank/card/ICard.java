package app.model.bank.card;

import app.model.bank.ClientRequisites;

import java.io.Serializable;

public interface ICard extends Serializable {
    ClientRequisites getCardInfo();
}
