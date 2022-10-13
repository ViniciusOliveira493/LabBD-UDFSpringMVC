<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Estoque</title>
		<link rel="stylesheet" type="text/css" href='<c:url value="./resources/css/styles.css" />'>
	</head>
	<body>
		<jsp:include page="components/menu.jsp" />
		<main>
			<div class="container">
				<form action="estoque" method = "POST">
					<div>
						<p>Digite a quantidade mínima do produto:&nbsp;</p>
						<input class="form-item" type = "number" name="txtQtd" id="txtQtd" min="0">					
					</div>
					
					<div class="btns">
						<input type="submit" id="btn" name="btn" value="Quantidade">
						<input type="submit" id="btn" name="btn" value="Lista">
					</div>
				</form>
			</div>
			<c:if test="${(qtdForaEstoque > 0) || (not empty erro) || (not empty mensagem)}">
				<div class="container">
					<c:if test="${qtdForaEstoque > 0}">
							<p class="sucesso">Existem ${qtdForaEstoque} produtos com estoque baixo</p>
						</c:if>
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