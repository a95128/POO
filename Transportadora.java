import java.io.Serializable;
import java.time.LocalDate;

public class Transportadora implements Serializable {
    private String nome;
  //  private double preco_exp;
    public static final double IMPOSTO = 0.23;
    public static final double EPEQUENA = 0.90;
    public static final double EMEDIA = 1.70;
    public static final double EGRANDE = 2.50;
    public static final double LUCRO = 1.25;

    public Transportadora() {
        this.nome = "";
    //    this.preco_exp = 0;
    }

    public Transportadora(String n) {
        this.nome = n;
    //    this.preco_exp = p;
    }
    public String getNome() {return this.nome;}
    public void setNome(String n) {this.nome=n;}
   // public double getPrecoexp() {return this.preco_exp;}
   // public void setPrecoexp(double p) {this.preco_exp=p;}


    public double calculaprecoexp(int tamanho, Boolean b){
        double preco = 0.0;
        if(tamanho == 1 && b.equals(true)) preco = (EPEQUENA*2*LUCRO *(1+IMPOSTO))*0.9;
        if(tamanho == 1 && b.equals(false)) preco = (EPEQUENA*LUCRO *(1+IMPOSTO))*0.9;
        if(tamanho >= 2 && tamanho <= 5 && b.equals(true)) preco = (EMEDIA*2*LUCRO *(1+IMPOSTO))*0.9;
        if(tamanho >= 2 && tamanho <= 5 && b.equals(false)) preco = (EMEDIA*LUCRO *(1+IMPOSTO))*0.9;
        if(tamanho > 5 && b.equals(true)) preco = (EGRANDE*2*LUCRO *(1+IMPOSTO))*0.9;
        if(tamanho > 5 && b.equals(false)) preco = (EGRANDE*LUCRO *(1+IMPOSTO))*0.9;
        return preco;
    }

    /*
    public double precoFINAL(Encomenda e) {
        double precoinic = e.calcularPrecoEnc();
        double precoFINAL = 0.0;
        precoFINAL = precoinic + calculaprecoexp(e.getNrArtigosPorCategoria(false),false) + calculaprecoexp(e.getNrArtigosPorCategoria(true), true);
        return precoFINAL;
    }

     */

    public boolean equals(Object o){
        if (this==o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;

        Transportadora t = (Transportadora) o;
        return t.getNome() == this.nome;
            //    t.getPrecoexp() == this.preco_exp;
    }

}
