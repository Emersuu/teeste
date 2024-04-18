import java.io.*;
import java.util.*;

class Item {
    String jogos;
    String categoria;
    double avaliacao;

    public Item(String jogos, String categoria, double avaliacao) {
        this.jogos = jogos;
        this.categoria = categoria;
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return jogos + "\t/" + categoria + "\t/" + avaliacao;
    }
}

public class JogoApp {
    private static List<Item> jogosList = new ArrayList<>();
    private static final String arquivoEntrada = "JogosDesordenados.csv";

    public static void main(String[] args) {
        boolean continuar = true;
        Scanner scanner = new Scanner(System.in);

        while (continuar) {
            System.out.println("[1] Ler arquivo");
            System.out.println("[2] Ordenar por categoria");
            System.out.println("[3] Ordenar por avaliação");
            System.out.println("[4] Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    lerArquivo();
                    break;
                case 2:
                    ordenarPorCategoria();
                    break;
                case 3:
                    ordenarPorAvaliacao();
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    private static void lerArquivo() {
        try (Scanner leitor = new Scanner(new File(arquivoEntrada))) {
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                String[] partes = linha.split("\t/");
                String jogo = partes[0];
                String categoria = partes[1].substring(1);
                double avaliacao = Double.parseDouble(partes[2].substring(1));

                jogosList.add(new Item(jogo, categoria, avaliacao));
            }
            System.out.println("Arquivo lido com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private static void ordenarPorCategoria() {
        Collections.sort(jogosList, Comparator.comparing(item -> item.categoria));
        salvarArquivoOrdenado("JogosOrdenadosporCategoria.csv");
        System.out.println("Jogos ordenados por categoria e salvos em JogosOrdenadosporCategoria.csv");
    }

    private static void ordenarPorAvaliacao() {
        Collections.sort(jogosList, (item1, item2) -> Double.compare(item2.avaliacao, item1.avaliacao));
        salvarArquivoOrdenado("JogosOrdenadosporAvaliacao.csv");
        System.out.println("Jogos ordenados por avaliação e salvos em JogosOrdenadosporAvaliacao.csv");
    }

    private static void salvarArquivoOrdenado(String nomeArquivo) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (Item item : jogosList) {
                escritor.println(item);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
}