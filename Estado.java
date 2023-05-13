import java.time.LocalDate;

public class Estado {
    private LocalDate data;
    private double avaliacao;
    private int donos;
    private Boolean categoria; //SE É PREMIUM == TRUE OU NÃO == FALSE;

    public Estado() {
        this.data = LocalDate.now();
        this.avaliacao = 0.0;
        this.donos = 0;
        this.categoria = Boolean.FALSE;
    }

    public Estado(LocalDate da, double a, int d, Boolean c) {
        this.data = da;
        if(a>=0 && a<=5) { this.avaliacao = a;}
        if (a<0) this.avaliacao=0;
        if (a>5) this.avaliacao=5;
        if (d>0) this.donos=d;
        if (d<0) this.donos=0;
        this.categoria=c;
    }

    public LocalDate getData() {return this.data;}
    public void setData(LocalDate d) {this.data=d;}
    public double getAvaliacao() {return this.avaliacao;}
    public void setAvaliacao(double a) {this.avaliacao=a;}
    public int getDonos() {return this.donos;}
    public void setDonos(int d) {this.donos=d;}
    public boolean getCategoria() {return this.categoria;}
    public void setCategoria(boolean c) {this.categoria=c;}

    public boolean equals(Object o){
        if (this==o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;

        Estado e = (Estado) o;
        return e.getData() == this.data &&
                e.getAvaliacao() == this.avaliacao &&
                e.getDonos() == this.donos &&
                e.getCategoria() == this.categoria;
    }
}

