import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MENUS {

    public static final String VERMELHO = "\u001B[31m";
    public static final String AZUL     = "\u001B[34m";
    public static final String VERDE    = "\u001B[32m";
    public static final String AMARELO  = "\u001B[33m";
    public static final String RESET    = "\u001B[0m";

    public static void MENULOGIN() throws UtilizadorException {

        Scanner sc = new Scanner(System.in);
        Utilizador user;
        Vintage v = putLogin();

        System.out.println("-------------BEM-VINDO À VINTED!----------");
        System.out.println("1-LOGIN");
        System.out.println("2-CRIAR CONTA");

        int n = sc.nextInt();

        switch (n) {
            // login
            case 1 -> {
                // System.out.println(v);
                System.out.println("MAIL:");
                String a = sc.next();
                System.out.println("PALAVRA-PASSE:");
                String b = sc.next();

                try
                {
                    user = v.autenticarUtilizador(a, b);
                    MENUPRINC(v, user);
                    v.store_state();

                } catch(UtilizadorException e)
                {
                    System.out.println("MAIL OU PALAVRA-PASSE INCORRETOS");
                }
                // MENUPRINC(v, user);


            }
            // criar conta
            case 2 -> {
                try
                {
                    System.out.println("MAIL:");
                    String n1 = sc.next();
                    System.out.println("NOME:");
                    String n2 = sc.next();
                    System.out.println("MORADA:");
                    String n3 = sc.next();
                    System.out.println("NIF:");
                    int n4 = sc.nextInt();
                    System.out.println("PASSWORD:");
                    String n5 = sc.next();
                    System.out.println("CONFIRME PASSWORD:");
                    String n6 = sc.next();
                    if (!n5.equals(n6))
                    {
                        System.out.println("PALAVRA-PASSE INCORRETA");
                    }
                    else
                    {
                        user = new Utilizador(n1, n2, n3, n4, n6, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                        v.addUtilizador(user);
                        System.out.println("CONTA CRIADA COM SUCESSO!");
                        MENUPRINC(v, user);
                    }
                }
                catch (InputMismatchException e)
                {
                    System.out.println("Input no formato errado!");
                }
            }
            default -> System.out.print(VERMELHO + "\nOpção Inválida!" + RESET);
        }
    }

    public static void MENUPRINC(Vintage v, Utilizador u) {

        Scanner menuP = new Scanner(System.in);
        System.out.print("\033[H\033[2J"); //limpa o menu anterior

        System.out.println("-------------BEM-VINDO À VINTED!----------");

        boolean continuar = true, limpar = false;

        String respostaUltimoPedido = "";

        while(continuar)
        {
            if(limpar) System.out.print("\033[H\033[2J"); //limpa o menu anterior

            System.out.println(respostaUltimoPedido);
            System.out.println();
            System.out.println("1-COMPRAR");
            System.out.println("2-VENDER");
            System.out.println("3-ESTATÍSTICAS");
            System.out.println("4-CONTA");
            System.out.println("0-SAIR");
            System.out.print("SELECIONE OPÇÃO: ");

            int opcao1 = menuP.nextInt();

            switch (opcao1)
            {
                case 1 -> respostaUltimoPedido = MENUCOMPRAS(v, u);
                case 2 -> respostaUltimoPedido = MENUVENDER(v, u);
                case 3 -> respostaUltimoPedido = MENUEST(v, u);
                case 4 -> respostaUltimoPedido = MENUCONTA(v, u);
                case 0 ->
                {
                    continuar = false;
                    System.out.println("ATÉ JÁ!");
                }
                default -> respostaUltimoPedido = VERMELHO + "\nOpção Inválida!" + RESET;
            }

            if(respostaUltimoPedido == null) continuar = false;

            limpar = true;
        }

        v.store_state();
        menuP.close();
    }

    public static String MENUCOMPRAS(Vintage v, Utilizador u) {
        Scanner compras = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.println("10-BACK..........................0-SAIR ");
        System.out.println("                                        ");

        printArtigosDisponiveis(v);

        System.out.print("ID DO ARTIGO: ");
        String artigoID = compras.nextLine();
        Artigo a = v.getArtigos().get(artigoID);
        if (a == null) {
            return "Artigo não encontrado!";
        } else {
            //v.efectCompra(a.getId(), u);
            v.addCarrinho(a,u.getEmail());
            v.getArtigos().remove(a);

            return "Artigo " + artigoID + " adicionado ao carrinho!";
        }
    }

    public static String MENUVENDER(Vintage v, Utilizador u) {

        String resposta = "";

        Scanner vender = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.println("10-BACK..........................0-SAIR ");
        System.out.println("                                        ");
        System.out.println("----VENDER ARTIGO-----");
        System.out.println("Transportadora: ");
        String n0 = vender.next();
        System.out.println("DESCRIÇÃO:");
        String n1 = vender.next();
        System.out.println("MARCA:");
        String n2 = vender.next();
        System.out.println("PREÇO:");
        double n3 = vender.nextDouble();
        System.out.println("DESCONTO:");
        double n4 = vender.nextDouble();
        System.out.println("AVALIAÇÃO:");
        double n5 = vender.nextDouble();
        System.out.println("NR DONOS:");
        int n6 = vender.nextInt();
        System.out.println("RESPONDA COM S EM CASO AFIRMATIVO E N CASO CONTRÁRIO. PREMIUM:"); //MUDAR PARA BOOL
        String n7 = vender.next();
        boolean b7;
        b7 = n7.equalsIgnoreCase("s");
        System.out.println("TIPO DE ARTIGO:");
        System.out.println("1-TShirt");
        System.out.println("2-Mala");
        System.out.println("3-Sapatilha");
        int n8 = vender.nextInt();
        switch (n8) {
            case 1 -> {
                System.out.println("TAMANHO:");
                String t1 = vender.next();
                System.out.println("PADRÃO:");
                String t2 = vender.next();
                TShirt t = new TShirt (new Transportadora(n0), n1, n2, n3, n4, LocalDate.now(), n5, n6, b7, t1, t2);
                v.addArtigoAVenda(t, u.getEmail());
                v.addArtigo(t);
                v.getArtigos().put(t.getId(),t);

                resposta = "Artigo (t-shirt) foi listado com sucesso!";
            }
            case 2 -> {
                System.out.println("LARGURA:");
                double m1 = vender.nextDouble();
                System.out.println("ALTURA:");
                double m2 = vender.nextDouble();
                System.out.println("MATERIAL:");
                String m3 = vender.next();
                Mala m = new Mala(new Transportadora(n0), n1, n2, n3, n4, LocalDate.now(), n5, n6, b7,m1,m2, m3);
                v.addArtigoAVenda(m, u.getEmail());
                v.addArtigo(m);
                v.getArtigos().put(m.getId(),m);
                resposta  = "Artigo (mala) foi listado com sucesso!";

            }
            case 3 -> {
                System.out.println("TAMANHO:");
                double s1 = vender.nextDouble();
                System.out.println("FIXADORES:");
                String s2 = vender.next();
                System.out.println("COR:");
                String s3 = vender.next();
                Sapatilha sapa1 = new Sapatilha(new Transportadora(n0),  n1, n2, n3, n4, LocalDate.now(), n5, n6, b7, s1, s2, s3);
                v.addArtigoAVenda(sapa1, u.getEmail());
                v.addArtigo(sapa1);
                v.getArtigos().put(sapa1.getId(),sapa1);
                resposta = "Artigo (par de sapatilhas) foi listado com sucesso!";
            }
            case 0 -> {
                System.out.println("Até já!");
                vender.close();
                resposta = null;
            }
            default -> resposta  = VERMELHO + "\nOpção Inválida!" + RESET;
        }
        v.store_state();

        return resposta;

    }

    public static String MENUEST(Vintage v, Utilizador u)
    {
        String resposta = "";

        Scanner est = new Scanner(System.in);
        System.out.print("\033[H\033[2J");

        System.out.println("10-BACK..........................0-SAIR ");
        System.out.println("                                        ");
        System.out.println("------ESTATÍSTICAS-----");
        System.out.println("1- VENDEDOR QUE MAIS FACTUROU NUM DADO PERIODO");
        System.out.println("2- TRANSPORTADORA COM MAIOR VOLUME DE FACTURAÇÃO");
        System.out.println("3- LISTAR AS ENCOMENDAS FEITAS POR UM VENDEDOR");
        System.out.println("4- MAIORES COMPRADORES/VENDEDORES DO SISTEMA NUM DADO PERIODO");
        System.out.println("5- LUCRO DA VINTAGE");


        System.out.println("SELECIONE OPÇÃO: ");

        int opcao1 = est.nextInt();

        switch (opcao1)
        {
            case 1 ->
            {

                System.out.println("O VENDEDOR QUE MAIS FATUROU ENTRE:");
                System.out.println("INSIRA DATA1");
                String data1 = est.next();
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate dataa = LocalDate.parse(data1, formatter1);

                System.out.println("INSIRA DATA2");
                String data2 = est.next();
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate datab = LocalDate.parse(data2, formatter2);
                System.out.println(v.vendedorMaisFaturou(dataa, datab));
                System.out.println("Carregue em ENTER para continuar");
                est.next();
            }
            case 2 ->
            {
                System.out.println("A TRANSPORTADORA COM MAIOR VOLUME DE FACTURAÇÃO É:");
                System.out.println(v.TransportadoraMaisFacturou().getNome());
                System.out.println("Carregue em ENTER para continuar");
                est.next();
            }
            case 3 ->
            {
                System.out.println("REGISTO DE ENCOMENDAS DO VENDEDOR:");
                String vendedor = est.next();
                //v.VerifUtilizador(vendedor);
                v.getUtilizadores().get(vendedor);
                System.out.println("Carregue em ENTER para voltar ao menu principal");
                est.next();
            }
            case 4 ->
            {
                System.out.println("MAIORES COMPRADORES/VENDEDORES DO SISTEMA: ");
                System.out.println("1- VENDEDORES");
                System.out.println("2- COMPRADORES");
                int n8 = est.nextInt();
                switch (n8)
                {
                    case 1 ->
                    {
                        System.out.println("INSIRA DATA1");
                        String data1 = est.next();
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate dataa = LocalDate.parse(data1, formatter1);

                        System.out.println("INSIRA DATA2");
                        String data2 = est.next();
                        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate datab = LocalDate.parse(data2, formatter2);

                        List<Map.Entry<Utilizador, Double>> maioresVendedores = v.maioresVendedores(dataa, datab);
                        for (Map.Entry<Utilizador, Double> entrada : maioresVendedores)
                        {
                            Utilizador vendedor = entrada.getKey();
                            double valorVendas = entrada.getValue();
                            System.out.println("Vendedor: " + vendedor.getNome() + ", Valor de vendas: " + valorVendas);
                        }
                        System.out.println("Carregue em ENTER para voltar ao menu principal");
                        est.next();
                    }

                    case 2 ->
                    {
                        System.out.println("INSIRA DATA1");
                        String data1 = est.next();
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate dataa = LocalDate.parse(data1, formatter1);

                        System.out.println("INSIRA DATA2");
                        String data2 = est.next();
                        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate datab = LocalDate.parse(data2, formatter2);

                        List<Map.Entry<Utilizador, Double>> maiorescompradores = v.maioresCompradores(dataa, datab);
                        for (Map.Entry<Utilizador, Double> entrada : maiorescompradores)
                        {
                            Utilizador vendedor = entrada.getKey();
                            double valorVendas = entrada.getValue();
                            System.out.println("Comprador: " + vendedor.getNome() + ", Valor de compras: " + valorVendas);
                        }
                        System.out.println("Carregue em ENTER para voltar ao menu principal");
                        est.next();
                    }

                    default -> resposta = VERMELHO + "\nOpção Inválida!" + RESET;
                }
            }
            case 5 -> resposta = "LUCRO DA VINTAGE: " + v.LucroVintage();
            case 10 -> resposta = "";
            case 0 ->
            {
                resposta = null;
                System.out.println("ATÉ JÁ!");
                v.store_state();
                est.close();
            }
            default -> resposta = "\nOpção inválida!";
        }
        return resposta;
    }

    public static String MENUCONTA(Vintage v, Utilizador u)
    {
        String resposta = "";
        Scanner conta = new Scanner(System.in);
        System.out.print("\033[H\033[2J");

        System.out.println("10-BACK..........................0-SAIR ");
        System.out.println("                                        ");
        System.out.println("------DEFINIÇÕES DE CONTA-----");
        System.out.println("1-EDITAR DADOS");
        System.out.println("2-VER VENDAS");
        System.out.println("3-VER FATURAS PRODUTOS ADQUIRIDOS");
        System.out.println("4-VER PRODUTOS À VENDA");
        System.out.println("5-CARRINHO");


        System.out.println("SELECIONE OPÇÃO: ");

        int opcao1 = conta.nextInt();

        switch (opcao1)
        {
            case 1 ->
            {
                System.out.print("\033[H\033[2J");
                System.out.println("10-BACK..........................0-SAIR ");
                System.out.println("                                        ");
                System.out.println("EDITAR DADOS:");
                System.out.println("ID: " + u.getId());
                System.out.println("1-EMAIL: " + u.getEmail());
                System.out.println("2-NOME: " + u.getNome());
                System.out.println("3-MORADA: " + u.getMorada());
                System.out.println("4-NIF: " + u.getNIF());
                System.out.println("5-PASSWORD: " + u.getPassword());
                System.out.println("Saldo: " + u.getSaldo());
                int edit = conta.nextInt();
                switch (edit)
                {
                    case 1 ->
                    {
                        System.out.println("NOVO EMAIL:");
                        String c1 = conta.next();
                        u.setEmail(c1);
                    }
                    case 2 ->
                    {
                        System.out.println("NOVO NOME:");
                        String c2 = conta.next();
                        u.setNome(c2);
                    }
                    case 3 ->
                    {
                        System.out.println("NOVA MORADA:");
                        String c3 = conta.next();
                        u.setMorada(c3);
                    }
                    case 4 ->
                    {
                        System.out.println("NOVO NIF:");
                        int c4 = conta.nextInt();
                        u.setNIF(c4);
                    }
                    case 5 ->
                    {
                        System.out.println("NOVA PASSWORD:"); //view
                        String c5 = conta.next();
                        u.setPassword(c5);
                    }
                    case 10 -> MENUCONTA(v, u);
                    case 0 ->
                    {
                        System.out.println("ATÉ JÁ!");
                        conta.close();
                        resposta = null;
                    }
                    default -> resposta = VERMELHO + "\nOpção Inválida!" + RESET;
                }
            }
            case 2 ->
            {
                System.out.print("\033[H\033[2J");
                System.out.println("10-BACK..........................0-SAIR ");
                System.out.println("                                        ");
                System.out.println("----------------VENDAS------------------");
                System.out.println(u.getVendas());
                int opcao4 = conta.nextInt();

                switch (opcao4)
                {

                    case 10 -> MENUCONTA(v, u);
                    case 0 ->
                    {
                        System.out.println("ATÉ JÁ!");
                        conta.close();
                        resposta = null;
                    }
                    default -> resposta = VERMELHO + "\nOpção Inválida!" + RESET;

                }

            }
            case 3 ->
            {
                System.out.print("\033[H\033[2J");
                System.out.println("10-BACK..........................0-SAIR ");
                System.out.println("                                        ");
                System.out.println("---PRODUTOS ADQUIRIDOS----");
                System.out.println("1- DEVOLVER ARTIGO");

                for (Artigo p : u.getProdutosAdquiridos())
                {
                    System.out.println(p);
                }

                int opcao4 = conta.nextInt();

                switch (opcao4)
                {
                    case 1 ->
                    {
                        System.out.print("Insira o ID da encomenda que deseja devolver: ");

                        String id = conta.next();

                        boolean foiDevolvido = v.devolucao(id, u);

                        if(foiDevolvido)
                            return "Encomenda com ID " + id + " foi devolvida com sucesso!";
                        else
                            return "Não foi possivel devolver a encomenda com ID " + id + ".";
                    }
                    case 10 -> MENUCONTA(v, u);
                    case 0 ->
                    {
                        System.out.println("ATÉ JÁ!");
                        conta.close();
                        resposta = null;
                    }
                    default -> resposta = VERMELHO + "\nOpção Inválida!" + RESET;

                }
            }
            case 4 ->
            {
                System.out.print("\033[H\033[2J");
                System.out.println("10-BACK..........................0-SAIR ");
                System.out.println("                                        ");
                System.out.println("-----PRODUTOS À VENDA--------");

                for (Artigo p : u.getProdutosAVenda())
                {
                    System.out.println(p);
                }

                int opcao4 = conta.nextInt();

                switch (opcao4)
                {

                    case 10 -> MENUCONTA(v, u);
                    case 0 ->
                    {
                        System.out.println("ATÉ JÁ!");
                        conta.close();
                        resposta = null;
                    }
                    default -> resposta = VERMELHO + "\nOpção Inválida!" + RESET;

                }


            }
            case 5-> {
                System.out.println("------CARRINHO-----");
                for (Artigo p : u.getCarrinho()) {
                    System.out.println(p);
                }
                Encomenda encomenda = new Encomenda();
                encomenda.setEstado("pendente");
                encomenda.setArtigos(u.getCarrinho());
                double precoFinal = encomenda.calcularPrecoEnc();
                encomenda.setPrecof(precoFinal);
                encomenda.setData(LocalDateTime.now());

                System.out.println("1-PAGAR");
                System.out.println("2 - ADICIONAR ARTIGO");
                System.out.println("3 - REMOVER ARTIGO");
                System.out.println("0-SAIR........10-BACK");


                int opcao7 = conta.nextInt();

                switch (opcao7) {
                    case 1 -> {
                        v.pagamento(u, encomenda);
                        v.addContaArtigoAquirido(encomenda, u.getEmail());
                        MENUPRINC(v, u);

                    }

                    case 2 -> {
                            MENUCOMPRAS(v, u);
                        }

                    case 3 -> {
                        System.out.println("REMOVER ARTIGO:");
                        String a = conta.next();
                        u.removeCarrinho(a);
                    }
                    case 10 -> MENUCONTA(v, u);
                    case 0 -> {
                        System.out.println("ATÉ JÁ!");
                        conta.close();

                    }
                }


            }
            case 10 -> resposta  = "";
            case 0 ->
            {
                resposta = null;
                System.out.println("ATÉ JÁ!");
                conta.close();
            }
            default -> resposta = VERMELHO + "\nOpção Inválida!" + RESET;
        }
        return resposta;
    }

    public static Vintage read_state() {
        Vintage v;

        try
        {
            FileInputStream fis = new FileInputStream("save.ser");
            ObjectInputStream in = new ObjectInputStream(fis);

            v = (Vintage) in.readObject();

            //System.out.println(v);

            in.close();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            v = new Vintage();

        }
        return v;
    }

    public static Vintage putLogin () {
        Vintage v = new Vintage();
        Scanner scanner = new Scanner(System.in);
        System.out.println("MÉTODO DE POVOAMENTO");
        System.out.println("1- FICHEIRO DE OBJETOS");
        System.out.println("2- SCRIPT");
        int op = scanner.nextInt();

        switch (op)
        {
            case 1 ->
                    v = read_state();
            case 2 ->
                    v = SCRIPT.script();
        }

        return v;
    }

    public static void printArtigosDisponiveis(Vintage v)
    {
        List<TShirt> tees =
                v.getArtigos().values().stream().filter(a -> a instanceof TShirt).map(a -> (TShirt)a).toList();
        List<Sapatilha> tilhas =
                v.getArtigos().values().stream().filter(a -> a instanceof Sapatilha).map(a -> (Sapatilha)a).toList();
        List<Mala> malas =
                v.getArtigos().values().stream().filter(a -> a instanceof Mala).map(a -> (Mala)a).toList();


        System.out.println("------ COMPRAR ------");
        System.out.println(AZUL + "----- SAPATILHAS ----" + RESET);
        printSapatilhas(tilhas);
        System.out.println(AZUL + "------ T-SHIRTS -----" + RESET);
        printTshirts(tees);
        System.out.println(AZUL + "------- MALAS -------" + RESET);
        printMalas(malas);

    }

    public static void printTshirts(List<TShirt> tees)
    {
        for(TShirt tee : tees)
        {
            System.out.println(AZUL + "ID: " + RESET + tee.getId());
            System.out.println(AZUL + "\tMarca: " + RESET + tee.getMarca());
            System.out.println(AZUL + "\tDescrição: " + RESET + tee.getDescricao());
            System.out.println(AZUL + "\tTamanho: " + RESET + tee.getTTamanho());
            System.out.println(AZUL + "\tPreco: " + RESET + tee.calculaPreco() + " (" + tee.getPreco() + " com " + tee.getDesconto() + "% " +
                    "de desconto)");
            System.out.println(AZUL + "\tPadrão: " + RESET + tee.getPadrao());
            System.out.println(AZUL + "\tAvaliação: " + RESET + tee.getAvaliacao() + "/5.0");
            System.out.println(AZUL + "\tData: " + RESET + tee.getData());
            System.out.println(AZUL + "\tTransportadora: " + RESET + tee.getTransportadora());
            System.out.println(AZUL + "\tDonos: " + RESET + tee.getDonos());
            System.out.println(AZUL + "\tCategoria: " + RESET + tee.getCategoria());
        }
    }

    public static void printSapatilhas(List<Sapatilha> tilhas)
    {
        for(Sapatilha tilha : tilhas)
        {
            System.out.println(AZUL + "ID: " + RESET + tilha.getId());
            System.out.println(AZUL + "\tMarca: " + RESET + tilha.getMarca());
            System.out.println(AZUL + "\tDescrição: " + RESET + tilha.getDescricao());
            System.out.println(AZUL + "\tCor: " + RESET + tilha.getCor());
            System.out.println(AZUL + "\tTamanho: " + RESET + tilha.getSTamanho());
            System.out.println(AZUL + "\tPreco: " + RESET + tilha.calculaPreco() + " (" + tilha.getPreco() + " com " + tilha.getDesconto() +
                    "% de desconto)");
            System.out.println(AZUL + "\tAvaliação: " + RESET + tilha.getAvaliacao() + "/5.0");
            System.out.println(AZUL + "\tData: " + RESET + tilha.getData());
            System.out.println(AZUL + "\tTransportadora: " + RESET + tilha.getTransportadora());
            System.out.println(AZUL + "\tDonos: " + RESET + tilha.getDonos());
            System.out.println(AZUL + "\tCategoria: " + RESET + tilha.getCategoria());
            System.out.println(AZUL + "\tFixador: " + RESET + tilha.getFixadores());
        }
    }

    public static void printMalas(List<Mala> malas)
    {
        for(Mala mala : malas)
        {
            System.out.println(AZUL + "ID: " + RESET + mala.getId());
            System.out.println(AZUL + "\tMarca: " + RESET + mala.getMarca());
            System.out.println(AZUL + "\tDescrição: " + RESET + mala.getDescricao());
            System.out.println(AZUL + "\tPreco: " + RESET + mala.calculaPreco() + " (" + mala.getPreco() + " com " + mala.getDesconto() +
                    "% de desconto)");
            System.out.println(AZUL + "\tAvaliação: " + RESET + mala.getAvaliacao() + "/5.0");
            System.out.println(AZUL + "\tData: " + RESET + mala.getData());
            System.out.println(AZUL + "\tTransportadora: " + RESET + mala.getTransportadora());
            System.out.println(AZUL + "\tDonos: " + RESET + mala.getDonos());
            System.out.println(AZUL + "\tCategoria: " + RESET + mala.getCategoria());
        }
    }
}

