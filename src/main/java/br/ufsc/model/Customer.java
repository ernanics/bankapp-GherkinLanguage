package br.ufsc.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private long id;
	private String fullName;
	private List<Card> cards = null;
	
	public Customer(){
		
	}
	
	public Customer(long id, String fullName, List<Card> cards){
		this.setId(id);
		this.setFullName(fullName);
		this.setCards(cards);
	}
	
	public Customer(long id, String fullName, Card card){
		this.setId(id);
		this.setFullName(fullName);
		this.getCards().add(card);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getFullName(){
		return this.fullName;
	}
	
	public void setFullName(String fullName){
		this.fullName = fullName;
	}
	
	public List<Card> getCards() {
		if (this.cards == null){
			this.cards = new ArrayList<Card>();
		}
		return this.cards;
	}
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public Card getCardByCardNumber(String cardNumber) throws Exception{
		for(Card card : this.getCards()){
			if(card.getNumber() == cardNumber){
				return card;
			}
		}
		throw new Exception("Invalid card!");
	}
	
}
