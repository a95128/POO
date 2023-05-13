import java.io.Serializable;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract class Artigo implements Serializable {
    private Transportadora t;
    private String id;
    private String descricao;
    private String marca;
    private double preco;
    private double desconto;
    private LocalDate data;
    private double avaliacao;
    private int donos;
    private Boolean categoria; //SE É PREMIUM == TRUE OU NÃO == FALSE;

    public Artigo() {
            this.t = new Transportadora();
            this.id = UUID.randomUUID().toString();;
            this.descricao = "";
            this.marca = "";
            this.preco=0.0;
            this.desconto=0.0;
            this.data = LocalDate.now();
            this.avaliacao = 0.0;
            this.donos = 0;
            this.categoria = Boolean.FALSE;

    }
    public Artigo(Transportadora t, String descricao, String m, double p, double des, LocalDate da, double a, int d, Boolean c) {
        this.t = t;
        this.id = UUID.randomUUID().toString();
        this.descricao = descricao;
        this.marca = m;
        this.preco=p;
        this.desconto=des;
        this.data = da;
        if(a>=0 && a<=5) { this.avaliacao = a;}
        if (a<0) this.avaliacao=0;
        if (a>5) this.avaliacao=5;
        if (d>0) this.donos=d;
        if (d<0) this.donos=0;
        this.categoria=c;
    }
    public Artigo(Artigo a) {
        this.t = a.getTransportadora();
        this.id = a.getId();
        this.descricao = a.getDescricao();
        this.marca = a.getMarca();
        this.preco=a.getPreco();
        this.desconto=a.getDesconto();
        this.data = a.getData();
        this.avaliacao = a.getAvaliacao();
        this.donos = a.getDonos();
        this.categoria = a.getCategoria();

    }

    public Transportadora getTransportadora() {return this.t;}

    public void setTransportadora(Transportadora ta) {this.t=ta;}
    public String getDescricao() {return this.descricao;}
    public void setDescricao(String d) {this.descricao=d;}
    public String getMarca() {return this.marca;}
    public void setMarca(String m) {this.marca = m;}
    public String getId() {return this.id;}
    public void setId(String id) {this.id = id;}
    public double getPreco() {return this.preco;}
    public void setPreco(double p) {this.preco=p;}
    public double getDesconto() {return this.desconto;}
    public void setDesconto(double d) {this.desconto=d;}
    public LocalDate getData() {return this.data;}
    public void setData(LocalDate d) {this.data=d;}
    public double getAvaliacao() {return this.avaliacao;}
    public void setAvaliacao(double a) {this.avaliacao=a;}
    public int getDonos() {return this.donos;}
    public void setDonos(int d) {this.donos=d;}
    public boolean getCategoria() {return this.categoria;}
    public void setCategoria(boolean c) {this.categoria=c;}

    public int veriftpArtigo(String s1) {
        int res = 0;
        if (s1.equalsIgnoreCase("sapatilha")) res = 1;
        if (s1.equalsIgnoreCase("tshirt")) res = 2;
        if (s1.equalsIgnoreCase("mala")) res = 3;
        return res;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artigo artigo = (Artigo) o;

        if (Double.compare(artigo.preco, preco) != 0) return false;
        if (Double.compare(artigo.desconto, desconto) != 0) return false;
        if (!t.equals(artigo.t)) return false;
        if (!descricao.equals(artigo.descricao)) return false;
        if (!marca.equals(artigo.marca)) return false;
        if (id!=(artigo.id)) return false;
        if (!data.equals(artigo.data)) return false;
        if (avaliacao!=(artigo.avaliacao)) return false;
        if (donos!=(artigo.donos)) return false;
        return categoria.equals(artigo.categoria);
    }


    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append(" ID: ").append(this.id)
                .append(" Transportadora: ").append(this.t.getNome())
                .append(" Descrição: ").append(this.descricao)
                .append(" Marca: ").append(this.marca)
                .append(" Preco: ").append(this.preco)
                .append(" Desconto: ").append(this.desconto)
                .append(" Data: ").append(this.data)
                .append(" Avaliação: ").append(this.avaliacao)
                .append(" Donos: ").append(this.donos)
                .append(" Categoria: ").append(this.categoria);
        return sb.toString();
    }

    public abstract Artigo clone();
    public abstract double calculaPreco();
}
