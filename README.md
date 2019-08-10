# mapreduce-ep3-client
Um sistema que permite a um programa cliente requisitar, a uma arquitetura Map-Reduce, a criação de um índice invertido de links (semelhante a uma das atividades do PageRank do Google) 

## Definição
Fluxo:
1. O cliente envia uma requisição, contendo uma lista L de URLs, para um nó denominado coordenador. A lista será lida de um arquivo da pasta local do cliente.
2. O coordenador recebe a lista L e a divide em M listas L1, L2, …, LI, …, LM.
3. O coordenador envia cada lista LI a um nó diferente, denominado mapper.
4. Cada mapper I recebe a parte LI, realiza uma atividade (ver 1.1.) e envia o resultado da atividade a um nó denominado reducer.
5. O reducer, somente após receber o resultado de todos os M mappers, gera um índice invertido de links (ver 1.2.) e envia para o cliente.
6. O cliente recebe o índice invertido do reducer e o armazena em um arquivo na mesma pasta local do ponto 1. O arquivo poderá ser visualizado pelo professor.

### A atividade do mapper
Deverá conter, no mínimo, as seguintes operações:
a) Receber em uma requisição uma lista de URLs do coordenador.
b) Baixar e armazenar na memória a página Web de cada URL da lista. Pode usar como exemplo o código em [1].
c) Obter todos os links que existem no conteúdo da página baixada. Para isso, pode usar uma livraria externa, como Jsoup (e.g., [2]) ou utilizar um parser com expressões regulares (e.g., [3]).
d) Criar uma estrutura que contenha, para cada URL da lista, todos os links extraídos dessa URL. Na seção 1.3. poderá observar um exemplo dessa estrutura.
e) Enviar a estrutura ao reducer. 

### A atividade do reducer
Deverá conter, no mínimo, as seguintes operações:
a) Receber a estrutura de cada mapper.
b) Verificar se já recebeu todas as estruturas dos M mappers.
c) Somente após receber as M estruturas, criar o índice invertido de links, como mostrado na Seção 1.3, e ordená-lo de forma decrescente pela quantidade.
d) Enviar o índice ordenado para o cliente.
* Assuma que o cliente conhece o endereço e porta do Coordenador.
* Assuma que o Coordenador conhece o endereço e porta de todos os Mappers.
* Assuma que cada Mapper conhece o endereço e porta do Reducer.
* Assuma que o Reducer não conhece diretamente o endereço e porta do Cliente.
A execução do programa deverá poder ser realizada com um número arbitrário de nós mappers. Na avaliação, será exigida a sua execução com os seguintes nós: 1 cliente, 1 coordenador, 3 (três) mappers e 1 reducer. Cada um desses nós deverá rodar em pelo menos 2 máquinas físicas, com IPs diferentes em 6 processos (JVM) diferentes. Não serão  aceitos trabalhos que executam em somente uma máquina (mesmo sendo diferentes máquinas virtuais).

### Criação do índice invertido de links
A estrutura criada na operação d) do mapper permite, dada uma URL X, conhecer as URLs
que X aponta.
Por exemplo, vamos supor que o mapper1 baixou e processou o www.site1.br, enviando ao reducer a seguinte estrutura:

www.site1.br: {www.site2.br, www.site3.br}

O mapper2 baixou e processou o www.site2.br enviando ao reducer a seguinte estrutura:

www.site2.br: {www.site3.br, www.site4.br}

E o mapper3 baixou e processou o www.site3.br, enviando ao reducer a seguinte estrutura:

www.site3.br: {www.site4.br}

Se quiser ver os apontamentos como um grafo, o exemplo acima define a figura abaixo.
Já o índice invertido criado na operação c) do reducer permite, dada uma URL Y, conhecer que URLs apontam para Y (exatamente o contrário da estrutura do mapper). Para o exemplo acima, o índice invertido de links (ordenado pela quantidade de apontamentos) será:

www.site3.br: {www.site1.br, www.site2.br} // tamanho 2

www.site4.br: {www.site2.br, www.site3.br} // tamanho 2

www.site2.br: {www.site1.br} // tamanho 1

www.site1.br: {} // tamanho 0

Note que o www.site4.br não foi baixado por nenhum mapper, porém apareceu na lista pelos link extraídos no item c).

## Referências
- [Conexão cliente-servidor por TCP](https://www.pegaxchange.com/2017/12/07/simple-tcp-ip-server-client-java/)
