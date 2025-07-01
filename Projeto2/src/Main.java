import java.io.*;
import java.util.*;

public class Main {
    static No raiz = null;
    static List<String> todasAsPalavras = new ArrayList<>();
    static Set<String> palavrasUnicas = new HashSet<>();

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 6) {
            System.out.println("\n1- Carregar texto\n2- Estatísticas\n3- Buscar palavra\n4- Exibir palavras\n5- Função extra\n6- Sair");
            opcao = sc.nextInt(); sc.nextLine();

            if (opcao == 1) carregarTexto("musica.txt");
            else if (opcao == 2) estatisticas();
            else if (opcao == 3) {
                System.out.print("Digite a palavra: ");
                String p = sc.nextLine().toLowerCase();
                Palavra resultado = buscar(raiz, p);
                if (resultado != null)
                    System.out.println("Ocorrências: " + resultado.ocorrencias);
                else
                    System.out.println("Palavra não encontrada.");
            }
            else if (opcao == 4) imprimirEmOrdem(raiz);
            else if (opcao == 5) funcaoExtra();
        }

        System.out.println("Encerrando...");
        sc.close();
    }

    static void carregarTexto(String caminho) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;
        while ((linha = br.readLine()) != null) {
            linha = linha.toLowerCase().replaceAll("[^a-z\\s]", "");
            String[] palavras = linha.split("\\s+");
            for (String p : palavras) {
                if (!p.isEmpty()) {
                    todasAsPalavras.add(p);
                    palavrasUnicas.add(p);
                    raiz = inserir(raiz, p);
                }
            }
        }
        br.close();
        System.out.println("TEXTO CARREGADO.");
    }

    static No inserir(No no, String texto) {
        if (no == null) return new No(new Palavra(texto));

        int cmp = texto.compareTo(no.palavra.texto);
        if (cmp < 0) no.esquerda = inserir(no.esquerda, texto);
        else if (cmp > 0) no.direita = inserir(no.direita, texto);
        else no.palavra.ocorrencias++;

        return no;
    }

    static Palavra buscar(No no, String texto) {
        if (no == null) return null;
        int cmp = texto.compareTo(no.palavra.texto);
        if (cmp < 0) return buscar(no.esquerda, texto);
        else if (cmp > 0) return buscar(no.direita, texto);
        else return no.palavra;
    }

    static void imprimirEmOrdem(No no) {
        if (no != null) {
            imprimirEmOrdem(no.esquerda);
            System.out.println(no.palavra.texto + " (" + no.palavra.ocorrencias + ")");
            imprimirEmOrdem(no.direita);
        }
    }

    static void estatisticas() {
        System.out.println("Total de palavras: " + todasAsPalavras.size());
        System.out.println("Palavras distintas: " + palavrasUnicas.size());
        String maisLonga = "";
        for (String p : palavrasUnicas)
            if (p.length() > maisLonga.length()) maisLonga = p;
        System.out.println("Palavra mais longa: " + maisLonga);
    }

    static void funcaoExtra() {
        Map<Character, Integer> letras = new HashMap<>();
        for (String palavra : todasAsPalavras)
            for (char c : palavra.toCharArray())
                letras.put(c, letras.getOrDefault(c, 0) + 1);

        System.out.println("Frequência das letras:");
        for (Map.Entry<Character, Integer> e : letras.entrySet())
            System.out.println(e.getKey() + ": " + e.getValue());
    }
}


// Projeto 2 – Estruturas de Dados
// Alunos: Marcelo Lallo - Gabriel Maia
// Turma: 03S – Campus Alphaville
// Professor: André Rodrigues Oliveira
