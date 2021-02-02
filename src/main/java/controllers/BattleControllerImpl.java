package controllers;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.*;

public class BattleControllerImpl implements BattleController {
    
    private Player p = new PlayerImpl(30);
    private Battle b = new BattleImpl();

    @FXML
    private Label LBLPlayerHealth;
    @FXML
    private HBox HBPlayerHand;
    @FXML
    private Label LBLEnemyDamage;

    @FXML
    public void initialize() {
        p.addCard(new CardImpl("Carta prova", 2, 3, 0));
        p.addCard(new CardImpl("Carta prova 2", 1, 1, 0));
        LBLPlayerHealth.setText(String.valueOf(p.getHealth()));
        List<Card> hand = new ArrayList<>(p.getHand());
        for(int i = 0; i < hand.size(); i++) {
            final int inHand = i;
            Button b = new Button(hand.get(i).getName());
            b.setOnAction(new EventHandler<ActionEvent>() {
                final int indexInHand = inHand;
                @Override
                public void handle(ActionEvent event) {
                    selectedCard(indexInHand);
                }
                
            });
            HBPlayerHand.getChildren().add(b);
        }
    }
    
    private void selectedCard(int index) {
        if(b.playCard(p.getHand().get(index), p)) {
            LBLEnemyDamage.setText("-" + String.valueOf(p.getHand().get(index).getDamage()));
            p.removeCard(index);
            HBPlayerHand.getChildren().remove(index);
            updateHand(index);
            LBLEnemyDamage.setVisible(true);
        }
        else {
            System.out.println("Not enough mana!");
        }
    }
    
    private void updateHand(int startingIndex) {
        List<Card> hand = new ArrayList<>(p.getHand());
        for(int i = startingIndex; i < hand.size(); i++) {
            final int inHand = i;
            
            Button b = (Button) HBPlayerHand.getChildren().get(i);
            b.setOnAction(new EventHandler<ActionEvent>() {
                final int indexInHand = inHand;
                @Override
                public void handle(ActionEvent event) {
                    selectedCard(indexInHand);
                }
                
            });
        }
    }
    
}
