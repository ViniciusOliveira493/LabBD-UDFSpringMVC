package br.com.viniciusOliveira.AtividadeSpringUDF.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.viniciusOliveira.AtividadeSpringUDF.model.Produto;
import br.com.viniciusOliveira.AtividadeSpringUDF.persistence.ProdutoDAO;

@Controller
public class ProdutoServlet{
	private Produto produto;
	private List<Produto> produtos = new ArrayList<>();
	private String erro;
	private String msg;
	
	@Autowired
	ProdutoDAO pdao;
	
	@RequestMapping(name = "produto", value = "/produto", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model) {
		limparVariaveis();
		return retorno(model);
	}
	
	@RequestMapping(name = "produto", value = "/produto", method = RequestMethod.POST)
	public  ModelAndView post(ModelMap model,@RequestParam Map<String,String> allParam) {	
		limparVariaveis();
		String btn = allParam.get("btn");
		Produto p = obterProduto(allParam);
		
		switch (btn) {
		case "Cadastrar":
			msg = cadastrar(p);
			break;
		case "Atualizar":
			msg = atualizar(p);
			break;
		case "Excluir":
			msg = excluir(p);
			break;
		default:
			produto = buscar(p);
			break;
		}
		
		return retorno(model);
	}	
	
	private String cadastrar(Produto p) {
		try {
			return pdao.insert(p);
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		}
		return msgErro();
	}
	
	private String atualizar(Produto p) {
		try {
			return pdao.update(p);
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		}
		return msgErro();
	}
	
	private String excluir(Produto p) {
		try {
			return pdao.delete(p);
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		}
		return msgErro();
	}
	
	private Produto buscar(Produto p) {
		try {
			return pdao.selectOne(p);
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		}
		return null;
	}
	
	private List<Produto> listar() {
		try {
			return pdao.selectAll();
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		}
		return new ArrayList<>();
	}
	
	private Produto obterProduto(Map<String,String> allParam) {
		Produto p = new Produto();
		p.setCodigo(Integer.parseInt(allParam.get("txtCodigo")));
		p.setNome(allParam.get("txtNome"));
		p.setValorUnitario(Double.parseDouble(allParam.get("txtValor")));
		p.setQtdEstoque(Integer.parseInt(allParam.get("txtQtdEstoque")));
		
		return p;
	}
	
	private ModelAndView retorno(ModelMap mdmap) {		
		produtos = listar();
		//printData();
		mdmap.addAttribute("produto",produto);
		mdmap.addAttribute("produtos",produtos);
		mdmap.addAttribute("erro",erro);
		mdmap.addAttribute("mensagem",msg);
		return new ModelAndView("index");
	}
	
	private String msgErro() {
		return " | um erro ocorreu";
	}
	
	private void printData() {
		System.out.println(produto);
		System.out.println(erro);
		System.out.println(msg);
		
		System.out.println("-------------- Produtos ------------");
		for(Produto p:produtos) {
			System.out.println(p);
		}
	}
	
	private void limparVariaveis() {
		produto = new Produto();
		erro = "";
		msg = "";
		produtos = new ArrayList<>();
	}
}
