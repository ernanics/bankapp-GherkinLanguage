package br.ufsc.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	private List<Customer> customers = null;

	public List<Customer> getCustomers() {
		if (this.customers == null){
			this.customers = new ArrayList<Customer>();
		}
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public Customer getCustomerByCardNumber(String cardNumber) throws Exception{
		for(Customer customer : this.getCustomers()){
			for(Card card : customer.getCards()){
				if (card.getNumber().equals(cardNumber)){
					return customer;
				}
			}
		}
		throw new Exception("Customer not found!");
	}
	
	public Account getAccountByCardNumber(String cardNumber) throws Exception{
		for(Customer customer : this.getCustomers()){
			for(Card card : customer.getCards()){
				if (card.getNumber().equals(cardNumber)){
					return card.getAccount();
				}
			}
		}
		throw new Exception("Account not found!");
	}
	
	public Card getCardByNumber(String cardNumber) throws Exception{
		for(Customer customer : this.getCustomers()){
			for(Card card : customer.getCards()){
				if (card.getNumber().equals(cardNumber)){
					return card;
				}
			}
		}
		throw new Exception("Card not found!");
	}
	
	public List<Customer> getListOfCustomerByAccountNumber(String accountNumber) throws Exception{
		List<Customer> listOfCustomer = new ArrayList<Customer>();
		for(Customer customer : this.getCustomers()){
			for(Card card : customer.getCards()){
				if (card.getAccount().getAccountNumber().equals(accountNumber)){
					listOfCustomer.add(customer);
				}
			}
		}
		return listOfCustomer;
	}
	
	public Customer createBankCustomer(long id, String fullName, String cardNumber, int pin, String accountNumber, double balance){
		Account account = new Account(accountNumber,balance);
		Card card = new Card(cardNumber,account,pin);
		Customer customer = new Customer(id, fullName, card);
		this.getCustomers().add(customer);
		return customer;
	}
}
