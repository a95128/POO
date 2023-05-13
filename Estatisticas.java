import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estatisticas {

    private Map<Utilizador, Double> hash_users;
    private Map<Integer, Artigo> hash_artigos;
    private Map<String, Transportadora> hash_transp;

    public Map<Utilizador,Double> getUtilizadores() {
        return hash_users;
    }

    public void setUtilizador(Map<Utilizador, Double> users) {
        this.hash_users = new HashMap<>(users);
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

    public Utilizador vendedorMaisFaturou(LocalDate inicio, LocalDate fim, Map<Utilizador, Double> hash_users) {
        // Calcula o total de vendas de cada vendedor
        Map<Utilizador, Double> vendasPorVendedor = new HashMap<>();
        for (Utilizador u : hash_users.keySet()) {
            for(Artigo a : u.getVendas()) {
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
    public List<Map.Entry<Utilizador, Double>> maioresCompradores(LocalDate inicio, LocalDate fim) {
        List<Map.Entry<Utilizador, Double>> comprasTotais = new ArrayList<>();

        // percorre todas as vendas realizadas durante o período
        for (Utilizador u : hash_users.values()) {
            for (Artigo a : u.getProdutosAdquiridos()) {
                LocalDate data = a.getData();
                if ((inicio == null || !data.isBefore(inicio)) && (fim == null || !data.isAfter(fim))) {
                    Utilizador comprador = u;
                    double valor = a.getPreco();

                    // adiciona o valor da venda ao total de compras do utilizador
                    hash_users.put(comprador, hash_users.getOrDefault(comprador, 0.0) + valor);
                }

                // ordena a lista de pares pelo total em ordem decrescente
                comprasTotais.sort(Map.Entry.<Utilizador, Double>comparingByValue().reversed());

            }
            return comprasTotais;
        }

    public List<Map.Entry<Utilizador, Double>> maioresVendedores (LocalDate inicio, LocalDate fim){
                // cria uma lista de pares (utilizador, total) para armazenar as vendas
                List<Map.Entry<Utilizador, Double>> vendasTotais = new ArrayList<>();

                // percorre todas as vendas realizadas durante o período
                for (Utilizador u : hash_users.values()) {
                    for (Artigo a : u.getVendas()) {
                        LocalDate data = a.getData();
                        if ((inicio == null || !data.isBefore(inicio)) && (fim == null || !data.isAfter(fim))) {
                            Utilizador comprador = u;
                            double valor = a.getPreco();

                    // adiciona o valor da venda ao total de vendas do utilizador
                    vendas.put(vendedor, vendas.getOrDefault(vendedor, 0.0) + valor);
                }

                // ordena a lista de pares pelo total em ordem decrescente
                vendasTotais.sort(Map.Entry.<Utilizador, Double>comparingByValue().reversed());

                return vendasTotais;
            }

        }
    }
}
*/

}

