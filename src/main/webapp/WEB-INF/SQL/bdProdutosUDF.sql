CREATE DATABASE bdProdutos
GO
USE bdProdutos
CREATE TABLE tbProduto(
	codigo			INT				NOT NULL
	, nome			VARCHAR(30)		NOT NULL
	, valorUnitario	DECIMAL(7,2)	NOT NULL
	, qtdEstoque	INT				NOT NULL
	PRIMARY KEY(codigo)
);
--========================================= PROCEDURE ==============================
CREATE PROCEDURE sp_produto(@op CHAR,@cod INT,@nome VARCHAR(30),@valorUnitario DECIMAL(7,2),@qtdEstoque INT, @res VARCHAR(30) OUTPUT)
AS
BEGIN
	IF (UPPER(@op) = 'I')
	BEGIN
		INSERT INTO tbProduto(codigo,nome,valorUnitario,qtdEstoque)
			VALUES
				(@cod,@nome,@valorUnitario,@qtdEstoque)
		SET @res = 'Produto cadastrado com sucesso'
	END

	IF (UPPER(@op) = 'D')
	BEGIN
		DELETE FROM  tbProduto WHERE codigo = @cod
		SET @res = 'Produto apagado com sucesso'
	END

	IF (UPPER(@op) = 'U')
	BEGIN
		UPDATE tbProduto
		SET 
			nome = @nome
			, valorUnitario = @valorUnitario
			, qtdEstoque = @qtdEstoque
		WHERE 
			codigo = @cod
		SET @res = 'Produto atualizado com sucesso'
	END

	IF (UPPER(@op) != 'I' AND UPPER(@op) != 'U' AND UPPER(@op) != 'D')
	BEGIN
		SET @res = 'operação inválida'
	END
END

--========================================= FUNCTIONS ==============================
CREATE FUNCTION fn_verificarQtdProdutosAbaixo(@qtdMinima INT) RETURNS int
AS
BEGIN
	DECLARE @qtd INT
	SELECT @qtd = COUNT(codigo)
	FROM tbProduto 
	WHERE qtdEstoque < @qtdMinima

	RETURN @qtd
END

--===================

CREATE FUNCTION fn_verificarProdutosAbaixo(@qtdMinima INT) 
RETURNS @tab TABLE(codigo INT,nome VARCHAR(30),qtd INT)
AS
BEGIN
	INSERT INTO @tab(codigo,nome,qtd)
	SELECT codigo,nome,qtdEstoque FROM tbProduto 
	WHERE qtdEstoque < @qtdMinima
	RETURN
END

--===========================================================