
public class Main {
    public static void main(String[] args) {
        FintechDAO conn = new FintechDAO();
        conn.insert("entidade",  "2", "mcdonalds", "alexandre", "hg");
        conn.getAll("entidade");
        conn.desconectar();

    }
}
