package br.ufsc.bank;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.model.ATM;

public class ATMTest {
	
	private ATM atm;
	
	@Before
	public void setUp(){
		atm = new ATM();
		atm.getBank().createBankCustomer(1, "John Frederic Piper", "9999999999999999", 123456, "125654-08", 90.00);
		atm.chargeBills(0, 200, 0, 150, 100);
	}
	
	@Test
	public void withdraw(){
		try {
			atm.withDraw("9999999999999999", 123456, 30);
			assertEquals(atm.getBank().getAccountByCardNumber("9999999999999999").getBalance(),60.0,0.0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void withdraw_invalid_amount(){
		try {
			atm.withDraw("9999999999999999", 123456, 31);
			assertEquals(atm.getBank().getAccountByCardNumber("9999999999999999").getBalance(),90.0,0.0);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void withdraw_no_funds(){
		try {
			atm.withDraw("9999999999999999", 123456, 100);
			assertEquals(atm.getBank().getAccountByCardNumber("9999999999999999").getBalance(),90.0,0.0);
		} catch (Exception e) {
			assertEquals(e.getMessage().equals("Non-sufficient funds! Please, check your balance and try again."),true);
		}
	}
	
	@Test
	public void withdraw_wrong_password(){
		try {
			atm.withDraw("9999999999999999", 123123, 100);
			assertEquals(atm.getBank().getAccountByCardNumber("9999999999999999").getBalance(),90.0,0.0);
		} catch (Exception e) {
			assertEquals(e.getMessage().equals("Invalid PIN!"),true);
		}
	}
	
	@Test
	public void withdraw_wrong_password_twice(){
		try {
			atm.getBank().getCardByNumber("9999999999999999").insertPinHistory(false, new Date(new Date().getTime()-(70*3600000)));
			atm.withDraw("9999999999999999", 123123, 100);
			assertEquals(atm.getBank().getAccountByCardNumber("9999999999999999").getBalance(),90.0,0.0);
		} catch (Exception e) {
			assertEquals(e.getMessage().equals("Invalid PIN! You have one more attempt in the next 2 hours, otherwise your bank card will be blocked for security reasons!"),true);
		}
	}
	
	@Test
	public void withdraw_wrong_password_blocked(){
		try {
			atm.getBank().getCardByNumber("9999999999999999").insertPinHistory(false, new Date(new Date().getTime()-(68*3600000)));
			atm.getBank().getCardByNumber("9999999999999999").insertPinHistory(false, new Date(new Date().getTime()-(70*3600000)));
			atm.withDraw("9999999999999999", 123123, 100);
			assertEquals(atm.getBank().getAccountByCardNumber("9999999999999999").getBalance(),90.0,0.0);
		} catch (Exception e) {
			assertEquals(e.getMessage().equals("Invalid PIN! Your Bank Card has been blocked for security reasons! Please, call customer service for more information."),true);
		}
	}
}
