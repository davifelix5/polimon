# Polimon

## Introdução

- Esse jogo está sendo desenvolvido para um projeto da disciplina de _Laboratório de Programação Orientada a Objetos (MAC0321)_, do _IME-USP_
- Ele tem o objetivo de forecener um caso de uso prático para os conceitos de orientação a objetos vistos em aula.
- O jogo é uma vesão do famoso _Pokémon_. A ideia é que o jogador possa:
  - se movimentar no mapa
  - integrarir com NPCs
  - Colecionar itens
  - Caputurar Pokemóns em batalhas.

## Instruções para jogabilidade
- Por enquanto, as únicas coisas implementadas foram a possibilidade de o _player_ se movimentar de diferentes maneiras ao longo do mapa da USP:
  - Para se movimentar no mapa, as teclas usadas são **W, A, S, D**
  - Para andas de bicicleta, **basta segurar a tecla B**
  - Para nadar na raia olímpica, basta apenas se aproximar da área. O jogador nada pelas águas em cima de um Pokémon aquático.
- O mapa do interior do biênio já foi implementado, mas ainda não é possível entrar no prédio pelo jogo. Caso queira testar o mapa do biênio, basta mudar o estado de `StateID.Outisde` `para StateID.Bienio` no arquivo `src/game/buttons/PlayButtonStrategy`.

OBS: não foi possível encontrar os mesmos _sprites_ para o _player_ nadando, andando e jogando de bicileta

## Algumas informações sobre a implementação

- Para a confecção do mapa, foi usado o software [Tiled](https://www.mapeditor.org/) e _tilesets_ livres na internet
  - O software exporta um arquivo `.csv` com cada elemento correspondedo ao ID do tile dentro do spritesheet que está na posição correspondente do mapa. Dessa forma, lemos os spritesheets para cada camada com tilesets diferentes e renderizamos o mapa da tela a partir desse `.csv`.

- O jogo foi divido em estados (_Padrão State_), sendo possível transitar entre 3 destes estados na presente implementação
  - `RestScreen`: quando o usuário acabou de entrar o jogo.
  - `Menu`: por enquanto, só as opções de sair e jogar funcionam.
  - `Outsite`: estado em que o jogador vai começar o jogo, no mapa da USP.

OBS: também há o estado `Bienio`, como foi dito acima

- Buscamos sempre usar a injeção de dependências para evitar acoplamento excessivo entre classes.

- O padrão Strategy foi usado em 2 contextos:
  - Alterar entre as animações do Player
  - Diferentes comportamentos para os botões do Menu.