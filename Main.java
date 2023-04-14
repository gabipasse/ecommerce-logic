import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner new_scanner = new Scanner(System.in);

        Estoque new_estoque = new Estoque();

        new_estoque.cadastrarProduto("Nome2", 3, "tamanho", "descricao", 3); //dummy caso realize login apenas como cliente

        int continuar = 1;

        int acao;

        while (continuar == 1) {

            System.out.println("1- Acessar login cliente. 2- Acessar login colaborador. 3- Deslogar. 4- Navegar pelos produtos (Cliente). 5- Administrar estoque (Colaborador). 6- Administrar pedidos (Colaborador). 7- Checar confirmacoes (Cliente). 8- Fechar programa. ");

            acao = new_scanner.nextInt();

            switch (acao) {
                case 1 -> {
                    if (new_estoque.cpfLogado != "Cliente") { //qndo vou logar em uma conta criada em açoes anteriores, mesmo assim cai 2x pra logincliente; deve rolar o mesmo pra colaborador

                        new_estoque.loginCliente();

                    }
                    new_estoque.loginCliente();
                }
                case 2 -> {
                    if (new_estoque.cpfLogado != "Colaborador") {

                        new_estoque.loginColaborador();

                    }
                    new_estoque.loginColaborador();
                }
                case 3 -> new_estoque.deslogar();
                case 4 -> new_estoque.navegar();
                case 5 -> new_estoque.estoqueSuper();
                case 6 -> new_estoque.gerenciarPedidos();
                case 7 -> new_estoque.checarConfimacoesCliente();
                case 8 -> continuar = 0;
            }

        }

    }
}
