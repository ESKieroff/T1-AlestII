import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FollowMoneyOptimized {
    private int linhaAtual = -1;
    private int colunaAtual = 0;

    public static void main(String[] args) {
        FollowMoneyOptimized seguirTrilha = new FollowMoneyOptimized();
        long startTime = System.currentTimeMillis(); // Obtém o tempo inicial

        seguirTrilha.executar();

        long endTime = System.currentTimeMillis(); // Obtém o tempo final
        long totalTime = endTime - startTime; // Calcula o tempo total em milissegundos
        System.out.println("Tempo total: " + totalTime + " milissegundos");
    }

    public void executar() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("caso2000.txt"));

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
            List<Integer> dinheiroRecuperado = new ArrayList<>();
            seguirTrilha(mapa, altura, largura, dinheiroRecuperado);

            // Imprime o resultado final
            // System.out.println("\nSomatório do dinheiro recuperado: " +
            // somatorio(dinheiroRecuperado));

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void seguirTrilha(char[][] mapa, int altura, int largura, List<Integer> dinheiroRecuperado) {
        String direcaoAtual = "direita"; // Começa indo para a direita
        int totalDinheiro = 0;

        while (mapa[linhaAtual][colunaAtual] != '#') {
            char simbolo = mapa[linhaAtual][colunaAtual];

            if (Character.isDigit(simbolo)) {
                int valor = Character.getNumericValue(simbolo);
                dinheiroRecuperado.add(valor);
                totalDinheiro += valor;

                // System.out.println("DINHEIRO RECUPERADO NO CAMINHO: " + valor);
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
        System.out.println("\nSomatório do dinheiro recuperado: " + totalDinheiro);
    }

    public int coletarDinheiro(char[][] mapa, int altura, int largura, String direcao) {
        StringBuilder dinheiro = new StringBuilder();
        int quantidadeDinheiro = 0;

        // Define os incrementos nas coordenadas x e y de acordo com a direcao
        int dx = 0;
        int dy = 0;

        if (direcao.equals("cima")) {
            dx = -1;
        } else if (direcao.equals("baixo")) {
            dx = 1;
        } else if (direcao.equals("esquerda")) {
            dy = -1;
        } else if (direcao.equals("direita")) {
            dy = 1;
        }

        // Coleta os números na direcao especificada
        int x = linhaAtual;
        int y = colunaAtual;
        while (x >= 0 && x < altura && y >= 0 && y < largura && (Character.isDigit(mapa[x][y]) || mapa[x][y] == '#')) {
            // Verifica se o caractere é um número e adiciona à string
            if (Character.isDigit(mapa[x][y])) {
                dinheiro.append(mapa[x][y]);
            } else if (mapa[x][y] == '#') {
                break; // Para quando encontrar o caractere de término
            }
            // Avança para a próxima posição na direcao especificada
            x += dx;
            y += dy;
            linhaAtual = x; // Atualiza a linhaAtual com o valor de x
            colunaAtual = y; // Atualiza a colunaAtual com o valor de y
        }
        if (dinheiro.length() > 0) {
            quantidadeDinheiro = Integer.parseInt(dinheiro.toString()); // Converte a string para número inteiro
        }
        return quantidadeDinheiro;
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

    public static int somatorio(List<Integer> list) {
        int sum = 0;
        for (int num : list) {
            sum += num;
        }
        return sum;
    }
}
