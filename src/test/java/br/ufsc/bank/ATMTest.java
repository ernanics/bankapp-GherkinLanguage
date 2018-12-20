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
			assertEquals(e.getMessage().equals("O valor informado é inválido!"),true);
		}
	}
	
	@Test
	public void withdraw_no_funds(){
		try {
			atm.withDraw("9999999999999999", 123456, 100);
			assertEquals(atm.getBank().getAccountByCardNumber("9999999999999999").getBalance(),90.0,0.0);
		} catch (Exception e) {
			assertEquals(e.getMessage().equals("Saldo insuficiente! Por favor, verifique seu saldo e tente novamente."),true);
		}
	}
	
	@Test
	public void withdraw_wrong_password(){
		try {
			atm.withDraw("9999999999999999", 123123, 100);
			assertEquals(atm.getBank().getAccountByCardNumber("9999999999999999").getBalance(),90.0,0.0);
		} catch (Exception e) {
			assertEquals(e.getMessage().equals("Senha inválida!"),true);
		}
	}
	
	@Test
	public void withdraw_wrong_password_twice(){
		try {
			atm.getBank().getCardByNumber("9999999999999999").insertPinHistory(false, new Date(new Date().getTime()-(70*3600000)));
			atm.withDraw("9999999999999999", 123123, 100);
			assertEquals(atm.getBank().getAccountByCardNumber("9999999999999999").getBalance(),90.0,0.0);
		} catch (Exception e) {
			assertEquals(e.getMessage().equals("Senha inválida! Você tem mais uma tentativa nas próximas 2 horas, do contário, seu cartão será bloqueado por motivos de segurança."),true);
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
			assertEquals(e.getMessage().equals("Senha inválida! Seu cartão foi bloqueado por motivos de segurança! Entre em contato com a central de serviços para maiores informações."),true);
		}
	}
}
