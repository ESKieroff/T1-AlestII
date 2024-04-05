import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FollowMoney {
    private int linhaAtual = -1;
    private int colunaAtual = 0;

    public static void main(String[] args) {
        FollowMoney seguirTrilha = new FollowMoney();
        long startTime = System.currentTimeMillis(); // Obtém o tempo inicial

        seguirTrilha.executar();

        long endTime = System.currentTimeMillis(); // Obtém o tempo final
        long totalTime = endTime - startTime; // Calcula o tempo total em milissegundos
        System.out.println("Tempo total: " + totalTime + " milissegundos");
    }

    public void executar() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("caso500.txt"));

            // Lê as dimensões do mapa
            String[] dimensoes = br.readLine().split(" ");
            int altura = Integer.parseInt(dimensoes[0]);
            int largura = Integer.parseInt(dimensoes[1]);

            // Cria a matriz para armazenar o mapa
            char[][] mapa = new char[altura][largura];

            // Lê o mapa linha por linha e armazena na matriz
            for (int i = 0; i < altura; i++) {
                String linha = br.readLine();
                for (int j = 0; j < largura; j++) {
                    mapa[i][j] = (j < linha.length()) ? linha.charAt(j) : ' '; // Evita índice fora dos limites
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

            while (mapa[linhaAtual][colunaAtual] != '#') {
                char simbolo = mapa[linhaAtual][colunaAtual];

                if (Character.isDigit(simbolo)) {
                    int valor = Character.getNumericValue(simbolo);
                    dinheiroRecuperado.add(valor);
                    totalDinheiro += valor;
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

            // Imprime o resultado final
            System.out.println("\nSomatório do dinheiro recuperado: " + totalDinheiro);

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

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
