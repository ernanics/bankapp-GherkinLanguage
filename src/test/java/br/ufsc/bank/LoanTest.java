package br.ufsc.bank;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import br.ufsc.model.Loan;

public class LoanTest {
	
	@Test
	public void calculate_loan(){
		Loan loan = new Loan(10000.00, 0.5334, 12L);
		assertEquals(loan.getValueOfMontlhyInstalments(),new BigDecimal(862.51).setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(loan.getLoanAmountWithInterests(),new BigDecimal(10350.09).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	@Test
	public void calculate_loan_2(){
		Loan loan = new Loan(100000000.00, 0.3227, 36L);
		assertEquals(loan.getValueOfMontlhyInstalments(),new BigDecimal(2946725.65).setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(loan.getLoanAmountWithInterests(),new BigDecimal(106082123.23).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	@Test
	public void calculate_loan_3(){
		Loan loan = new Loan(500.00, 0.1250, 5L);
		assertEquals(loan.getValueOfMontlhyInstalments(),new BigDecimal(100.38).setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(loan.getLoanAmountWithInterests(),new BigDecimal(501.88).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
	
	@Test
	public void calculate_loan_4(){
		Loan loan = new Loan(3250500.00, 0.7665, 72L);
		assertEquals(loan.getValueOfMontlhyInstalments(),new BigDecimal(58911.94).setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(loan.getLoanAmountWithInterests(),new BigDecimal(4241659.83).setScale(2, BigDecimal.ROUND_HALF_UP));
	}
}
