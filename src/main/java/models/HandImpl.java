package models;

import java.util.ArrayList;
import java.util.List;

import controllers.Card;

public class HandImpl implements Hand {

    List<Card> cards;

    public HandImpl() {
        cards = new ArrayList<>();
    }

    public HandImpl(final ArrayList<Card> hand) {
        cards = new ArrayList<>(hand);
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public void addCard(final Card c) {
        cards.add(c);

    }

    @Override
    public void removeCard(final int index) {
        cards.remove(index);
    }

    public Card getCard(final int index) {
        return cards.get(index);
    }

}
