<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Produtos</title>
		<link rel="stylesheet" type="text/css" href='<c:url value="./resources/css/styles.css" />'>
	</head>
	<body>
		<jsp:include page="components/menu.jsp" />
		<main>
			<div class="container">
				<form action="produto" method = "POST">
					<div>
						<p>Digite o código do Produto:&nbsp;</p>
						<input class="form-item" type = "number" name="txtCodigo" id="txtCodigo" min="0" value='<c:out value="${produto.codigo}"></c:out>'>
					</div>
					<div>
						<p>Digite o nome do Produto:&nbsp;</p>
						<input class="form-item" type = "text" name="txtNome" id="txtNome" value='<c:out value="${produto.nome}"></c:out>'>
					</div>
					<div>
						<p>Digite o valor do Produto:&nbsp;</p>
						<input class="form-item" type = "number" min="0" step="0.01" name="txtValor" id="txtValor" value='<c:out value="${produto.valorUnitario}"></c:out>'>
					</div>
					<div>
						<p>Digite o quantidade do Produto no estoque:&nbsp;</p>
						<input class="form-item" type = "number" name="txtQtdEstoque" id="txtQtdEstoque" value='<c:out value="${produto.qtdEstoque}"></c:out>'>
					</div>
					<div class="btns">
						<input type="submit" id="btn" name="btn" value="Cadastrar">
						<input type="submit" id="btn" name="btn" value="Atualizar">
						<input type="submit" id="btn" name="btn" value="Excluir">
						<input type="submit" id="btn" name="btn" value="Buscar">
					</div>
				</form>
			</div>
			<c:if test="${(not empty erro) || (not empty mensagem)}">
				<div class="container">
					<c:if test="${not empty erro}">
						<p class="erro">${erro}</p>
					</c:if>
					<c:if test="${not empty mensagem}">
						<p class="sucesso">${mensagem}</p>
					</c:if>
				</div>
			</c:if>
			<div class="container">
				<c:if test="${not empty produtos}">
					<div class="">
						<table class = "tbProdutos" border = "1">
							<thead>
								<tr>
									<th>
										Codigo
									</th>
									<th>
										Nome
									</th>
									<th>
										Valor Unitário
									</th>
									<th>
										Estoque
									</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${produtos}" var="p">
									<tr>
										<td>
											${p.codigo}
										</td>
										<td>
											${p.nome}
										</td>
										<td>
											${p.valorUnitario}
										</td>
										<td>
											${p.qtdEstoque}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</div>
		</main>
	</body>
</html>