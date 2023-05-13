import java.time.LocalDate;

public class TShirt extends Artigo {
    private String tamanho;
    private String padrao;
    public TShirt() {
        super();
        this.tamanho = "";
        this.padrao = "";
    }

    public TShirt(TShirt a){
        super(a);
        this.tamanho = a.getTTamanho();
        this.padrao = a.getPadrao();

    }

    public TShirt(Transportadora t, String descricao, String m, double p, double des, LocalDate da, double a, int d, Boolean c, String ta, String pa) {
        super(t,descricao,m,p,des,da,a,d,c);
        this.tamanho=ta;
        this.padrao=pa;
    }

    public String getTTamanho() {return this.tamanho;}
    public void setTTamanho(String t) {this.tamanho=t;}
    public String getPadrao() {return this.padrao;}
    public void setPadrao(String b) {this.padrao=b;}
@Override
    public TShirt clone() {return new TShirt(this);}

    @Override
    public double calculaPreco(){
        double res = 0.0;
        int donos = getDonos();
        String tamanho = getTTamanho();
        String padrao = getPadrao();
        double desconto=0.0;
        double avaliacao = getAvaliacao();
        LocalDate data = getData();
        double preco = getPreco();
        Boolean categoria = getCategoria();
        if(categoria.equals(false)) {
            if (!padrao.equalsIgnoreCase("liso"))
            {
                setDesconto(50);
                desconto = 0.50;
                res = (preco - (preco / (donos * avaliacao)))*desconto;
            }
            else {
                setDesconto(0);

                res = (preco - (preco / (donos * avaliacao)));
            }
        }
        else{
            if(!padrao.equalsIgnoreCase("liso"))
            {
                setDesconto(50);
                desconto = 0.50;
                res = (preco + (preco / (donos * avaliacao)))*desconto;
            }
            else {
                setDesconto(0);
                res = (preco + (preco / (donos * avaliacao)));
            }
        }
        return res;};


    public boolean equals(Object Artigo ) {
        if(this == Artigo) return true;
        if ((Artigo == null) || (this.getClass() != Artigo.getClass())) return false;
        TShirt T = (TShirt) Artigo;
        return(super.equals(T) & this.tamanho.equals(T.getTTamanho()))
                               & this.padrao.equals(T.getPadrao());
    }

    @Override
    public String toString() {
        return super.toString() +
                " Tamanho: " + tamanho +
                " Padrão: " + padrao ;
    }
}