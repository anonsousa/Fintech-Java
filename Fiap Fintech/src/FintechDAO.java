import java.sql.*;

public class FintechDAO {
    private Connection conn = null;
    private String url, user, pass;

    public FintechDAO(){
        conectar();
    }

    private void conectar(){
        try {
            Class.forName("oracle.jdbc.OracleDriver");//Driver
            url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
            user = "RM551647";
            pass = "260698";
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Conectado ao banco.");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void desconectar(){
        try {
            conn.close();
            System.out.println("Desconectou com sucesso");
        } catch (Exception e){
            System.out.println("Erro em desconectar");
        }
    }

    //INSERIR DADOS
    public void insert(String tabela, String... valores) {
        try {
            if (conn != null) {
                // Monta a consulta
                StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
                sqlBuilder.append(tabela);
                sqlBuilder.append(" VALUES (");
                for (int i = 0; i < valores.length; i++) {
                    if (i > 0) {
                        sqlBuilder.append(", ");
                    }
                    sqlBuilder.append("?");
                }
                sqlBuilder.append(")");
                String sql = sqlBuilder.toString();

                PreparedStatement statement = conn.prepareStatement(sql);
                for (int i = 0; i < valores.length; i++) {
                    statement.setString(i + 1, valores[i]);
                }

                statement.executeUpdate();
                statement.close();
                System.out.println("Dados inseridos com sucesso");
            } else {
                System.out.println("Não foi possível inserir os dados");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados: " + e.getMessage());
        }
    }

    //GETALL DATA
    public void getAll(String tabela) {
        try {
            if (conn != null) {
                String sql = "SELECT * FROM " + tabela;
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int numColumns = metaData.getColumnCount();

                // Imprimir cabeçalho das colunas
                for (int i = 1; i <= numColumns; i++) {
                    String columnName = metaData.getColumnName(i);
                    System.out.printf("%-20s", columnName); // Aqui eu defino a largura das colunas
                }
                System.out.println();

                while (resultSet.next()) {
                    for (int i = 1; i <= numColumns; i++) {
                        String columnValue = resultSet.getString(i);
                        System.out.printf("%-20s", columnValue); // Aqui eu defino a largura das colunas
                    }
                    System.out.println();
                }

                resultSet.close();
                statement.close();
            } else {
                System.out.println("Não foi possível recuperar os dados, erro na conexão");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao recuperar dados: " + e.getMessage());
        }
    }
}