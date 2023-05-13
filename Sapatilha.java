import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Sapatilha extends Artigo{
    private double tamanho;
    private String fixadores; // atacadores == true e velcro == false
    private String cor;
    public Sapatilha() {
        super();
        this.tamanho = 0;
        this.fixadores = "";
        this.cor = "";
    }

    public Sapatilha(Sapatilha a){
        super(a);
        this.tamanho = a.getSTamanho();
        this.fixadores = a.getFixadores();
        this.cor = a.getCor();
    }

    public Sapatilha(Transportadora t, String descricao, String m, double p, double des, LocalDate da, double a, int d, Boolean c, double ta, String b, String cr) {
        super(t,descricao,m,p,des,da,a,d,c);
        this.tamanho=ta;
        this.fixadores=b;
        this.cor=cr;
    }

    public double getSTamanho() {return this.tamanho;}
    public void setSTamanho(int t) {this.tamanho=t;}
    public String getFixadores() {return this.fixadores;}
    public void setFixadores(String b) {this.fixadores=b;}
    public String getCor() {return this.cor;}
    public void setCor(String c) {this.cor=c;}

    public void analisedesconto(int donos, int tamanho) {
        if(donos!=0 || tamanho > 45) setDesconto(0.0);

    }

    public Sapatilha clone() {return new Sapatilha(this);}

    @Override
    public double calculaPreco() {
        double res = 0.0;
        int donos = getDonos();
        double tamanho = getSTamanho();
        double desconto = 0.0;
        double avaliacao = getAvaliacao();
        LocalDate data = getData();
        double preco = getPreco();
        Boolean categoria = getCategoria();
        if (categoria.equals(false)) {
            if (donos > 1) {
                desconto = (100 - desconto) / 100;
                res = (preco - (preco / (donos * avaliacao))) * desconto;
            } else {
                if (tamanho > 45) {
                    desconto = (100 - desconto) / 100;

                    res = (preco - 0.1*(preco / (donos * avaliacao))) * desconto;
                } else {
                    setDesconto(0);
                    res = (preco - 0.1*(preco / (donos * avaliacao)));
                }
            }
        }

        else {
              res = preco + preco*(0.1+(ChronoUnit.YEARS.between(data, LocalDate.now()) / (donos * avaliacao)));
             }

        return res;};

    public boolean equals(Object Artigo) {
        if(this == Artigo) return true;
        if ((Artigo == null) || (this.getClass() != Artigo.getClass())) return false;
        Sapatilha s = (Sapatilha) Artigo;
        return(super.equals(s) & this.tamanho == (s.getSTamanho()))
                               & this.fixadores.equals(s.getFixadores())
                               & this.cor.equals(s.getCor());
    }

    public String toString() {
        return super.toString() +
                " Tamanho: " + tamanho +
                " Fixadores: " + fixadores +
                " Cor: " + cor;
    }

}


