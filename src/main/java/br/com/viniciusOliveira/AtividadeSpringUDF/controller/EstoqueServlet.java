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
public class EstoqueServlet {
	private int qtdForaEstoque;
	private List<Produto> listaProdutosForaEstoque = new ArrayList<Produto>();
	private String erro;
	private String msg;
	
	@Autowired
	ProdutoDAO pdao;
	
	@RequestMapping(name = "estoque", value = "/estoque", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model) {
		limparVariaveis();
		listaProdutosForaEstoque = consultaListaProdutosForaEstoque(0);
		return retorno(model);
	}
	
	@RequestMapping(name = "estoque", value = "/estoque", method = RequestMethod.POST)
	public ModelAndView post(ModelMap model,@RequestParam Map<String,String> allParam) {
		limparVariaveis();
		String btn = allParam.get("btn");
		int qtd = Integer.parseInt(allParam.get("txtQtd"));
		switch (btn) {
		case "Quantidade":
			qtdForaEstoque = consultaQtdForaEstoque(qtd);
			break;

		default:
			listaProdutosForaEstoque = consultaListaProdutosForaEstoque(qtd);
			break;
		}
		return retorno(model);
	}
	
	private int consultaQtdForaEstoque(int qtdMinima) {
		try {
			return pdao.consultaQtdForaEstoque(qtdMinima);
		} catch (SQLException e) {
			erro = e.getMessage();
			e.printStackTrace();
		}
		return -1;
	}
	
	private List<Produto> consultaListaProdutosForaEstoque(int qtdMinima){
		try {
			return pdao.consultaListaProdutosForaEstoque(qtdMinima);
		} catch (SQLException e) {
			erro = e.getMessage();
		}
		return new ArrayList<>();
	}
	
	private ModelAndView retorno(ModelMap mdmap) {
		mdmap.addAttribute("qtdForaEstoque",qtdForaEstoque);
		mdmap.addAttribute("erro",erro);
		mdmap.addAttribute("mensagem",msg);
		mdmap.addAttribute("produtos",listaProdutosForaEstoque);
		return new ModelAndView("estoque");
	}
	
	private void limparVariaveis() {
		erro = "";
		msg = "";
		listaProdutosForaEstoque = new ArrayList<>();
		qtdForaEstoque = 0;
	}
}
