import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

class Estoque{

    static class Colaborador{

        Object cpf;

        Object senha;

        Colaborador next;

        public Colaborador(String cpf, String senha){

            this.cpf = cpf;

            this.senha = senha;

        }

    }

    static class Cliente{

        class Confirmacao{

            String mensagemConfirmacao;

            Confirmacao next;

            public Confirmacao(String mensagemConfirmacao){

                this.mensagemConfirmacao = mensagemConfirmacao;

            }

        }

        Confirmacao confirmacaoBase;

        Confirmacao confirmacaoAtual;

        public void gerarConfirmacao(String mensagemConfirmacao){

            Confirmacao new_confirmacao = new Confirmacao(mensagemConfirmacao);

            if(confirmacaoBase == null){

                confirmacaoBase = new_confirmacao;

            } else {

                confirmacaoAtual.next = new_confirmacao;

            }

            confirmacaoAtual = new_confirmacao;

        }

        public void lerConfirmacoes(){

            Confirmacao confirmacaoCheck = confirmacaoBase;

            while(confirmacaoCheck != null){

                System.out.println(confirmacaoCheck.mensagemConfirmacao);

                confirmacaoCheck = confirmacaoCheck.next;

            }

        }

        Object cpf;

        Object senha;

        Cliente next;

        class produtoCarrinho{ //organizar melhor

            Produto produtoInserido;

            Object qtd;

            produtoCarrinho next;

            public produtoCarrinho(Produto nome, Object qtd){

                this.produtoInserido = nome;

                this.qtd = qtd;
            }

            public Object[] dados(){

                return new Object[]{this.produtoInserido.nome, this.qtd};

            }

        }

        produtoCarrinho pcAtual;

        produtoCarrinho pcBase;

        public void inserirCarrinho(Produto produtoInserido, Object qtd){

            produtoCarrinho new_pc = new produtoCarrinho(produtoInserido, qtd);

            if(pcBase == null){

                pcBase = new_pc;

            } else {

                pcAtual.next = new_pc;

            }

            pcAtual = new_pc;

        }

        public Cliente(String cpf, String senha){

            this.cpf = cpf;

            this.senha = senha;

        }

        public void dados(){

            produtoCarrinho pcCheck = pcBase;

            while(pcCheck != null){

                System.out.println(Arrays.toString(pcCheck.dados()));

                pcCheck = pcCheck.next;

            }

        }


        public boolean checarNoCarrinho(Produto produto){

            produtoCarrinho pcCheck = pcBase;

            if(pcCheck == null){

                return false;

            }

            while(pcCheck != null){

                if(pcCheck.produtoInserido == produto){

                    return true;

                }

                pcCheck = pcCheck.next;

            }

            return false;

        }

        public void modificarQtdPC(Produto produto, int qtd){

            System.out.println("CAIU MODIFICAR");

            produtoCarrinho pcCheck = pcBase;

            while(pcCheck != null){

                if(pcCheck.produtoInserido == produto){

                    System.out.printf("Qtd antes da modificacao:"+ pcCheck.qtd);

                    pcCheck.qtd = (int)pcCheck.qtd + qtd; //colocar pra operacao tbm adicionar ou remover?

                    System.out.printf("Qtd apos da modificacao:"+ pcCheck.qtd);

                }

                pcCheck = pcCheck.next;

            }

        }

    }

    static class Produto{

        Object nome;

        Object preco;

        Object tamanho;

        Object descricao;

        Object qtd;

        Produto next;

        Produto previous;

        public Produto(Object nome, Object preco, Object tamanho, Object descricao, Object qtd){

            this.nome = nome;

            this.preco = preco;

            this.tamanho = tamanho;

            this.descricao = descricao;

            this.qtd = qtd;

        }

        public Object[] dados(){

            return new Object[]{this.nome, this.preco, this.tamanho, this.descricao, this.qtd};

        }//metodo para retornar todos os dados sobre determinado produto; utilizar array pro return e printar qndo necessario;

        public void modificarQtd(int qtdModificar){

            qtd = (int)qtd - qtdModificar;

        }

    }

    static class Pedido{

        Cliente fezEssePedido;

        itemPedido ipBase;

        itemPedido ipAtual;

        Pedido next;

        String informacoesPagamento;

        String informacoesEnvio;

        class itemPedido{

            itemPedido next;

            int preco;

            Cliente.produtoCarrinho pc;

            public itemPedido(int preco, Cliente.produtoCarrinho pc){

                this.pc = pc;

                this.preco = preco;

            }

            public Object[] dados(){

                return new Object[]{this.pc.produtoInserido.nome, this.preco, this.pc.qtd};

            }

        }

        public void inserirIp(int preco, Cliente cliente){

            Scanner new_scanner = new Scanner(System.in);

            itemPedido new_ip = new itemPedido(preco, cliente.pcBase);

            if(ipBase == null){

                ipBase = new_ip;

            } else {

                ipAtual.next = new_ip;

            }

            ipAtual = new_ip;

            System.out.println("Insira suas informacoes de pagamento:");

            this.informacoesPagamento = new_scanner.next();

            System.out.println("Insira suas informacoes de envio");

            this.informacoesEnvio = new_scanner.next();

        }

        public void visualizarIps(){

            itemPedido ipCheck = ipBase;

            while(ipCheck != null){

                System.out.println(Arrays.toString(ipCheck.dados()));

                ipCheck = ipCheck.next;

            }

        }

    }

    Pedido pedidoBase;

    Pedido pedidoAtual;

    String superKey = "123"; //utilizado para cadastrar colaborador

    Produto produtoAtual;

    Produto produtoBase;

    Cliente clienteAtual;

    Cliente clienteBase;

    Colaborador colaboradorAtual;

    Colaborador colaboradorBase;

    Object cpfLogado; //cliente ou colaborador, permitindo jogar as mudancas e tal pra cada corno

    Object permissaoLogado;

    public int deslogar(){

        if(cpfLogado == null){

            System.out.println("Nenhuma conta logada no momento.");

            return 1;

        }

        cpfLogado = null;

        permissaoLogado = null;

        return 0;

    }
    public void loginCliente(){

        Scanner new_scanner = new Scanner(System.in);

        System.out.println("Deseja logar(1) ou criar uma conta(2)?");

        int resp = new_scanner.nextInt();

        if(resp == 1){

            System.out.println("Insira seu cpf");

            String cpfLogin = new_scanner.next();

            System.out.println("Insira sua senha");

            String senhaLogin = new_scanner.next();

            if(!checkLoginCliente(cpfLogin, senhaLogin)){

                System.out.println("Nao possui cadastro.");

                loginCliente();

            }

        } else if (resp == 2){

            System.out.println("Insira seu cpf");

            String cpfCriar = new_scanner.next();

            System.out.println("Insira a senha desejada");

            String senhaCriar = new_scanner.next();

            criarCliente(cpfCriar, senhaCriar);

        } else {

            System.out.println("Comando invalido.");

            loginCliente();

        }

    }

    public void criarCliente(String cpf, String senha){

        Cliente new_cliente = new Cliente(cpf, senha);

        if(clienteBase == null){

            clienteBase = new_cliente;

        } else {

            clienteAtual.next = new_cliente;

        }



        clienteAtual = new_cliente;

    }

    public boolean checkLoginCliente(String cpf, String senha){

        Cliente clienteCheck = clienteBase;


        while(clienteCheck != null){

            if(Objects.equals(clienteCheck.cpf, cpf) && Objects.equals(clienteCheck.senha, senha)){

                cpfLogado = clienteCheck;

                permissaoLogado = "Cliente";

                return true;

            }

            clienteCheck = clienteCheck.next;

        }

        return false;

    }

    //login colaborador

    public int loginColaborador(){

        Scanner new_scanner = new Scanner(System.in);

        System.out.println("Deseja logar(1) ou criar uma conta(2)?");

        int resp = new_scanner.nextInt();

        if(resp == 1){

            System.out.println("Insira seu cpf");

            String cpfLogin = new_scanner.next();

            System.out.println("Insira sua senha");

            String senhaLogin = new_scanner.next();

            if(!checkLoginColaborador(cpfLogin, senhaLogin)){

                System.out.println("Nao possui cadastro.");

                loginColaborador();

            }

        } else if (resp == 2){

            System.out.println("Insira a chave de admin:");

            if(!Objects.equals(new_scanner.next(), "123")){

                return 1;

            }

            System.out.println("Insira seu cpf");

            String cpfCriar = new_scanner.next();

            System.out.println("Insira a senha desejada");

            String senhaCriar = new_scanner.next();

            criarColaborador(cpfCriar, senhaCriar);

        } else {

            System.out.println("Comando invalido.");

            loginColaborador();

        }

        return 0;

    }

    public void criarColaborador(String cpf, String senha){

        Colaborador new_colaborador = new Colaborador(cpf, senha);

        if(colaboradorBase == null){

            colaboradorBase = new_colaborador;

        } else {

            colaboradorAtual.next = new_colaborador;

        }



        colaboradorAtual = new_colaborador;

    }

    public boolean checkLoginColaborador(String cpf, String senha){

        Colaborador colaboradorCheck = colaboradorBase;


        while(colaboradorCheck != null){

            if(Objects.equals(colaboradorCheck.cpf, cpf) && Objects.equals(colaboradorCheck.senha, senha)){

                cpfLogado = colaboradorCheck;

                permissaoLogado = "Colaborador";

                return true;

            }

            colaboradorCheck = colaboradorCheck.next;

        }

        return false;

    }

    //metodo para modificar qtd de determinado item, inserir itens novos, ou excluir itens; além de visualizar itens e qtd de cada item presente no estoque (if colaborador)

    public int estoqueSuper(){

        Scanner new_scanner = new Scanner(System.in);

        String resposta = "";

        if(permissaoLogado != "Colaborador"){

            System.out.println("Acesso negado.");

            return 1;

        }

        Produto estoqueCheck = produtoBase;

        while(estoqueCheck != null){

            System.out.println(Arrays.toString(estoqueCheck.dados()));

            estoqueCheck = estoqueCheck.next;

        }

        while(!Objects.equals(resposta, "1") && !Objects.equals(resposta, "2")){

            System.out.println("Deseja modificar o estoque? sim(1) nao(2)");

            resposta = new_scanner.next();

        }

        if(resposta.equals("1")){ //acho que faltou a parte de inserir que n existem ainda em estoque

            System.out.println("Insira o nome do produto em questao:");

            String nomeProduto = new_scanner.next();

            System.out.println(checarExistenciaProduto(nomeProduto));

            if(checarExistenciaProduto(nomeProduto) != null){

                Produto produtoChecado = (Produto) checarExistenciaProduto(nomeProduto);

                String operacao = "";

                while(!Objects.equals(operacao, "1") && !Objects.equals(operacao, "2")){

                    System.out.println("Deseja adicionar(1) ou remover(2) unidades desse produto do estoque?"); //ajuda 1: pq ta pedindo input 2x aqui?

                    operacao = new_scanner.next();

                }

                if(Objects.equals(operacao, "1")){

                    System.out.println("Quantas unidades deseja adicionar?");

                    produtoChecado.qtd = (int)produtoChecado.qtd + new_scanner.nextInt();

                } else if(Objects.equals(operacao, "2")){

                    System.out.println("Quantas unidades deseja remover?");

                    produtoChecado.qtd = Math.max((int) produtoChecado.qtd - new_scanner.nextInt(), 0);

                }

            }

        }

        return 0;

    }

    public int gerenciarPedidos(){

        Scanner new_scaner = new Scanner(System.in);

        if(permissaoLogado != "Colaborador"){

            System.out.println("Acesso negado.");

            return 1;

        }

        Pedido pedidoCheck = pedidoBase;

        while(pedidoCheck != null){

            System.out.println("VASCO");

            pedidoCheck.visualizarIps();

            System.out.println("Deseja confirmar esse pedido?");

            if(new_scaner.next().equalsIgnoreCase("sim")){

                Pedido.itemPedido ipCheck = pedidoCheck.ipBase;

                if((int)ipCheck.pc.produtoInserido.qtd < (int)ipCheck.pc.qtd){

                    System.out.println("Estoque atual não supre esse pedido!");

                }

                System.out.println();

                while(ipCheck != null){

                    System.out.println("DADOS:::" + Arrays.toString(ipCheck.pc.produtoInserido.dados()) + pedidoCheck.informacoesEnvio + pedidoCheck.informacoesPagamento);

                    ipCheck.pc.produtoInserido.modificarQtd((int)pedidoCheck.ipBase.pc.qtd);

                    ipCheck = ipCheck.next;

                    System.out.println("Insira as informacoes de envio referentes a esse pedido:");

                    pedidoCheck.fezEssePedido.gerarConfirmacao(new_scaner.next()); //como acessar quem fez o pedido

                }

            }

            pedidoCheck = pedidoCheck.next;

        }

        return 0;

    }

    public void cadastrarProduto(String nome, Object preco, String tamanho, String descricao, Object qtd){ //check pra login, só permite se tiver logado como colaborador

        Produto new_produto = new Produto(nome, preco, tamanho, descricao, qtd);

        if(produtoBase == null){

            produtoBase = new_produto;

        } else {

            produtoAtual.next = new_produto;

            new_produto.previous = produtoAtual;

        }

        produtoAtual = new_produto;

    }

    public Object checarExistenciaProduto(String nome){

        Produto estoquecheck = produtoBase;

        while(estoquecheck != null){

            if(estoquecheck.nome.toString().equals(nome)){

                return estoquecheck;

            }

            estoquecheck = estoquecheck.next;

        }

        return null;

    }

    public int navegar(){

        Scanner new_scanner = new Scanner(System.in);

        String continuarNavegando = "1";

        if(produtoBase == null){

            System.out.println("Nenhum produto disponivel.");

            return 1;

        } else if(cpfLogado == null){

            System.out.println("Realize o login!");

            return 1;

        } else if(permissaoLogado == "Colaborador"){

            System.out.println("Acao disponivel apenas para clientes.");

        }

        Produto produtoNavegar = produtoBase;

        while(continuarNavegando.equals("1")){

            navegarInterno(produtoNavegar);

            System.out.println("(1) continuar navegacao ou (2) confirmar saida da navegacao");

            continuarNavegando = new_scanner.next();

        }

        return 0;

    }

    public int navegarInterno(Produto produtoNavegar){

        Scanner new_scanner = new Scanner(System.in);

        String acao;

        System.out.println(Arrays.toString(produtoNavegar.dados()));

        System.out.println("(1)Anterior, (2)Adicionar ao carrinho, (3)Posterior, (4)Checkout ou (5)Sair da navegacao");

        acao = new_scanner.next();

        int qtdAdd = -1;

        if(Objects.equals(acao, "1")){

            if(produtoNavegar.previous == null){

                System.out.println("Esse é o primeiro produto da navegacao!");

                navegarInterno(produtoBase);

            } else{

                navegarInterno(produtoNavegar.previous);

            }

        } else if(Objects.equals(acao, "2")){

            while(qtdAdd < 0 || qtdAdd > (int)produtoNavegar.qtd){

                System.out.println("Qual a quantidade desse produto que deseja adicionar ao carrinho?");

                qtdAdd = new_scanner.nextInt();

                if (!((Cliente) cpfLogado).checarNoCarrinho(produtoNavegar)){

                    System.out.println("Caiu no primeiro if");

                    ((Cliente) cpfLogado).inserirCarrinho(produtoNavegar, qtdAdd);

                } else{

                    System.out.println("caiu no segundo if");

                    ((Cliente) cpfLogado).modificarQtdPC(produtoNavegar, qtdAdd);

                }

            }

        } else if (Objects.equals(acao, "3")) {

            if(produtoNavegar.next == null){

                navegarInterno(produtoBase);

            } else{

                navegarInterno(produtoNavegar.next);

            }

        } else if (Objects.equals(acao, "4")){

            gerarPedido((Cliente) cpfLogado);

            return 0;

        } else {

            return 0;

        }

        ((Cliente) cpfLogado).dados();

        return 0;

    }

    public int gerarPedido(Cliente cliente){

        if(cliente == null){

            return 1;

        }

        Pedido new_pedido = new Pedido();

        new_pedido.fezEssePedido = cliente;

        if(pedidoBase == null){

            pedidoBase = new_pedido;

        } else {

            pedidoAtual.next = new_pedido;

        }

        pedidoAtual = new_pedido;

        Cliente.produtoCarrinho pcCheck = cliente.pcBase;

        int precoInserir;

        while(pcCheck != null){

            precoInserir =  (int)pcCheck.produtoInserido.preco * (int)pcCheck.qtd;

            new_pedido.inserirIp(precoInserir , (Cliente)cpfLogado);

            System.out.println(new_pedido);

            pcCheck = pcCheck.next;

        }

        return 0;

    }

    public int checkout(Cliente cliente){

        if(cpfLogado == null){

            System.out.println("Realize o login!");

        } else if(permissaoLogado != "Cliente"){

            System.out.println("Acao disponivel apenas para clientes.");

            return 1;

        }

        gerarPedido(cliente);

        return 0;

    }

    public int checarConfimacoesCliente(){

        if(permissaoLogado != "Cliente"){

            System.out.println("Acao disponivel apenas para clientes.");

            return 1;

        }

        Cliente cliente = (Cliente) cpfLogado;

        cliente.lerConfirmacoes();

        return 0;

    }

}
