import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SCRIPT {

    public static Vintage script() {
        Vintage v = new Vintage();

        Transportadora t = new Transportadora("Amazon basic");
        Transportadora t1 = new Transportadora("Amazon prime");
        Transportadora t2 = new Transportadora("Ajshshs");
        Transportadora t3 = new Transportadora("thakjlkfn");
        Transportadora t4 = new Transportadora("kjvkj");

        Sapatilha s1 = new Sapatilha(t, "Sapatilhas brancas", "Nike", 80.5, 0, LocalDate.of(2022, 3, 10), 3.0, 3, false, 46.0, "Atacadores", "Branco");
        Sapatilha s2 = new Sapatilha(t1, "Sapatilhas preta", "Jordan", 100.0, 100.0, LocalDate.of(2023, 4, 10), 3.0, 3, true, 37.0, "Atacadores", "Preto");
        Sapatilha s3 = new Sapatilha(t, "Sapatilhas preta", "Jordan", 70, 50, LocalDate.of(2023, 4, 10), 3.0, 3, false, 37.0, "Atacadores", "Preto");
        Sapatilha s4 = new Sapatilha(t1, "Sapatilhas azuis", "Reebok", 70.0, 20.0, LocalDate.of(2023, 5, 13), 2.0, 5, true, 38.0, "Atacadores", "Azul");
        Sapatilha s5 = new Sapatilha(t, "Sapatilhas cinzentas", "New Balance", 100.0, 30.0, LocalDate.of(2023, 5, 13), 4.0, 6, false, 41.0, "Atacadores", "Cinzento");
        Sapatilha s6 = new Sapatilha(t, "Sapatilhas pretas", "Converse", 60.0, 0.0, LocalDate.of(2023, 5, 13), 3.5, 2, true, 37.0, "Velcro", "Preto");
        Sapatilha s7 = new Sapatilha(t4, "Sapatilhas brancas", "Vans", 75.0, 10.0, LocalDate.of(2023, 5, 13), 2.0, 3, false, 42.0, "Atacadores", "Branco");

        TShirt tshirt1 = new TShirt(t1, "Camisola verde", "Zara", 20.0, 10, LocalDate.of(2023, 4, 10), 2.0, 4, true, "S", "liSo");
        Mala m1 = new Mala(t1, "Prada basic", "Prada", 10500.0, 0.0, LocalDate.of(2020, 4, 10), 10.0, 1, true, 5.0, 3.0, "Pele");

        Utilizador u1 = new Utilizador("ajrochaalves@gmail.com", "Ana", "Rua Nova", 12554, "123", 100000.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        Utilizador u2 = new Utilizador("simao@gmail.com", "Simao", "yuhaobd", 1234, "afdsfgfsdf", 10.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        Utilizador u3 = new Utilizador("diogo@gmail.com","Diogo","Rua Tibães",543,"diogo123",1000.0,new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), new ArrayList<>());

        Encomenda e1 = new Encomenda(new ArrayList<>(), 50.0, "Finalizada", LocalDateTime.now());

        e1.addArtigo(s1);
        e1.addArtigo(s2);
        e1.addArtigo(s3);
        u2.getVendas().add(s1);
        u2.getVendas().add(s2);
        u2.getVendas().add(s3);
        u1.getVendas().add(s4);
        u1.getVendas().add(s5);
        u1.getVendas().add(s6);

        u1.getProdutosAdquiridos().add(m1);
        u1.getProdutosAdquiridos().add(s1);
        u1.getProdutosAdquiridos().add(s7);

        v.addUtilizador(u1);
        v.addUtilizador(u2);
        v.addUtilizador(u3);
        v.addEncomenda(e1);
        v.addTransportadora(t);
        v.addTransportadora(t1);
        v.addTransportadora(t2);
        v.addTransportadora(t3);
        v.addTransportadora(t4);
        v.addArtigo(s1);
        v.addArtigo(s2);
        v.addArtigo(s3);
        v.addArtigo(s4);
        v.addArtigo(s5);
        v.addArtigo(s6);
        v.addArtigo(s7);
        v.addArtigo(tshirt1);
        v.addArtigo(m1);

        return v;
    }
}




