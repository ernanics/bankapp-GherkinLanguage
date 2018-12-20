Feature: Simulação de Empréstimo
Eu como cliente
Quero simular o valor das parcelas de um empréstimo dada determinada taxa de juros mensal e número de parcelas
Então eu acesso o aplicativo do banco, informo estes valores e o aplicativo me mostra o valor de cada parcela
	e o valor final que irei ter pago.

  Scenario Outline: EMI é a sigla em inglês que corresponte ao valor das parcelas mensais de um financiamento.
    O calculo do EMI é realizado utilizado a fórmula matemática abaixo: 
    EMI=P*R*[((1+R)ˆN)/(((1+R)ˆN)-1)], onde 
    P é o valor total a ser financiado, 
    R é a taxa de juros mensais, 
    e N é o número de parcelas.
    O clinete informa esses valores e o aplicativo retorna o valor de cada parcela e o valor total do financiamento
    com juros.

    Given um cliente qualquer
    When o cliente informar R$ <valorFinanciamento> como o valor a ser financiado
    And e que a taxa de juros mensais do financiamento é <pcJuros>%
    And e que o financiamemnto será pago em <nuParcelas> parcelas
    Then o sistema calcula que o valor de cada uma das parcelas e <valorDaParcelas> 
    And que o valor total do financiamento com juros e <valorFinanciamentoComJuros>

    Examples: 
      | valorFinanciamento 	|pcJuros | nuParcelas | valorDaParcelas | valorFinanciamentoComJuros |
      |      10,000.00			| 0.5334 |  			 12 |          862.51 |      		         10,350.09 |
      | 100,000,000.00 			| 0.3227 |         36 |    2,946,725.65 |         		106,082,159.40 |
      |         500.00 			| 0.1250 |          5 |          100.38 |           	        501.90 |
      |   3,250,500.00 			| 0.7665 |         72 |       58,911.94 |            		4,241,659.68 |
