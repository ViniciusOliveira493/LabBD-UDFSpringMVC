package br.com.viniciusOliveira.AtividadeSpringUDF.model;

public class Produto {
	private int codigo;
	private String nome;
	private double valorUnitario;
	private int qtdEstoque;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public int getQtdEstoque() {
		return qtdEstoque;
	}
	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}
	
	@Override
	public String toString() {
		return codigo + tab() + nome + tab() + valorUnitario + tab() + qtdEstoque;
	}
	private String tab() {
		return " \t|\t";
	}
}
