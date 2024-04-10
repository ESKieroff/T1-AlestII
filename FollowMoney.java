import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FollowMoney {
    // Variáveis para controlar a posição atual na matriz do mapa
    private int linhaAtual = -1;
    private int colunaAtual = 0;
    // Variável para armazenar o nome do caso de teste atual
    private String casoTeste;

    // Método principal
    public static void main(String[] args) {
        // Cria uma instância da classe FollowMoney
        FollowMoney seguirTrilha = new FollowMoney();
        // Executa os casos de teste
        seguirTrilha.executarCasosTeste();
    }

    // Método para executar os casos de teste
    public void executarCasosTeste() {
        // Obtém a lista de arquivos no diretório "arquivos"
        File diretorio = new File("arquivos");
        File[] arquivos = diretorio.listFiles();

        // Verifica se a lista de arquivos não está vazia
        if (arquivos != null) {
            // Itera sobre cada arquivo na lista
            for (File arquivo : arquivos) {
                // Verifica se o arquivo é um arquivo regular, começa com "caso" e termina com
                // ".txt"
                if (arquivo.isFile() && arquivo.getName().startsWith("caso") && arquivo.getName().endsWith(".txt")) {
                    // Obtém o caminho completo do arquivo
                    casoTeste = arquivo.getAbsolutePath();
                    // Imprime o tamanho do caso de teste atual (nome do arquivo sem extensão)
                    System.out.println(
                            "\nTamanho: " + arquivo.getName().substring(0, arquivo.getName().lastIndexOf('.')));
                    // Obtém o tempo de início da execução do caso de teste
                    long startTime = System.currentTimeMillis();
                    // Processa o caso de teste
                    processarCasoTeste(casoTeste);
                    // Obtém o tempo de término da execução do caso de teste
                    long endTime = System.currentTimeMillis();
                    // Calcula o tempo total de execução do caso de teste
                    long totalTime = endTime - startTime;
                    // Imprime o tempo total de execução do caso de teste
                    System.out.println("Tempo: " + totalTime + " milissegundos" + "\n-----");
                }
            }
        } else {
            System.err.println("Diretório não encontrado ou não é um diretório válido.");
        }
    }

    // Método para processar um caso de teste
    public void processarCasoTeste(String caminhoArquivo) {
        // Reinicializa as variáveis de controle de posição na matriz do mapa
        linhaAtual = -1;
        colunaAtual = 0;

        try {
            // Cria um BufferedReader para ler o arquivo de entrada
            BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));

            // Lê as dimensões do mapa (altura e largura)
            String[] dimensoes = br.readLine().split(" ");
            int altura = Integer.parseInt(dimensoes[0]);
            int largura = Integer.parseInt(dimensoes[1]);

            // Cria a matriz para armazenar o mapa
            char[][] mapa = new char[altura][largura];

            // Lê o mapa linha por linha e armazena na matriz
            for (int i = 0; i < altura; i++) {
                String linha = br.readLine();
                for (int j = 0; j < largura; j++) {
                    mapa[i][j] = (j < linha.length()) ? linha.charAt(j) : ' ';
                }
            }

            // Fecha o BufferedReader
            br.close();

            // Encontra a primeira linha que contém um caractere na primeira posição
            for (int i = 0; i < altura; i++) {
                if (mapa[i][0] != ' ') {
                    linhaAtual = i;
                    break;
                }
            }

            // Chama o método para seguir a trilha e calcular a quantidade de dinheiro
            // recuperado
            int totalDinheiro = 0;
            String direcaoAtual = "direita"; // Começa indo para a direita
            List<Integer> dinheiroRecuperado = new ArrayList<>();

            // Lista temporária para armazenar os dígitos consecutivos
            StringBuilder valorTemp = new StringBuilder();

            // Loop para percorrer o mapa e seguir a trilha
            while (mapa[linhaAtual][colunaAtual] != '#') {
                char simbolo = mapa[linhaAtual][colunaAtual];

                // Verifica se o caractere atual é um dígito
                if (Character.isDigit(simbolo)) {
                    // Adiciona o dígito à lista temporária
                    valorTemp.append(simbolo);
                } else {
                    // Se não for um dígito, converte a lista temporária em um número inteiro e
                    // adiciona à lista de dinheiro recuperado
                    if (valorTemp.length() > 0) {
                        int valor = Integer.parseInt(valorTemp.toString());
                        dinheiroRecuperado.add(valor);
                        totalDinheiro += valor;
                        valorTemp.setLength(0); // Limpa a lista temporária
                    }
                }

                // Atualiza a direção
                direcaoAtual = atualizarDirecao(mapa, direcaoAtual);

                // Atualiza as coordenadas usando o método moverPosicao
                int[] novaPosicao = moverPosicao(linhaAtual, colunaAtual, direcaoAtual);
                linhaAtual = novaPosicao[0];
                colunaAtual = novaPosicao[1];

                // Verifica se a nova posição está dentro dos limites da matriz
                if (linhaAtual < 0 || linhaAtual >= altura || colunaAtual < 0 || colunaAtual >= largura) {
                    break; // Sai do loop caso a nova posição seja inválida
                }
            }

            // Imprime o resultado final (soma do dinheiro recuperado)
            System.out.println("--> Soma: " + totalDinheiro);

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    // Método para atualizar a direção conforme o símbolo atual na matriz
    public String atualizarDirecao(char[][] mapa, String direcaoAtual) {
        char simbolo = mapa[linhaAtual][colunaAtual];
        if (simbolo == '/') {
            switch (direcaoAtual) {
                case "direita":
                    return "cima";
                case "esquerda":
                    return "baixo";
                case "cima":
                    return "direita";
                case "baixo":
                    return "esquerda";
            }
        } else if (simbolo == '\\') {
            switch (direcaoAtual) {
                case "direita":
                    return "baixo";
                case "esquerda":
                    return "cima";
                case "cima":
                    return "esquerda";
                case "baixo":
                    return "direita";
            }
        }
        return direcaoAtual; // Retorna a direção atual se não houver mudança
    }

    // Método para calcular a nova posição baseada na direção atual
    public static int[] moverPosicao(int linha, int coluna, String direcao) {
        switch (direcao) {
            case "direita":
                coluna++;
                break;
            case "esquerda":
                coluna--;
                break;
            case "cima":
                linha--;
                break;
            case "baixo":
                linha++;
                break;
        }
        return new int[] { linha, coluna };
    }

}
