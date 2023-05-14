import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//ADICIONAR SALDO
public class Utilizador implements Serializable {
    private UUID id;
    private String email;
    private String nome;
    private String morada;
    private int NIF;
    private String password;
    private double saldo;
    private List<Artigo> produtosAVenda;
    private List<Artigo> produtosAdquiridos;
    private List<Artigo> vendas;
    private List<Artigo> carrinho;

    public Utilizador() {
        this.id = UUID.randomUUID();
        this.email = " ";
        this.nome = " ";
        this.morada = " ";
        this.NIF = 0;
        this.password = " ";
        this.saldo = 0.0;
        this.produtosAVenda = new ArrayList<>();
        this.produtosAdquiridos = new ArrayList<>();
        this.vendas = new ArrayList<>();
        this.carrinho = new ArrayList<>();
    }

    public Utilizador (String e, String n, String m, int NIF,String p, double s, List<Artigo> a, List<Artigo> b, List<Artigo> d, List<Artigo> c) {
        this.id = UUID.randomUUID();
        this.email = e;
        this.nome = n;
        this.morada = m;
        this.NIF = NIF;
        this.password = p;
        this.saldo = s;
        this.produtosAVenda = a;
        this.produtosAdquiridos = b;
        this.vendas = d;
        this.carrinho = c;
    }

        public Utilizador(Utilizador a){
            this.id = a.getId();
            this.email = a.getEmail();
            this.nome = a.getNome();
            this.morada = a.getMorada();
            this.NIF = a.getNIF();
            this.password = a.getPassword();
            this.saldo = a.getSaldo();
            this.produtosAVenda = a.getProdutosAVenda();
            this.produtosAdquiridos = a.getProdutosAdquiridos();
            this.vendas = a.getVendas();
            this.carrinho = a.getCarrinho();
    }


    public UUID getId() {return this.id;}
    public void setId(UUID id) {this.id = id;}
    public String getEmail() {return this.email;}
    public void setEmail(String e) {this.email = e;}
    public String getNome() {return this.nome;}
    public void setNome(String n) {this.nome = nome;}
    public String getMorada() {return this.morada;}
    public void setMorada(String m) {this.morada = m;}
    public int getNIF() {return this.NIF;}
    public void setNIF(int nf) {this.NIF = nf;}
    public String getPassword() {return this.password;}
    public void setPassword(String p){this.password=p;}
    public double getSaldo() {return this.saldo;}
    public void setSaldo(double p){this.saldo=p;}
    public List<Artigo> getProdutosAVenda() {return this.produtosAVenda;}
    public void setProdutosAVenda(List<Artigo> pav) {this.produtosAVenda = pav;}
    public List<Artigo> getProdutosAdquiridos() {return this.produtosAdquiridos;}
    public void setProdutosAdquiridos(List<Artigo> pa) {this.produtosAdquiridos = pa;}
    public List<Artigo> getVendas() {return this.vendas;}
    public void setVendas(List<Artigo> vendas) {this.vendas = vendas;}

    public List<Artigo> getCarrinho() {return this.carrinho;}
    public void setCarrinho(List<Artigo> carrinho) {this.carrinho = carrinho;}
    public void adicionarProdutoAVenda(Artigo p) {
        //System.out.println(p);
        this.produtosAVenda.add(p.clone());
    }
    public List<Artigo> adicionarProdutoAdquirido(Artigo p) {this.produtosAdquiridos.add(p.clone());
        return this.produtosAdquiridos;
    }
    public List<Artigo> adicionarVenda(Artigo p) {
        this.saldo += p.getPreco();
        setSaldo(this.saldo);
        this.vendas.add(p.clone());
        return this.vendas;
    }

    public List<Artigo> adicionarCarrinho (Artigo p) {this.carrinho.add(p.clone());
        return this.carrinho;
    }

    public List<Artigo> removeCarrinho (String a ) {
        for (Artigo b : this.carrinho) {
            if (b.getId().equals(a)) {
                this.carrinho.remove(b.clone());
            }
        }
        return this.carrinho;
    }


    public double getTotalCarrinho(List<Artigo> a ) {
        double res = 0.0;
        for (Artigo b : a) {
            res += b.getPreco();
        }
        return res;
    }



    public Utilizador clone() {return new Utilizador(this);}


    public boolean equals(Object o ) {
            if(this == o) return true;
            if ((o == null) || (this.getClass() != o.getClass())) return false;
            Utilizador u = (Utilizador) o;
            return(super.equals(u) & this.id==(u.getId()))
                                   & this.email.equals(u.getEmail())
                                   & this.nome.equals(u.getNome())
                                   & this.morada.equals(u.getMorada())
                                   & this.NIF==u.getNIF()
                                   & this.saldo==u.getSaldo()
                                   & this.produtosAVenda.equals(u.getProdutosAVenda())
                                   & this.produtosAdquiridos.equals(u.getProdutosAdquiridos())
                                   & this.vendas.equals(u.getVendas());
        }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.id)
                .append("\nMAIL: ").append(this.email)
                .append("\nNOME: ").append(this.nome)
                .append("\nMORADA: ").append(this.morada)
                .append("\nNIF: ").append(this.NIF)
                .append("\nSaldo: ").append(this.saldo)

                .append("\n[PRODUTOS A VENDA]: ");
                if (this.produtosAVenda != null) {
                    for (Artigo artigo : this.produtosAVenda) {
                        sb.append("\n").append(artigo);
                    }
                }

                sb.append("\n[PRODUTOS ADQUIRIDOS]: ");
                if (this.produtosAdquiridos != null) {
                    for (Artigo artigo : this.produtosAdquiridos) {
                        sb.append("\n").append(artigo);
                    }
                }

               sb.append("\n[PRODUTOS VENDAS]: ");
               if (this.vendas != null) {
                   for (Artigo artigo : this.vendas) {
                       sb.append("\n").append(artigo);
                   }
               }

               sb.append("\n[CARRINHO]:");
               if (this.carrinho != null) {
               for(Artigo artigo : this.carrinho) {
               sb.append("\n").append(artigo);
               }
               }


        return sb.toString();
    }
}