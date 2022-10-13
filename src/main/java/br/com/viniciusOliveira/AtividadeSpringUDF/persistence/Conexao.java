package br.com.viniciusOliveira.AtividadeSpringUDF.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public class Conexao {
	private String server = "localhost";
	private String login = "root";
	private String senha = "12345678";
	private String bd = "bdProdutos";
	
	public Connection getConexao() {		
		return getConexaoSqlServer();		
	}
	
	public Connection getConexaoSqlServer() {
		String path = "jdbc:sqlserver://"+server+";"
        + "databaseName="+bd+";"
        + "user="+login+";"
        + "password="+senha+";"+
        "encrypt=true;trustServerCertificate=true";       
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(path);
			return conn;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void close(Connection cn) throws SQLException {
		cn.close();
	}
}

