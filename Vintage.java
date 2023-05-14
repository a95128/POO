import java.io.*;
import java.time.LocalDate;
import java.util.*;



public class Vintage implements Serializable {
    private Map<String, Utilizador> users;
    private Map<String, Artigo> artigos;
    private Map<String, Encomenda> encomendas; //FAZ MAIS SENTIDO TER UM UTILIZADOR que vendeu
    private Map<String, Transportadora> transportadoras;
    private List<Artigo> compras;


    public Vintage() {

        this.users = new HashMap<>();
        this.encomendas = new HashMap<>();
        this.artigos = new HashMap<>();
        this.transportadoras = new HashMap<>();
        this.compras = new ArrayList<>();
    }

    public Vintage(Map<String, Utilizador> u, Map<String, Encomenda> e, Map<String, Artigo> a, Map<String, Transportadora> t) {
        this.users = u;
        this.encomendas = e;
        this.artigos = a;
        this.transportadoras = t;
    }

    public Vintage(Vintage v) {
        this.users = v.getUtilizadores(); // Copia o MAP de usuários do objeto Vintage original
        this.encomendas = v.getEncomendas(); // Copia a lista de encomendas do objeto Vintage original
        this.artigos = v.getArtigos(); // Copia a lista de artigos do objeto Vintage original
        this.transportadoras = v.getTransportadoras(); // Copia a lista de transportadoras do objeto Vintage original
        this.compras = new ArrayList<>(v.getComprar());
    }

    public Map<String, Utilizador> getUtilizadores() {
        return users;
    }

    public void setUtilizador(Map<String, Utilizador> users) {
        this.users = new HashMap<>(users);
    }

    public Map<String, Artigo> getArtigos() {
        return this.artigos;
    }

    public void setArtigos(Map<String, Artigo> artigos) {
        this.artigos = artigos;
    }

    public Map<String, Encomenda> getEncomendas() {
        return this.encomendas;
    }

    public void setEncomendas(Map<String, Encomenda> encomendas) {
        this.encomendas = encomendas;
    }

    public Map<String, Transportadora> getTransportadoras() {
        return this.transportadoras;
    }

    public void setTransportadoras(Map<String, Transportadora> t) {
        this.transportadoras = t;
    }

    public List<Artigo> getComprar() {
        return this.compras;
    }

    public void setComprar(List<Artigo> compras) {
        this.compras = compras;
    }

    public void addUtilizador(Utilizador u) {
        this.users.put(u.getEmail(), u);
    }

    public void addArtigo(Artigo a) {
        this.artigos.put(a.getId(), a);
    }

    public void addEncomenda(Encomenda e) {
        this.encomendas.put(e.getId(), e);
    }

    public void addTransportadora(Transportadora t) {
        this.transportadoras.put(t.getNome(), t);
    }

    public void addArtigoAVenda(Artigo a, String user) {
        Utilizador util = this.users.get(user);
        util.adicionarProdutoAVenda(a);
        //System.out.println(util.getProdutosAVenda());
    }

    public void addContaArtigoAquirido(Encomenda e,String u) {
        Utilizador util = this.users.get(u);
        for(Artigo a : e.getArtigos()) {
            util.adicionarProdutoAdquirido(a);
        }
    }

    public void addVenda(Artigo a, String user) {
        Utilizador util = this.users.get(user);
        util.adicionarVenda(a);
    }

    public void addAdquirido(Artigo a, String user) {
        Utilizador util = this.users.get(user);
        util.adicionarProdutoAdquirido(a);
    }

    public void addCarrinho(List<Artigo> artigos, String user) {
        Utilizador util = this.users.get(user);
        for (Artigo a : artigos) {
            util.adicionarCarrinho(a);
        }
    }


    public List<Artigo> deleteCarrinho(Artigo a) {
        List<Artigo> comprass = new ArrayList<>();
        for(Artigo b : compras) {
            if(!b.equals(a)) comprass.add(b.clone());
        }
       return comprass;
    }


    public void deleteUtilizadores(Utilizador u) {
        this.users.remove(u);
    }

    public Utilizador autenticarUtilizador(String email, String senha) throws UtilizadorException {
        if (users.containsKey(email)) {
            Utilizador u = users.get(email);
            if (u.getPassword().equals(senha)) {
                return u;
            } else {
                throw new UtilizadorException("MAIL OU PASSWORD INVÁLIDA");
            }
        } else {
            throw new UtilizadorException("O UTILIZADOR NÃO EXISTE");
        }
    }

    public void efectCompra(String id, Utilizador u) {
        if (getArtigos().containsKey(id)) {
            Artigo a = getArtigos().get(id);
            if (a == null) {
                System.out.println("Artigo não encontrado.");
            } else {
                ArrayList<Artigo> artigos = new ArrayList<>();
                addCarrinho(artigos, u.getEmail());
                System.out.println("Artigo adicionado ao carrinho.");
            }

        }
    }


    public void VerifUtilizador(String id) {
        if (users.containsKey(id)) {
            getUtilizadores().get(id);
        } else System.out.println("O UTILIZADOR NÃO EXISTE!");

    }

    public void pagamento(Utilizador u, Encomenda e) {
        double res = 0.0;
        if(u.getSaldo() < e.precoFINAL()) {
            System.out.println("NÃO É POSSIVEL EFECTUAR COMPRA");
        }
        else {
            res = u.getSaldo() - e.precoFINAL();
            e.setEstado("Finalizada");
            System.out.println("COMPRA EFECTUADA");
        }
        u.setSaldo(res);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Utilizadores: ");

        for (Utilizador u : users.values()) {
            sb.append("\n").append(u);
        }

        /*
        else {
            sb.append("\nNenhum artigo encontrado.").append("\nPreço Final: 0")
                    .append("\nEstado: Não definido");
        }
        */
        return sb.toString();

    }

    public void store_state() {
        try {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.ser"));

            //System.out.println(this);
            out.writeObject(this);

            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    // ESTATISTICAS


    public Utilizador vendedorMaisFaturou(LocalDate inicio, LocalDate fim) {
        // Calcula o total de vendas de cada vendedor
        Map<Utilizador, Double> vendasPorVendedor = new HashMap<>();
        for (Utilizador u : users.values()) {
            for (Artigo a : u.getVendas()) {
                LocalDate data = a.getData();
                if ((inicio == null || !data.isBefore(inicio)) && (fim == null || !data.isAfter(fim))) {
                    Utilizador vendedor = u;
                    double valor = a.getPreco();
                    vendasPorVendedor.put(vendedor, vendasPorVendedor.getOrDefault(vendedor, 0.0) + valor);
                }
            }
        }

        // Encontra o vendedor que mais faturou
        Utilizador vendedorMaisFaturou = null;
        double maiorValor = 0.0;
        for (Map.Entry<Utilizador, Double> entry : vendasPorVendedor.entrySet()) {
            Utilizador vendedor = entry.getKey();
            double valor = entry.getValue();
            if (valor > maiorValor) {
                vendedorMaisFaturou = vendedor;
                maiorValor = valor;
            }
        }

        return vendedorMaisFaturou;
    }

    /*
    public Transportadora TransportadoraMaisFacturou() {
        Map<Transportadora, Double> vendasportransportadora = new HashMap<>();
        for (Utilizador u: users.values()) {
            for (Artigo a : u.getProdutosAdquiridos()) {

                    double valor = a.getPreco();
                    Transportadora t = a.getTransportadora();
                    vendasportransportadora.put(t, vendasportransportadora.getOrDefault(t, 0.0) + valor);
                }
            }
        }
        Transportadora transportadoraMaisFaturou = null;
        double maiorValor = 0.0;
        for (Map.Entry<Transportadora, Double> entry : vendasportransportadora.entrySet()) {
            Transportadora t = entry.getKey();
            double valor = entry.getValue();
            if (valor > maiorValor) {
                transportadoraMaisFaturou = t;
                maiorValor = valor;
            }
        }

        return transportadoraMaisFaturou;

    }
    */

    public List<Map.Entry<Utilizador, Double>> maioresCompradores(LocalDate inicio, LocalDate fim) {
        // Calcula o total de vendas de cada vendedor
        Map<Utilizador, Double> comprasporuser = new HashMap<>();
        for (Utilizador u : users.values()) {
            for (Artigo a : u.getProdutosAdquiridos()) {
                LocalDate data = a.getData();
                if ((inicio == null || !data.isBefore(inicio)) && (fim == null || !data.isAfter(fim))) {
                    Utilizador comprador = u;
                    double valor = a.getPreco();
                    comprasporuser.put(comprador, comprasporuser.getOrDefault(comprador, 0.0) + valor);
                }
            }
        }

        List<Map.Entry<Utilizador, Double>> comprasTotais = new ArrayList<>(comprasporuser.entrySet());
        comprasTotais.sort(Map.Entry.<Utilizador, Double>comparingByValue().reversed());

        return comprasTotais;
    }

    public List<Map.Entry<Utilizador, Double>> maioresVendedores(LocalDate inicio, LocalDate fim) {
        // Calcula o total de vendas de cada vendedor
        Map<Utilizador, Double> vendasPorVendedor = new HashMap<>();
        for (Utilizador u : users.values()) {
            for (Artigo a : u.getVendas()) {
                LocalDate data = a.getData();
                if ((inicio == null || !data.isBefore(inicio)) && (fim == null || !data.isAfter(fim))) {
                    Utilizador vendedor = u;
                    double valor = a.getPreco();
                    vendasPorVendedor.put(vendedor, vendasPorVendedor.getOrDefault(vendedor, 0.0) + valor);
                }
            }
        }

        List<Map.Entry<Utilizador, Double>> vendasTotais = new ArrayList<>(vendasPorVendedor.entrySet());
        vendasTotais.sort(Map.Entry.<Utilizador, Double>comparingByValue().reversed());

        return vendasTotais;
    }




}
