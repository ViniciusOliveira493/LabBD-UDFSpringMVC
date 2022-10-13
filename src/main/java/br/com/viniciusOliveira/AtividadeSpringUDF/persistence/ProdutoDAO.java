package br.com.viniciusOliveira.AtividadeSpringUDF.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.viniciusOliveira.AtividadeSpringUDF.model.Produto;
import br.com.viniciusOliveira.AtividadeSpringUDF.persistence.interfaces.IProdutoDAO;

@Repository
public class ProdutoDAO implements IProdutoDAO{
	@Autowired
	Conexao cn;
	
	@Override
	public String insert(Produto p) throws SQLException, ClassNotFoundException{
		return modifyProduct(p, "I");
	}

	@Override
	public String update(Produto p) throws SQLException, ClassNotFoundException{
		return modifyProduct(p, "U");
	}

	@Override
	public String delete(Produto p) throws SQLException, ClassNotFoundException{
		return modifyProduct(p, "D");
	}

	@Override
	public Produto selectOne(Produto p) throws SQLException, ClassNotFoundException{
		Connection conn = cn.getConexao();
		String query = "SELECT"
						+ "	codigo AS c"
						+ " ,nome AS n"
						+ " ,valorUnitario AS vl"
						+ " ,qtdEstoque AS e"
					+ " FROM tbProduto "
					+ " WHERE codigo = ?";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setInt(1, p.getCodigo());
		ResultSet rs = pstm.executeQuery();
		while(rs.next()) {
			p.setCodigo(rs.getInt("c"));
			p.setNome(rs.getString("n"));
			p.setValorUnitario(rs.getDouble("vl"));
			p.setQtdEstoque(rs.getInt("e"));
		}
		rs.close();
		pstm.close();
		cn.close(conn);
		return p;
	}
	
	@Override
	public List<Produto> selectAll() throws SQLException, ClassNotFoundException {
		Connection conn = cn.getConexao();
		List<Produto> produtos = new ArrayList<>();
		String query = "SELECT"
				+ "	codigo AS c"
				+ " ,nome AS n"
				+ " ,valorUnitario AS vl"
				+ " ,qtdEstoque AS e"
			+ " FROM tbProduto ";
		PreparedStatement pstm = conn.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()) {
			Produto p = new Produto();
			p.setCodigo(rs.getInt("c"));
			p.setNome(rs.getString("n"));
			p.setValorUnitario(rs.getDouble("vl"));
			p.setQtdEstoque(rs.getInt("e"));
			produtos.add(p);
		}
		rs.close();
		pstm.close();
		cn.close(conn);
		return produtos;
	}
	
	private String modifyProduct(Produto p, String op) throws SQLException, ClassNotFoundException{
		Connection conn = cn.getConexao();
		String res = "";
		String query = "{CALL sp_produto(?,?,?,?,?,?)}";
		CallableStatement cs = conn.prepareCall(query);
		cs.setString(1, op);
		cs.setInt(2,p.getCodigo());
		cs.setString(3, p.getNome());
		cs.setDouble(4, p.getValorUnitario());
		cs.setInt(5, p.getQtdEstoque());
		cs.registerOutParameter(6, Types.VARCHAR);
		cs.execute();
		res = cs.getString(6);
		cs.close();
		cn.close(conn);
		return res;
	}

	@Override
	public int consultaQtdForaEstoque(int qtdMinima) throws SQLException {
		Connection conn = cn.getConexao();
		String query = "SELECT dbo.fn_verificarQtdProdutosAbaixo(?)";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setInt(1, qtdMinima);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()) {
			return rs.getInt(1);
		}
		rs.close();
		pstm.close();
		cn.close(conn);
		return -1;
	}

	@Override
	public List<Produto> consultaListaProdutosForaEstoque(int qtdMinima) throws SQLException {
		Connection conn = cn.getConexao();
		List<Produto> produtos = new ArrayList<>();
		String query = "SELECT"
				+ "	codigo AS c"
				+ " ,nome AS n"
				+ " ,qtd AS q"
			+ " FROM dbo.fn_verificarProdutosAbaixo(?)";
		PreparedStatement pstm = conn.prepareStatement(query);
		pstm.setInt(1, qtdMinima);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()) {
			Produto p = new Produto();
			p.setCodigo(rs.getInt("c"));
			p.setNome(rs.getString("n"));
			p.setQtdEstoque(rs.getInt("q"));
			produtos.add(p);
		}
		rs.close();
		pstm.close();
		cn.close(conn);
		return produtos;
	}
	

}
