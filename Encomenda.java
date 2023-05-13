import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Encomenda implements Serializable {

    private String id;
    private List<Artigo> artigos;
    public static final double TSU = 0.25; //TAXA DE SATISFAÇÃO USADOS
    public static final double TSN = 0.50; //TAXA DE SATISFACAO NOVOS
    private double preco_final;
    private String estado;
    private LocalDateTime data;


    //private double taxa_exp;

    public Encomenda() {
        this.id = UUID.randomUUID().toString();
        this.artigos = new ArrayList<Artigo>();
        this.preco_final = 0.0;
        this.estado = "";
        this.data = LocalDateTime.now();

    }
    public Encomenda(List<Artigo> a, double p,String e, LocalDateTime d) {
        this.id = UUID.randomUUID().toString();
        this.artigos = a;
        this.preco_final = p;
        this.estado = e;
        this.data = d;
    }

    public Encomenda(Encomenda e) {
        this.id = e.getId();
        this.artigos = e.getArtigos();
        this.preco_final=e.getPrecof();
        this.estado=e.getEstado();
        this.data=e.getData();
    }

    //SETS E GETS

    public String getId() {return this.id;}
    public void setId(String id) {this.id = id;}
    public List<Artigo> getArtigos() {return this.artigos;}
    public void setArtigos(List<Artigo> f) {this.artigos = f;}
    public double getPrecof() {return this.preco_final;}
    public void setPrecof(double p) {this.preco_final = p;}
    public String getEstado() {return this.estado;}
    public void setEstado(String e) {this.estado = e;}
    public LocalDateTime getData() {return this.data;}
    public void setData(LocalDateTime d) { this.data = d;}

    //ADICIONA UM ARTIGO
    public void addArtigo(Artigo p) {this.artigos.add(p.clone());}

    //APAGA UM ARTIGO
    public void deleteArtigo(Artigo p) {this.artigos.remove(p);}

    //INDICA OS NUMEROS DE ARTIGOS NUMA ENCOMENDA
    public int nrArtigos() {return this.artigos.size();}

    //INDICA OS NUMEROS DE ARTIGOS PREMIUMS OU NAO
    public int getNrArtigosPorCategoria(boolean categoria) {
        int conta = 0;
        for (Artigo artigo : this.artigos) {
            if (artigo.getCategoria() == categoria) {
                conta++;
            }
        }
        return conta;
    }

    public double calcularPrecoEnc() {
        double preco = 0.0;
        int novos = 0;
        int usados = 0;
        for (Artigo artigo : this.artigos) {
            preco += artigo.calculaPreco();
            if (artigo.getDonos() == 1) novos++;
            else usados++;
        }
        preco += novos*TSN + usados*TSU;
        return preco;
    }

    // Método para verificar se é possivel devolver uma encomenda
    public boolean devolve() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoDaysAgo = now.minusHours(48);
        return data.isAfter(twoDaysAgo) && data.isBefore(now);
    }

    //CLONE ENCOMENDA

    public Encomenda clone() {return new Encomenda(this);}

    // Método para exibir informações da encomenda

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.id)
                .append("Artigos: ");
        if (this.artigos != null) {
            for (Artigo artigo : artigos) {
                sb.append("\n").append(artigo);
            }

            sb.append("\nPreço Final: ").append(this.preco_final)
                    .append("\nEstado: ").append(this.estado)
                    .append("\nData: ").append(this.data);
        }
        else {
            sb.append("\nNenhum artigo encontrado.").append("\nPreço Final: 0")
            .append("\nEstado: Não definido");
        }
        return sb.toString();
    }


}
