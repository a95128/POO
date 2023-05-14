import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estatisticas {

    private Map<String, Utilizador> users;
    private Map<Integer, Artigo> hash_artigos;
    private Map<String, Transportadora> hash_transp;

    public Map<String, Utilizador>getUtilizadores() {return users;}

    public void setUtilizador(Map<String, Utilizador> users) {
        this.users = new HashMap<>(users);
    }

    public Map<Integer,Artigo> getArtigos() {
        return hash_artigos;
    }

    public void setArtigos(Map<Integer,Artigo> artigos) {
        this.hash_artigos = new HashMap<>(artigos);
    }

    public Map<String,Transportadora> getTransportadoras() {
        return this.hash_transp;
    }

    public void setTransportadoras(Map<String,Transportadora> t ) {
        this.hash_transp = t;
    }


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


