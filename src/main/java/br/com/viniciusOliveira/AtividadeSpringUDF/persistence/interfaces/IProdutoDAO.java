package br.com.viniciusOliveira.AtividadeSpringUDF.persistence.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.com.viniciusOliveira.AtividadeSpringUDF.model.Produto;

public interface IProdutoDAO {
	public String insert(Produto p) throws SQLException, ClassNotFoundException;
	public String update(Produto p) throws SQLException, ClassNotFoundException;
	public String delete(Produto p) throws SQLException, ClassNotFoundException;
	public Produto selectOne(Produto p) throws SQLException, ClassNotFoundException;
	public List<Produto> selectAll() throws SQLException, ClassNotFoundException;
	
	public int consultaQtdForaEstoque(int qtdMinima) throws SQLException;
	public List<Produto> consultaListaProdutosForaEstoque (int qtdMinima) throws SQLException;
}
