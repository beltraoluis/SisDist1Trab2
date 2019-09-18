Sistemas Distribuídos: Implementação do Algoritmo PhaseKing
  - 
Repositório dedicado ao trabalho dos alunos Luís Beltrão Santana e André Luiz Rodrigues dos Santos para a disciplina de Sistemas Distribuídos.

Introdução
  - 
O código nesse reposítório tem por objetivo implementar o algoritmo Phase-King, estudado em https://www.cs.uic.edu/~ajayk/Chapter14.pdf (slide 17)

O algoritmo foi implementado com um conjunto de cinco processos (n = 5), com um tolerância máxima de um processo malicioso (f = 1), duas fases (com duas rodadas em cada fase) e identificação única para cada nó no intervalo.

Main
  -- 
  Classe central do programa. Cria uma lista de processos, representados pela classe PhaseKingProcess, cuja chamada para execução do algoritmo é iniciada na função principal da Main.
  
PhaseKingProcess
  -- 
  Classe que reprenta um processo do algoritmo PhaseKing. Contém os atributos que representam o número de falhas toleradas, uma lista com os valores recebidos e o valor do tiebraker. Também instancia a classe MulticastPeer, uma instância para um peer do processo rei e uma instância para o grupo.
  
  A função init() configura os callbacks para recebimento de mensagens do grupo, que são adicionadas ao vetor de valores, assim como mensagens recebidas do rei, que são atribuídas ao tiebraker.
  A função start() gera um valor aleatório e inicia um thread para execução do algoritmo. Para cada uma das fases, é executado o multicast para do valor para o grupo, e após o envio da mensagem o processo espera receber as mensagens dos outros processos (primeira rodada da fase). Na segunda rodada, verifica o valor da maioria e calcula as ocorrencias.
  Caso o processo executando a fase seja o rei, ele transmite o valor da maioria no segundo round. Em seguida é verificado se o valor do consenso é satisfatório ou se é necessário o tiebraker do rei.
  Após a execução do número necessário de fases, o valor do consenso é determinado e é impresso para o usuário.
  
Classe MulticastPeer
  -- 
  Classe que encapsula as funções de publicação e recebimento de mensagens, ao usar as funções das classes MulticastReceiver e MulticastPublisher
  
Classe MulticastReceiver
  -- 
  Classe que realiza a implementação concreta do recebimento de mensagens. Nela é feito o cadastro do receiver no grupo multicast, assim como o tratamento do recebimento de mensagens assim como eventual  
  
Classe MulticastPublisher
  -- 
   Classe que realiza a implementação concreta do envio de mensagens no multicast. Na função send(), instancia um DatagramSocket e usa a mensagem como parâmetro para o envio. Em caso de colisão espera um tempo aleatório e retransmite. 
   
   
![](http://reactivex.io/documentation/operators/images/S.PublishSubject.png)
