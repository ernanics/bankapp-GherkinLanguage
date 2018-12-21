package br.ufsc.cucumber.steps;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

import br.ufsc.model.ATM;
import br.ufsc.model.PinHistory;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ATMTest {
	public String nomeCliente;
	public String contaCorrente;
	public String numeroCartao;
	public int senhaCartao;
	public double saldo;
	public int valorSacar;
	public int senharCartaoInformada;
	public ATM atm;
	
	@Given("^o cliente \"([^\"]*)\"$")
	public void o_cliente(String arg1) {
	    this.nomeCliente = arg1;
	}

	@Given("^o número da conta corrente do cliente e \"([^\"]*)\"$")
	public void o_número_da_conta_corrente_do_cliente_e(String arg1) {
	    this.contaCorrente = arg1;
	}

	@Given("^o número do cartao da conta e \"([^\"]*)\"$")
	public void o_número_do_cartao_de_credito_e(String arg1) {
	    this.numeroCartao = arg1;
	}

	@Given("^a senha do cartao da conta e (\\d+)$")
	public void a_senha_do_cartao_da_conta_e(int arg1) {
	    this.senhaCartao = arg1;
	}


	@Given("^o saldo da conta corrente do cliente e \\$([^\"]*)")
	public void o_saldo_da_conta_corrente_do_cliente_e_$(float arg1) {
	    this.saldo = arg1;
	}

	@Given("^o cliente escolhe a opcao Saque$")
	public void o_cliente_escolhe_a_opcao_Saque() {
	    atm = new ATM();
	    atm.getBank().createBankCustomer(1, this.nomeCliente, this.numeroCartao, this.senhaCartao, this.contaCorrente, this.saldo);
	}

	@Given("^no caixa eletronica ha (\\d+) notas de 5, (\\d+) notas de 10, (\\d+) notas de 20, (\\d+) notas de 50 e (\\d+) notas de 100$")
	public void no_caixa_eletronica_ha_notas_de_notas_de_notas_de_notas_e_notas(int arg1, int arg2, int arg3, int arg4, int arg5) {
	    atm.chargeBills(arg1, arg2, arg3, arg4, arg5);
	}

	@Given("^o cliente nao informou nenhum vez sua senha incorretamente nas ultimas (\\d+) horas$")
	public void o_cliente_nao_informou_nenhum_vez_sua_senha_incorretamente_nas_ultimas_horas(int arg1) {
	    try {
			atm.getBank().getCardByNumber(this.numeroCartao).setPinHistory(new ArrayList<PinHistory>());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@When("^o cliente informa o valor para sacar de \\$(\\d+)$")
	public void o_cliente_informa_o_valor_para_sacar_de_$(int arg1) {
	    this.valorSacar = arg1;
	}

	@When("^e informa a senha (\\d+)$")
	public void e_informa_a_senha(int arg1) {
	    this.senharCartaoInformada = arg1;
	}

	@When("^e pressiona a tecla Confirma$")
	public void e_pressiona_a_tecla_Confirma() {
	    //do nothing
	}

	@Then("^o caixa eletronico ejeta o dinheiro$")
	public void o_caixa_eletronico_ejeta_o_dinheiro(){
	    try {
			atm.withDraw(this.numeroCartao, this.senharCartaoInformada, this.valorSacar);
		} catch (Exception e) {
			assertEquals("", e.getMessage());
			
		}
	}

	@Then("^o saldo da conta correte do cliente e de \\$([^\"]*)")
	public void o_saldo_da_conta_correte_do_cliente_e_de_$(float arg1){
		try {
			assertEquals(arg1,this.atm.getBank().getAccountByCardNumber(this.numeroCartao).getBalance(),0.01);
		} catch (Exception e) {
			assertEquals("", e.getMessage());
		}
	    
	}

	@Then("^o caixa eletronico mostra a mensagem \"([^\"]*)\"$")
	public void o_caixa_eletronico_mostra_a_mensagem(String arg1) {
		try {
			atm.withDraw(this.numeroCartao, this.senharCartaoInformada, this.valorSacar);
		} catch (Exception e) {
			assertEquals(arg1, e.getMessage());
			
		}
	}

	@Given("^o cliente informou pela primeira vez sua senha incorretamente ha (\\d+) horas atras$")
	public void o_cliente_informou_pela_primeira_vez_sua_senha_incorretamente_ha_horas_atras(int arg1) {
	    try {
			atm.getBank().getCardByNumber(this.numeroCartao).insertPinHistory(false, new Date(new Date().getTime()-(arg1*3600000)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Given("^o cliente informou pela segunda vez sua senha incorretamente ha (\\d+) horas atras$")
	public void o_cliente_informou_pela_segunda_vez_sua_senha_incorretamente_ha_horas_atras(int arg1) throws Throwable {
		try {
			atm.getBank().getCardByNumber(this.numeroCartao).insertPinHistory(false, new Date(new Date().getTime()-(arg1*3600000)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Given("^o cliente informou pela terceira vez sua senha incorretamente ha (\\d+) horas atras$")
	public void o_cliente_informou_pela_terceira_vez_sua_senha_incorretamente_ha_horas_atras(int arg1) throws Throwable {
	    try {
			atm.getBank().getCardByNumber(this.numeroCartao).insertPinHistory(false, new Date(new Date().getTime()-(arg1*3600000)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
