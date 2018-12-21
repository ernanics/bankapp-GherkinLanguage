package br.ufsc.cucumber.steps;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.ufsc.model.Loan;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoanTest {
	public double valorFinanciado;
	public double pcJuros;
	public long numeroParcelas;
	public Loan loan;
	
	@Given("^um cliente qualquer$")
	public void um_cliente_qualquer() {
	    //do nothing
	}

	@When("^o cliente informar \\$([^\"]*) como o valor a ser financiado$")
	public void o_cliente_informar_R$_como_o_valor_a_ser_financiado(float arg1) {
	    this.valorFinanciado = arg1;
	}

	@When("^e que a taxa de juros mensais do financiamento é ([^\"]*)%$")
	public void e_que_a_taxa_de_juros_mensais_do_financiamento_é(float arg1) {
	    this.pcJuros = arg1;
	}

	@When("^e que o financiamemnto será pago em (\\d+) parcelas$")
	public void e_que_o_financiamemnto_será_pago_em_parcelas(long arg1) {
	    this.numeroParcelas = arg1;
	}

	@Then("^o sistema calcula que o valor de cada uma das parcelas e \\$([^\"]*)$")
	public void o_sistema_calcula_que_o_valor_de_cada_uma_das_parcelas_e(double arg1) {
	    loan = new Loan(this.valorFinanciado, this.pcJuros, this.numeroParcelas);
	    assertEquals(new BigDecimal(arg1).setScale(2, RoundingMode.HALF_UP),loan.getValueOfMontlhyInstalments());
	}

	@Then("^que o valor total do financiamento com juros e \\$([^\"]*)$")
	public void que_o_valor_total_do_financiamento_com_juros_e(double arg1) {
		assertEquals(new BigDecimal(arg1).setScale(2, RoundingMode.HALF_UP),loan.getLoanAmountWithInterests());
	}
}
