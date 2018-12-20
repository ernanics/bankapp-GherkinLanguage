package br.ufsc.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Card {
	private String number;
	private Account account = null;
	private int pin;
	private List<PinHistory> pinHistory = null;
	private boolean isBlocked = false;
	
	public Card(){
		
	}
	
	public Card(String number, Account account, int pin){
		this.setNumber(number);
		this.setAccount(account);
		this.setPin(pin);
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Account getAccount() {
		if (this.account == null){
			this.account = new Account();
		}
		return this.account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public List<PinHistory> getPinHistory() {
		if (this.pinHistory == null){
			this.pinHistory = new ArrayList<PinHistory>();
		}
		return this.pinHistory;
	}
	public void setPinHistory(List<PinHistory> pinHistory) {
		this.pinHistory = pinHistory;
	}
	
	public void insertPinHistory(boolean result, String day, String month, String year, String hour, String minute, String seconds){
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
		StringBuffer dateStr = new StringBuffer();
		Date date = null;
		dateStr.append(day)
			.append("/")
			.append(month)
			.append("/")
			.append(year)
			.append(" ")
			.append(hour)
			.append(":")
			.append(minute)
			.append(":")
			.append(seconds);
		try {
			date = dateTimeFormat.parse(dateStr.toString());
			this.insertPinHistory(result, date);
		} catch(ParseException e ){
			e.printStackTrace();
		}
	}
	
	public int getNumberOfIncorrectPasswordWithin72hrs(){
		int count = 0;
		if (!this.getPinHistory().isEmpty()){
			List<PinHistory> reversedList = new ArrayList<PinHistory>(this.getPinHistory());
			Collections.reverse(reversedList);
			Date now = new Date();
			for (PinHistory item : reversedList){
				if (!item.isResult()){
					if ((now.getTime() - item.getDate().getTime())<=(3600000*72)){
						count = count + 1;
					} else {
						break;
					}
				}
			}
		}
		return count;
	}
	
	public Date getDateOfLastIncorrectPassword(){
		Date date = null;
		if (!this.getPinHistory().isEmpty()){
			List<PinHistory> reversedList = new ArrayList<PinHistory>(this.getPinHistory());
			Collections.reverse(reversedList);
			Date now = new Date();
			for (PinHistory item : reversedList){
				if (!item.isResult()){
					if ((now.getTime() - item.getDate().getTime())<=(3600000*72)){
						return item.getDate();
					}
				}
			}
		}
		return date;
	}
	
	public Date getDateOfFirst72HrsIncorrectPassword(){
		Date date = null;
		if (!this.getPinHistory().isEmpty()){
			List<PinHistory> reversedList = new ArrayList<PinHistory>(this.getPinHistory());
			Collections.reverse(reversedList);
			Date now = new Date();
			for (PinHistory item : reversedList){
				if (!item.isResult()){
					if ((now.getTime() - item.getDate().getTime())<=(3600000*72)){
						date = item.getDate();
					}
				}
			}
		}
		return date;
	}

	public long getTimeToExpireWrongPassword(){
		Date now = new Date();
		if (this.getNumberOfIncorrectPasswordWithin72hrs() > 0){
			return 72 - (now.getTime() - getDateOfFirst72HrsIncorrectPassword().getTime())/3600000;
		}
		return 0;
	}
	
	public void insertPinHistory(boolean result, Date date) {
		this.getPinHistory().add(new PinHistory(result, date));
		if (getNumberOfIncorrectPasswordWithin72hrs() >=3) {
			this.setBlocked(true);
		}
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
}
