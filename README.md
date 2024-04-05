## Classe FollowMoney:
    Atributos:
        - linhaAtual: inteiro
        - colunaAtual: inteiro

## Método principal (main):
        - Instanciar um objeto da classe FollowMoney
        - Obter o tempo inicial
        - Chamar o método executar
        - Obter o tempo final
        - Calcular o tempo total
        - Imprimir o tempo total

## Método executar:
        - Tentar:
            - Abrir o arquivo de mapa
            - Ler as dimensões do mapa
            - Criar uma matriz para armazenar o mapa
            - Ler o mapa linha por linha e armazenar na matriz
            - Encontrar a primeira linha que contém um caractere na primeira posição
            - Inicializar uma lista para armazenar o dinheiro recuperado
            - Chamar o método seguirTrilha
            - Imprimir o somatório do dinheiro recuperado
        - Capturar e tratar possíveis erros de leitura do arquivo

## Método seguirTrilha:
        - Inicializar a direção atual como "direita"
        - Inicializar o total de dinheiro como zero
        - Enquanto não chegar ao fim do mapa (#):
            - Obter o caractere atual do mapa
            - Se o caractere for um número:
                - Adicionar o valor do número à lista de dinheiro recuperado
                - Somar o valor do número ao total de dinheiro
            - Atualizar a direção com base no caractere atual
            - Mover para a próxima posição de acordo com a direção atual
            - Verificar se a nova posição está dentro dos limites do mapa
        - Imprimir o total de dinheiro recuperado

## Método atualizarDirecao:
        - Receber o caractere atual do mapa e a direção atual
        - Se o caractere for "/", atualizar a direção conforme regras especificadas
        - Se o caractere for "\", atualizar a direção conforme regras especificadas
        - Retornar a direção atualizada

## Método moverPosicao:
        - Receber a linha e coluna atuais e a direção
        - Atualizar a linha e coluna de acordo com a direção
        - Retornar a nova posição

## Método somatorio:
        - Receber a lista de números
        - Somar todos os números da lista
        - Retornar o resultado da soma


pseudocodigo: 

    Função principal():
        // Inicialização
        linhaAtual := -1
        colunaAtual := 0
        tempoInicial := obterTempoAtual()

        // Execução
        mapa := lerMapa("mapa.txt")
        dinheiroRecuperado := seguirTrilha(mapa)
        
        // Resultado
        imprimirResultado(dinheiroRecuperado)

        // Tempo total
        tempoFinal := obterTempoAtual()
        tempoTotal := calcularTempoTotal(tempoInicial, tempoFinal)
        imprimirTempoTotal(tempoTotal)

    Função lerMapa(caminhoArquivo):
        // Abre o arquivo e lê as dimensões do mapa
        altura, largura := lerDimensoesMapa(caminhoArquivo)

        // Cria a matriz para armazenar o mapa
        mapa := criarMatriz(altura, largura)

        // Lê o mapa linha por linha e armazena na matriz
        para cada linha no arquivo:
            preencherMatriz(mapa, linha)

        retornar mapa

    Função seguirTrilha(mapa):
        // Inicialização
        direcaoAtual := "direita"
        dinheiroRecuperado := listaVazia()

        // Encontra a primeira linha que contém um caractere na primeira posição
        linhaAtual := encontrarPrimeiraLinha(mapa)

        // Enquanto não chegar ao fim do mapa:
        enquanto mapa[linhaAtual][colunaAtual] != '#':
            simbolo := mapa[linhaAtual][colunaAtual]
            
            // Se o símbolo atual for um número, coleta o dinheiro
            se simbolo for um número:
                valor := converterParaNumero(simbolo)
                adicionarValorLista(dinheiroRecuperado, valor)

            // Atualiza a direção conforme o símbolo atual
            direcaoAtual := atualizarDirecao(mapa, direcaoAtual)

            // Move para a próxima posição
            novaLinha, novaColuna := moverPosicao(linhaAtual, colunaAtual, direcaoAtual)
            linhaAtual := novaLinha
            colunaAtual := novaColuna

        retornar dinheiroRecuperado

    Função atualizarDirecao(mapa, direcaoAtual):
        // Verifica o símbolo atual e atualiza a direção conforme as regras
        simbolo := mapa[linhaAtual][colunaAtual]

        se simbolo for '/':
            direcaoAtual := atualizarDirecaoDiagonal(direcaoAtual, 'direita')
        senão se simbolo for '\':
            direcaoAtual := atualizarDirecaoDiagonal(direcaoAtual, 'esquerda')

        retornar direcaoAtual

    Função atualizarDirecaoDiagonal(direcaoAtual, novaDirecao):
        // Atualiza a direção diagonal conforme as regras
        se direcaoAtual for 'direita' e novaDirecao for 'direita':
            retornar 'cima'
        senão se direcaoAtual for 'direita' e novaDirecao for 'esquerda':
            retornar 'baixo'
        senão se direcaoAtual for 'esquerda' e novaDirecao for 'direita':
            retornar 'baixo'
        senão se direcaoAtual for 'esquerda' e novaDirecao for 'esquerda':
            retornar 'cima'

    Função moverPosicao(linhaAtual, colunaAtual, direcaoAtual):
        // Move para a próxima posição conforme a direção atual
        se direcaoAtual for 'cima':
            linhaAtual := linhaAtual - 1
        senão se direcaoAtual for 'baixo':
            linhaAtual := linhaAtual + 1
        senão se direcaoAtual for 'esquerda':
            colunaAtual := colunaAtual - 1
        senão se direcaoAtual for 'direita':
            colunaAtual := colunaAtual + 1

        retornar linhaAtual, colunaAtual

    Função imprimirResultado(dinheiroRecuperado):
        // Imprime o somatório do dinheiro recuperado
        somatorio := somarValoresLista(dinheiroRecuperado)
        imprimir("Somatório do dinheiro recuperado: ", somatorio)

    Função calcularTempoTotal(tempoInicial, tempoFinal):
        // Calcula o tempo total em milissegundos
        retornar tempoFinal - tempoInicial

