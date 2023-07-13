# Polimon

## Introdução

- Esse jogo está sendo desenvolvido para um projeto da disciplina de _Laboratório de Programação Orientada a Objetos (MAC0321)_, do _IME-USP_
- Ele tem o objetivo de fornecer um caso de uso prático para os conceitos de orientação a objetos vistos em aula.
- O jogo é uma versão do famoso _Pokémon_. A ideia é que o jogador possa:
  - se movimentar no mapa
  - integrarir com NPCs
  - Colecionar itens
  - Capturar Pokemóns em batalhas.

## Instruções para jogabilidade
- Para se movimentar no mapa, as teclas usadas são **W, A, S, D**
- Para andar de bicicleta, **basta segurar a tecla B**
- Para nadar na raia olímpica, basta apenas se aproximar da área. O jogador nada pelas águas em cima de um Pokémon aquático.
- O mapa do interior do biênio já foi implementado, basta se aproximar da porta do prédio redondo com teto rosado para poder entrar
- Para integir com o NPC, basta ficar próximo dele e apertar a tecla **ENTER**; para passar os dálogos, basta pressionar a tecla **ESPAÇO**

OBS: não foi possível encontrar os mesmos _sprites_ para o _player_ nadando, andando e jogando de bicileta

## Looks and feels
- Para mudar a aparência do jogo, basta escolher o tema desejado no Menu Inicial.
- As opções são _Clássico_ e _Vintage_, sendo que o segundo deixa o jogo com uma aparência mais antiga e o primeiro com cores mais vivas
- As aparências mudam os elementos do mapa e o player.

## Pokémons
- Cada tipo de Pokémon conta com uma área específica e pré-determinada do mapa externo, de modo que será encontrado somente em sua respectiva região. Todos os Pokémons são invisíveis no mapa.
- Atualmente, o jogo conta com suporte a três tipos de Pokémons, conforme exibido nas imagens seguintes, respectivamente dos tipos Normal, Água e Metal:
  - ![Imagem Região Normal](assets/pokemon_normal.png)
  - ![Imagem Região Água](assets/pokemon_agua.png)
  - ![Imagem Região Metal](assets/pokemon_metal.png)
- A cada 20 segundos, os Pokémons de cada região (e, portanto, de cada tipo) são regenerados seguindo uma lógica randômica:
  - Cada tipo possui uma porbabilidade de ser criado no mapa seguindo sua raridade (atualmente: Normal 100%, Água 100%, Metal 80%).
  - Dado que deve ser criado, o Pokémon correspondente a um certo tipo é sorteado aleatoriamente do conjunto de Pokémons desse tipo.
  - Foi usado o padrão Singleton para a criação do gerador de Pokémons a fim de facilitar o processo e compartilhar o mesmo mecanismo de geração pseudorandômica de números para todos os eventos de criação de Pokémon
- Caso haja contato entre o jogador e algum Pokémon, será exibida uma mensagem na saída padrão informando que o jogador encontrou um Pokémon e o nome desse Pokémon. Futuramente, isso será substituído por botões na própria tela do jogo para o jogador decidir o que fará.
## Estratégias dos NPCs e Pokémons
- As estratégias presentes para os NPCs e Pokémons são:
  - Andar aleatoriamente
  - Ficar parado
- Essas estratégias podem ser definidas do menu inicial do jogo (para voltar ao menu, basta pressionar **ESC**)
- Para permitir que diferentes NPCs tenham suas estratégias definidas a partir de uma mesma instância, foi usado o método Prototype.
- Devido às particularidades da implementação dos Pokémons, não foi usado o método Prototype nessa situação, dando preferência para uma simples alteração da estratégia de cada Pokémon.

## Algumas informações sobre a implementação

- Para a confecção do mapa, foi usado o software [Tiled](https://www.mapeditor.org/) e _tilesets_ livres na internet
  - O software exporta um arquivo `.csv` com cada elemento correspondedo ao ID do tile dentro do spritesheet que está na posição correspondente do mapa. Dessa forma, lemos os spritesheets para cada camada com tilesets diferentes e renderizamos o mapa da tela a partir desse `.csv`.

- O jogo foi divido em estados (_Padrão State_), sendo possível transitar entre 4 destes estados na presente implementação
  - `RestScreen`: quando o usuário acabou de entrar no jogo.
  - `Menu`: para acessá-lo novamente basta pressionar **ESC**; no menu é possível escolher a fábrica e a estratégia de NPC e Pokémon desejada
  - `Outside`: estado em que o jogador vai começar o jogo, no mapa da USP.
  - `Biênio`: este é um estado interior; para acessá-lo, basta entrar no prédio redondo com teto rosado (como mostra a imagem abaixo)
    - ![Imagem do biênio](assets/img.png)

- Buscamos sempre usar a injeção de dependências para evitar acoplamento excessivo entre classes.

- O padrão Strategy foi usado em mais 2 contextos:
  - Alterar entre as animações do Player
  - Diferentes comportamentos para os botões do Menu.