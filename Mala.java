import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Mala extends Artigo {
    /*
    public static class Dimensao {
        private double largura;
        private double altura;

        public Dimensao() {
            this.largura = 0.0;
            this.altura = 0.0;
        }



        public Dimensao(double largura, double altura) {
            this.largura = largura;
            this.altura = altura;
        }
        public double getLargura() {return largura;}
        public void setLargura(double largura) {this.largura = largura;}
        public double getAltura() {return altura;}
        public void setAltura(double altura) {this.altura = altura;}

    }
    */

    //private Dimensao dimensao;
    private double largura;

    private double altura;
    private String material;
    public Mala() {
        super();
        this.largura = 0.0;
        this.altura = 0.0;
       // this.dimensao = new Dimensao();
        this.material = "";
    }

    public Mala(Mala a){
        super(a);
        this.largura = a.getLargura();
        this.altura = a.getAltura();
        //this.dimensao = a.getMDimensao();
        this.material = a.getMMaterial();
    }

    public Mala(Transportadora t, String descricao, String m, double p, double des, LocalDate da, double a, int d, Boolean c, double largura, double altura, String mt) {
        super(t,descricao,m,p,des,da,a,d,c);
        //this.dimensao=dimensao;
        this.largura = largura;
        this.altura=altura;
    }

    public double getLargura() {return this.largura;}
    public void setLargura(double largura) {this.largura=largura;}
    public double getAltura() {return this.altura;}
    public void setAltura(double altura) {this.altura=altura;}
    //public Dimensao getMDimensao() {return this.dimensao;}
    //public void setMDimensao(double largura, double altura) {this.dimensao= new Dimensao(largura,altura);}
    public String getMMaterial() {return this.material;}
    public void setMMaterial(String b) {this.material=b;}

    public Mala clone() {return new Mala(this);}

    @Override
    public double calculaPreco() { //NÃO PERCEBI A CENADO DESCONTO INVERSO
        double res = 0.0;
        int donos = getDonos();
        double desconto = getDesconto();
        double largura = getLargura();
        double altura = getAltura();
        double avaliacao = getAvaliacao();
        LocalDate data = getData();
        double preco = getPreco();
        int utilizacao = (int) ChronoUnit.YEARS.between(data, LocalDate.now());
        Boolean categoria = getCategoria();

        if (categoria.equals(false)) {
            if (donos > 1) {
                desconto = (100 - (desconto)) / 100;
                res = (preco - (preco / (donos * avaliacao))) * desconto;
            } else {
                  desconto = (100 - desconto) / 100;
                  res = (preco - 0.1*(preco / (donos * avaliacao))) * desconto;
                   }
            }

        else {
            res = preco + preco*(0.1+(ChronoUnit.YEARS.between(data, LocalDate.now())) / (donos * avaliacao));
        }

        return res;};

    public boolean equals(Object Artigo) {
        if(this == Artigo) return true;
        if ((Artigo == null) || (this.getClass() != Artigo.getClass())) return false;
        Mala m = (Mala) Artigo;
        return(super.equals(m) & this.largura == (m.getLargura())
                               & this.altura == (m.getAltura())
                               & this.material.equals(m.getMMaterial()));
    }

    public String toString() {
        return super.toString() +
                " Largura: " + largura +
                " Altura: " + altura +
                " Material: " + material ;
    }
}