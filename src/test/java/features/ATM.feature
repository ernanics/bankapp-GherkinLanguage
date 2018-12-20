Feature: Caixa Eletronicos
Eu como cliente
Quero fazer uma retirada em dinheiro da minnha conta corrente
Então eu vou até um ATM, realizo a identificação através de cartão e senha,
	escolho o valor desejado e realizo o saque

  Scenario: Um cliente vai até um Caixa eletrônico e realiza com sucesso um saque, escolhendo um valor válido,
    utilizando um cartão e a senha correta. A conta corrente do cliente possui saldo suficiente.

    Given o cliente 'J. F.Piper'
    And o número da conta corrente do cliente e '125654-08'
    And o número do cartao de credito e '9988776655443322'
    And a senha do cartao de credtio e '123456'
    And o saldo da conta corrente do cliente e $90.00
    And o cliente escolhe a opcao 'Withdraw cash'
    And no caixa eletronica ha 200 notas 10, 0 notas de 20, 150 notas 50 e 110 notas 100 in the ATM
    And o cliente nao informou nenhum vez sua senha incorretamente nas utlimas 72 horas
    When o cliente informa o valor para sacar de $30.00
    And e informa a senha '123456'
    And e pressiona a tecla 'Confirma'
    Then o caixa eletronico ejeta o dinheiro
    And o saldo da conta correte do cliente e de $60.00

  Scenario: Um cliente vai até um Caixa eletrônico e não consegue realizar o saque pois informa uma senha incorreta. É
  	a primeira vez que o cliente erra a senha dentro do período de 72 horas. Se ele errar a senha três vezes no período de
  	72 horas o cartão é bloqueado por motivos de segurança.

    Given o cliente 'J. F.Piper'
    And o número da conta corrente do cliente e '125654-08'
    And o número do cartao de credito e '9988776655443322'
    And a senha do cartao de credtio e '123456'
    And o saldo da conta corrente do cliente e $90.00
    And o cliente escolhe a opcao 'Withdraw cash'
    And no caixa eletronica ha 200 notas 10, 0 notas de 20, 150 notas 50 e 110 notas 100 in the ATM
    And o cliente nao informou nenhum vez sua senha incorretamente nas utlimas 72 horas
    When o cliente informa o valor para sacar de $30.00
    And e informa a senha '123123'
    And e pressiona a tecla 'Confirma'
    Then o caixa eletronico mostra a mensagem 'Senha inválida!'

  Scenario: Um cliente vai até um Caixa eletrônico e não consegue realizar o saque pois informa uma senha incorreta. É
  	a segunda vez que o cliente erra a senha dentro do período de 72 horas. Se ele errar a senha três vezes no período de
  	72 horas o cartão é bloqueado por motivos de segurança.

    Given o cliente 'J. F.Piper'
    And o número da conta corrente do cliente e '125654-08'
    And o número do cartao de credito e '9988776655443322'
    And a senha do cartao de credtio e '123456'
    And o saldo da conta corrente do cliente e $90.00
    And o cliente escolhe a opcao 'Withdraw cash'
    And no caixa eletronica ha 200 notas 10, 0 notas de 20, 150 notas 50 e 110 notas 100 in the ATM
    And o cliente informou 'pela primeira vez' sua senha incorretamente ha 2 horas atras
    When o cliente informa o valor para sacar de $30.00
    And e informa a senha '123123'
    And e pressiona a tecla 'Confirma'
    Then o caixa eletronico mostra a mensagem 'Senha inválida! Você tem mais uma tentativa nas próximas 70 horas, do contário, seu cartão será bloqueado por motivos de segurança.'

  Scenario: Um cliente vai até um Caixa eletrônico e não consegue realizar o saque pois informa uma senha incorreta. É
  	a terceira vez que o cliente erra a senha dentro do período de 72 horas, portanto, seu cartão será bloqueado por motivos de segurança.

    Given o cliente 'J. F.Piper'
    And o número da conta corrente do cliente e '125654-08'
    And o número do cartao de credito e '9988776655443322'
    And a senha do cartao de credtio e '123456'
    And o saldo da conta corrente do cliente e $90.00
    And o cliente escolhe a opcao 'Withdraw cash'
    And no caixa eletronica ha 200 notas 10, 0 notas de 20, 150 notas 50 e 110 notas 100 in the ATM
    And o cliente informou 'pela primeira vez' sua senha incorretamente ha 3 horas atras
    And o cliente informou 'pela segunda vez' sua senha incorretamente ha 2 horas atras
    When o cliente informa o valor para sacar de $30.00
    And e informa a senha '123123'
    And e pressiona a tecla 'Confirma'
    Then o caixa eletronico mostra a mensagem 'Senha inválida! Seu cartão foi bloqueado por motivos de segurança! Entre em contato com a central de serviços para maiores informações.'
    
    Scenario: Um cliente vai até um Caixa eletrônico e não consegue realizar o saque pois seu cartao estaá bloqueado.

    Given o cliente 'J. F.Piper'
    And o número da conta corrente do cliente e '125654-08'
    And o número do cartao de credito e '9988776655443322'
    And a senha do cartao de credtio e '123456'
    And o saldo da conta corrente do cliente e $90.00
    And o cliente escolhe a opcao 'Withdraw cash'
    And no caixa eletronica ha 200 notas 10, 0 notas de 20, 150 notas 50 e 110 notas 100 in the ATM
    And o cliente informou 'pela primeira vez' sua senha incorretamente ha 4 horas atras
    And o cliente informou 'pela segunda vez' sua senha incorretamente ha 3 horas atras
    And o cliente informou 'pela terceira vez' sua senha incorretamente ha 2 horas atras
    When o cliente informa o valor para sacar de $30.00
    And e informa a senha '123456'
    And e pressiona a tecla 'Confirma'
    Then o caixa eletronico mostra a mensagem 'Este cartão está bloqueado!'

  Scenario: Um cliente vai até um Caixa eletrônico e não consegue realizar o saque pois informa um valor incorreto, isto é,
  	não existem notas disponíveis no caixa eletrônico para entregar o valor solicitado pelo cliente.
  
    Given o cliente 'J. F.Piper'
    And o número da conta corrente do cliente e '125654-08'
    And o número do cartao de credito e '9988776655443322'
    And a senha do cartao de credtio e '123456'
    And o saldo da conta corrente do cliente e $90.00
    And o cliente escolhe a opcao 'Withdraw cash'
    And no caixa eletronica ha 200 notas 10, 0 notas de 20, 150 notas 50 e 110 notas 100 in the ATM
    And o cliente nao informou nenhum vez sua senha incorretamente nas utlimas 72 horas
    When o cliente informa o valor para sacar de $31.00
    Then o caixa eletronico mostra a mensagem 'O valor informado é inválido!'

  Scenario: Um cliente vai até um Caixa eletrônico e não consegue realizar o saque pois o saldo disponível é insuficiente.
    Given o cliente 'J. F.Piper'
    And o número da conta corrente do cliente e '125654-08'
    And o número do cartao de credito e '9988776655443322'
    And a senha do cartao de credtio e '123456'
    And o saldo da conta corrente do cliente e $90.00
    And o cliente escolhe a opcao 'Withdraw cash'
    And no caixa eletronica ha 200 notas 10, 0 notas de 20, 150 notas 50 e 110 notas 100 in the ATM
    And o cliente nao informou nenhum vez sua senha incorretamente nas utlimas 72 horas
    When o cliente informa o valor para sacar de $100.00
    And e informa a senha '123456'
    And e pressiona a tecla 'Confirma'
    Then o caixa eletronico mostra a mensagem 'Saldo insuficiente! Por favor, verifique seu saldo e tente novamente.'
    And o saldo da conta correte do cliente e de $90.00
