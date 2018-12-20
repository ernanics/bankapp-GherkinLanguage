package br.ufsc.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ATM {
	
	Bank bank = null;
	private Map<Integer,Integer> bills = null;
	
	public void chargeBills(int numOf5Bills, int numOf10Bills, int numOf20Bills, int numOf50Bills, int numOf100Bills){
		this.checkAndCharge(5, numOf5Bills);
		this.checkAndCharge(10, numOf10Bills);
		this.checkAndCharge(20, numOf20Bills);
		this.checkAndCharge(50, numOf50Bills);
		this.checkAndCharge(100, numOf100Bills);	
	}
	
	private void checkAndCharge(int bill, int numberOfbills){
		if (this.getBills().get(bill) != null){
			this.getBills().put(bill, (this.getBills().get(bill)+numberOfbills));
		} else {
			this.getBills().put(bill, numberOfbills);
		}
	}

	public Map<Integer,Integer> getBills() {
		if (this.bills == null){
			this.bills = new HashMap<Integer,Integer>();
		}
		return bills;
	}

	public void setBills(Map<Integer,Integer> bills) {
		this.bills = bills;
	}

	public Bank getBank() {
		if (bank == null){
			this.bank = new Bank();
		}
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	public Map<Integer, Integer> withDraw(String cardNumber, int pin, Integer amount) throws Exception{
		Customer customer = this.getBank().getCustomerByCardNumber(cardNumber);
		Card card = customer.getCardByCardNumber(cardNumber);
		
		if (card.isBlocked()){
			throw new Exception("Este cartão está bloqueado!");
		}
		
		//Check bills
		Map<Integer, Integer> bills = this.getAvailableBillsByAmount(new HashMap<Integer,Integer>(), 100, 100, amount, amount);
		
		
		//Check PIN
		if (!this.checkPIN(card, pin)){
			if(card.getNumberOfIncorrectPasswordWithin72hrs()==1){
				throw new Exception("Senha inválida!");
			}
			if(card.getNumberOfIncorrectPasswordWithin72hrs()==2){
				long time = (new Date().getTime())-(card.getDateOfLastIncorrectPassword().getTime());
				time = (72*3600000)- time;
				
				throw new Exception("Senha inválida! Você tem mais uma tentativa nas próximas " + card.getTimeToExpireWrongPassword() + " horas, do contário, seu cartão será bloqueado por motivos de segurança.");
			}
			if(card.getNumberOfIncorrectPasswordWithin72hrs()>2){
				throw new Exception("Senha inválida! Seu cartão foi bloqueado por motivos de segurança! Entre em contato com a central de serviços para maiores informações.");
			}
			
		}
		
		//Check balance
		if (amount > card.getAccount().getBalance()){
			throw new Exception("Saldo insuficiente! Por favor, verifique seu saldo e tente novamente.");
		}
		
		card.getAccount().setBalance(card.getAccount().getBalance()-amount);
		
		return bills;
	}
	
	public boolean checkPIN(Card card, int pin){
		if (!(card.getPin() == pin)){
			card.insertPinHistory(false, new Date());
			if(card.getNumberOfIncorrectPasswordWithin72hrs()>=3){
				card.setBlocked(true);
			}
			return false;
		}
		
		card.insertPinHistory(true, new Date());
		return true;
	}
	
	public Map<Integer, Integer> getAvailableBillsByAmount(Map<Integer, Integer> bills, Integer bill, Integer firstAttempt, Integer totalAmount, Integer partialAmount) throws Exception {
		//Success
		if(partialAmount == 0){
			return bills;
		}
			
		if (partialAmount >= bill){
			Integer numberOfBills = partialAmount/bill;
			if (this.getNumberOfBills(bill) >= numberOfBills){
				if(bills.get(bill) != null){
					bills.put(bill, bills.get(bill)+numberOfBills);
					return getAvailableBillsByAmount(bills, this.getNextBill(bill), firstAttempt, totalAmount, partialAmount-(numberOfBills*bill));
				} else {
					bills.put(bill,numberOfBills);
					return getAvailableBillsByAmount(bills, this.getNextBill(bill), firstAttempt, totalAmount, partialAmount-(numberOfBills*bill));
				}
			}
		} 
		
		//Fail
		if ((bill == this.getNextBill(bill))&&(partialAmount>0)&&(firstAttempt == this.getNextBill(firstAttempt))){
			throw new Exception("O valor informado é inválido!");
		}
		
		//Try with other combination
		if ((bill == this.getNextBill(bill))&&(partialAmount>0)&&(firstAttempt != this.getNextBill(firstAttempt))){
			return getAvailableBillsByAmount(new HashMap<Integer,Integer>(), this.getNextBill(firstAttempt), this.getNextBill(firstAttempt), totalAmount, totalAmount);
		}
		
		//Continue with other bill
		return getAvailableBillsByAmount(bills, this.getNextBill(bill), firstAttempt, totalAmount, partialAmount);

	}
	
	private int getNextBill(int bill){
		if (bill == 100)
			return 50;
		
		if (bill == 50)
			return 20;
		
		if (bill == 20)
			return 10;
		
		return 5;
	}
	
	private int getNumberOfBills(int value){
		return this.getBills().get(value);
	}
}
