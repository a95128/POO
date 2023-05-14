import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MENUS {

    public static void MENULOGIN() throws UtilizadorException {

        Scanner sc = new Scanner(System.in);
        Utilizador user;
       Vintage v = putLogin();

        System.out.println("-------------BEM-VINDO À VINTED!----------");
        System.out.println("1-LOGIN");
        System.out.println("2-CRIAR CONTA");

    int n = sc.nextInt();

        switch (n) {
            case 1 -> {
                // System.out.println(v);
                System.out.println("MAIL:");
                String a = sc.next();
                System.out.println("PALAVRA-PASSE:");
                String b = sc.next();

                try {
                    user = v.autenticarUtilizador(a, b);
                    //v.store_state();
                    MENUPRINC(v, user);
                    v.store_state();

                } catch(UtilizadorException e) {
                    System.out.println("MAIL OU PALAVRA-PASSE INCORRETOS");
                }
                   // MENUPRINC(v, user);


            }
            case 2 -> {
                try {
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
                    if (!n5.equals(n6)) {
                        System.out.println("PALAVRA-PASSE INCORRETA");
                    } else {
                        user = new Utilizador(n1, n2, n3, n4, n6, 0.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                        v.addUtilizador(user);
                        System.out.println("CONTA CRIADA COM SUCESSO!");
                        MENUPRINC(v, user);
                        v.store_state();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input no formato errado!");
                }
            }
            default -> System.out.print("\nOpção Inválida!");
        }
}

/*
    public static void MENUDATA(Vintage v, Utilizador u) {
        Scanner menuData = new Scanner(System.in);
        System.out.println("DEFINA A DATA DO PROGRAMA:");
        String data1 = menuData.next();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dataprograma = LocalDate.parse(data1, formatter1);
        MENUPRINC(v,u);
    }
    */
    public static void MENUPRINC(Vintage v, Utilizador u) {

        Scanner menuP = new Scanner(System.in);
        System.out.print("\033[H\033[2J"); //limpa o menu anterior

        System.out.println("-------------BEM-VINDO À VINTED!----------");
        System.out.println("1-COMPRAR");
        System.out.println("2-VENDER");
        System.out.println("3-ESTATÍSTICAS");
        System.out.println("4-CONTA");
        System.out.println("0-SAIR");
        System.out.println("SELECIONE OPÇÃO: ");

        int opcao1 = menuP.nextInt();

        switch (opcao1) {

            case 1:
                MENUCOMPRAS(v,u);
                break;

            case 2:
                MENUVENDER(v,u);
                break;

            case 3:
                MENUEST(v,u);
                break;

            case 4:
                MENUCONTA(v,u);
                break;

            case 0:
                v.store_state();
                System.out.println("ATÉ JÁ!");
                menuP.close();
                break;

            default:
                System.out.print("\nOpção Inválida!");
                break;
        }
    }

    public static void MENUCOMPRAS(Vintage v, Utilizador u) {
        Scanner compras = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.println("10-BACK..........................0-SAIR ");
        System.out.println("                                        ");
        System.out.println("----COMPRAR-----");

        boolean continuarComprando = true;

        while (continuarComprando) {
            // Obtém a lista atualizada de artigos disponíveis para compra
            List<Artigo> artigosDisponiveis = new ArrayList<>(v.getArtigos().values());
            artigosDisponiveis.removeAll(u.getCarrinho());

            // Exibe a lista atualizada de artigos disponíveis
            for (Artigo artigo : artigosDisponiveis) {
                System.out.println(artigo);
            }

            System.out.print("ID DO ARTIGO : ");
            String artigoID = compras.nextLine();

            if (artigoID.equals("0")) {
                continuarComprando = false;
            } else if (artigoID.equals("10")) {
                MENUPRINC(v, u);
            } else {
                Artigo a = v.getArtigos().get(artigoID);
                u.adicionarCarrinho(a);
                v.deleteCarrinho(a);
            }
        }
    }

    public static void MENUVENDER(Vintage v, Utilizador u) {
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
        System.out.println("RESPONDA COM S EM CASO AFIRMATIVO E N CASO CONTRÁRIO. PREMIUM:");
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
                //v.addArtigo(t);
                u.adicionarProdutoAVenda(t);
                v.addArtigoAVenda(t, u.getEmail());
                v.addArtigo(t);
                System.out.println("ARTIGO CRIADO COM SUCESSO");
                MENUPRINC(v,u);


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
                v.store_state();
                MENUPRINC(v,u);

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
                v.store_state();
                MENUPRINC(v,u);
            }

            case 10 -> MENUPRINC(v, u);
            case 0 -> {
                System.out.println("ATÉ JÁ!");
                vender.close();
            }

            default -> System.out.print("\nOpção Inválida!");
        }
        v.store_state();

    }


    public static void MENUEST(Vintage v, Utilizador u) {

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

        switch (opcao1) {
            case 1 -> {

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

            }
            case 2 -> {
                /*
                System.out.println("A TRANSPORTADORA COM MAIOR VOLUME DE FACTURAÇÃO É:");
                System.out.println(v.TransportadoraMaisFacturou());
                */
            }

            case 3 -> {
                System.out.println("REGISTO DE ENCOMENDAS DO VENDEDOR:");
                String vendedor = est.next();
                //v.VerifUtilizador(vendedor);
                v.getUtilizadores().get(vendedor);
            }

            case 4 -> {
                System.out.println("MAIORES COMPRADORES/VENDEDORES DO SISTEMA: ");
                System.out.println("1- VENDEDORES");
                System.out.println("2- COMPRADORES");

                int n8 = est.nextInt();
                switch (n8) {
                    case 1 -> {
                        System.out.println("INSIRA DATA1");
                        String data1 = est.next();
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate dataa = LocalDate.parse(data1, formatter1);

                        System.out.println("INSIRA DATA2");
                        String data2 = est.next();
                        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate datab = LocalDate.parse(data2, formatter2);

                        List<Map.Entry<Utilizador, Double>> maioresVendedores = v.maioresVendedores(dataa, datab);
                        for (Map.Entry<Utilizador, Double> entrada : maioresVendedores) {
                            Utilizador vendedor = entrada.getKey();
                            double valorVendas = entrada.getValue();
                            System.out.println("Vendedor: " + vendedor.getNome() + ", Valor de vendas: " + valorVendas);
                        }

                    }

                    case 2 -> {
                        System.out.println("INSIRA DATA1");
                        String data1 = est.next();
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate dataa = LocalDate.parse(data1, formatter1);

                        System.out.println("INSIRA DATA2");
                        String data2 = est.next();
                        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate datab = LocalDate.parse(data2, formatter2);

                        List<Map.Entry<Utilizador, Double>> maiorescompradores = v.maioresCompradores(dataa, datab);
                        for (Map.Entry<Utilizador, Double> entrada : maiorescompradores) {
                            Utilizador vendedor = entrada.getKey();
                            double valorVendas = entrada.getValue();
                            System.out.println("Comprador: " + vendedor.getNome() + ", Valor de compras: " + valorVendas);
                        }



                    }

                    default -> System.out.print("\nOpção Inválida!");

                }
            }
            case 5 -> System.out.println("LUCRO DA VINTAGE");
            case 10 -> MENUPRINC(v, u);
            case 0 -> {
                System.out.println("ATÉ JÁ!");
                est.close();
            }
            default -> System.out.println("\nOpção Inválida!");
        }

        }

    public static void MENUCONTA(Vintage v, Utilizador u) {
        Scanner conta = new Scanner(System.in);
        System.out.print("\033[H\033[2J");

        System.out.println("10-BACK..........................0-SAIR ");
        System.out.println("                                        ");
        System.out.println("------DEFINIÇÕES DE CONTA-----");
        System.out.println("1-EDITAR DADOS");
        System.out.println("2-VER VENDAS");
        System.out.println("3-VER FATURAS PRODUTOS ADQUIRIDOS");
        System.out.println("4-VER PRODUTOS À VENDA");
        System.out.println("5-VER FATURAS EMITIDAS");
        System.out.println("6-VER CARRINHO");


        System.out.println("SELECIONE OPÇÃO: ");

        int opcao1 = conta.nextInt();

        switch (opcao1) {
            case 1 -> {
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
                switch (edit) {
                    case 1 -> {
                        System.out.println("NOVO EMAIL:");
                        String c1 = conta.next();
                        u.setEmail(c1);
                    }
                    case 2 -> {
                        System.out.println("NOVO NOME:");
                        String c2 = conta.next();
                        u.setNome(c2);
                    }
                    case 3 -> {
                        System.out.println("NOVA MORADA:");
                        String c3 = conta.next();
                        u.setMorada(c3);
                    }
                    case 4 -> {
                        System.out.println("NOVO NIF:");
                        int c4 = conta.nextInt();
                        u.setNIF(c4);
                    }
                    case 5 -> {
                        System.out.println("NOVA PASSWORD:"); //view
                        String c5 = conta.next();
                        u.setPassword(c5);
                    }
                    case 10 -> MENUCONTA(v, u);
                    case 0 -> {
                        System.out.println("ATÉ JÁ!");
                        conta.close();
                    }
                    default -> System.out.println("\nOpção Inválida!");
                }
            }

            case 2 -> {
                System.out.print("\033[H\033[2J");
                System.out.println("10-BACK..........................0-SAIR ");
                System.out.println("                                        ");
                System.out.println("----------------VENDAS------------------");
                System.out.println(u.getVendas());
                int opcao4 = conta.nextInt();

                switch (opcao4) {

                    case 10 -> MENUCONTA(v, u);
                    case 0 -> {
                        System.out.println("ATÉ JÁ!");
                        conta.close();
                    }
                    default -> System.out.println("\nOpção Inválida!");

                }

            }
            case 3 -> {

                System.out.print("\033[H\033[2J");
                System.out.println("10-BACK..........................0-SAIR ");
                System.out.println("                                        ");
                System.out.println("---PRODUTOS ADQUIRIDOS----");

                for (Artigo p : u.getProdutosAdquiridos()) {
                    System.out.println(p);
                }

                System.out.println("1- DEVOLVER ENCOMENDA COM O ID: ");

                String opcao4 = conta.next();
                switch (opcao4) {


                    case "10" -> MENUCONTA(v, u);
                    case "0" -> {
                        System.out.println("ATÉ JÁ!");
                        conta.close();
                    }
                    default -> System.out.println("\nOpção Inválida!");

                }


            }

            case 4 -> {
                System.out.print("\033[H\033[2J");
                System.out.println("10-BACK..........................0-SAIR ");
                System.out.println("                                        ");
                System.out.println("-----PRODUTOS À VENDA--------");

                for (Artigo p : u.getProdutosAVenda()) {
                    System.out.println(p);
                }

                int opcao4 = conta.nextInt();

                switch (opcao4) {

                    case 10 -> MENUCONTA(v, u);
                    case 0 -> {
                        System.out.println("ATÉ JÁ!");
                        conta.close();
                    }
                    default -> System.out.println("\nOpção Inválida!");

                }

            }

            case 5 -> {
                System.out.print("\033[H\033[2J");
                System.out.println("10-BACK..........................0-SAIR ");
                System.out.println("                                        ");
                System.out.println("---VER FATURAS EMITADAS----");
                System.out.println(v.getEncomendas());
                System.out.println("1- DEVOLVER ARTIGO COM O ID: ");

                for (Artigo p : u.getProdutosAdquiridos()) {
                    System.out.println(p);
                }

                int opcao4 = conta.nextInt();

                switch (opcao4) {

                    case 10 -> MENUCONTA(v, u);

                    case 0 -> {
                        System.out.println("ATÉ JÁ!");
                        conta.close();
                    }
                    default -> System.out.println("\nOpção Inválida!");

                }
            }

            case 6 -> {
                System.out.print("\033[H\033[2J");
                System.out.println("10-BACK..........................0-SAIR ");
                System.out.println("                                        ");
                System.out.println("---VER CARRINHO----");

                for (Artigo p : u.getCarrinho()) {
                    System.out.println(p);
                }

                System.out.println("1 - TERMINAR COMPRA:");
                System.out.println("2 - ADICIONAR ARTIGO");
                System.out.println("3 - REMOVER ARTIGO");

                int opcao4 = conta.nextInt();

                switch (opcao4) {

                    case 1 -> {

                        System.out.println("\033[H\033[2J");
                        System.out.println("----CARRINHO DE COMPRAS----");
                        List<Artigo> carrinho = u.getCarrinho();
                        for (Artigo artigo : carrinho) {
                            System.out.println(artigo);
                        }

                        Encomenda encomenda = new Encomenda();
                        encomenda.setEstado("pendente");
                        encomenda.setArtigos(u.getCarrinho());
                        double precoFinal = encomenda.calcularPrecoEnc();
                        encomenda.setPrecof(precoFinal);
                        encomenda.setData(LocalDateTime.now());

                        System.out.println("---------------------------");
                        System.out.println("TOTAL S/ PORTES: " + u.getTotalCarrinho(u.getCarrinho()) + "€");
                        System.out.println("PRECO FINAL: " + encomenda.getPrecof());
                        System.out.println("---------------------------");
                        System.out.println("1-PAGAR");
                        System.out.println("0-SAIR........10-BACK");


                        int opcao7 = conta.nextInt();

                        switch (opcao7) {
                            case 1-> {
                            v.pagamento(u,encomenda);
                            v.addContaArtigoAquirido(encomenda,u.getEmail());
                            MENUPRINC(v,u);

                            }
                            case 10 -> MENUCONTA(v, u);
                            case 0 -> {
                                System.out.println("ATÉ JÁ!");
                                conta.close();

                            }
                        }

                    }
                    case 2 -> {
                        MENUCOMPRAS(v,u);
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
                    default -> System.out.println("\nOpção Inválida!");
                }
            }
            case 10 -> MENUPRINC(v, u);
            case 0 -> {
                System.out.println("ATÉ JÁ!");
                conta.close();
            }
            default -> System.out.println("\nOpção Inválida!");

        }
    }


    public static Vintage read_state() {
        Vintage v;

        try
        {
            FileInputStream fis = new FileInputStream("save.ser");
            ObjectInputStream in = new ObjectInputStream(fis);

            v = (Vintage) in.readObject();

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

        switch (op){
            case 1 ->
                v = read_state();
            case 2 ->
                v = SCRIPT.script();

        }

        return v;
    }

    }


