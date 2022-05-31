package com.residencia.comercio.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.comercio.dtos.ProdutoDTO;
import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/produto")
@Validated
public class ProdutoController {

	@Autowired
	ProdutoService produtoService;

	@Operation(summary = "resgata a lista de produtos", description = "resgata a lista de produtos", responses = {
			@ApiResponse(responseCode = "200", description = "Get realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao realizar o Get.") })
	@GetMapping
	public ResponseEntity<List<Produto>> findAll() {
		List<Produto> produtoList = produtoService.findAll();

		if (produtoList.isEmpty()) {
			throw new NoSuchElementException("Não foram encontrado produtos");
		} else
			return new ResponseEntity<>(produtoService.findAll(), HttpStatus.OK);
	}

	@Operation(summary = "resgata o produto pelo seu ID", description = "Informe o ID do produto para obter as informações sobre ele", responses = {
			@ApiResponse(responseCode = "200", description = "Get realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao realizar o Get.") })
	@GetMapping("/{id}")
	public ResponseEntity<Produto> findById(@PathVariable Integer id) {
		Produto produto = produtoService.findById(id);
		if (null == produto) {
			throw new NoSuchElementException("Não foi encontrado o produto com o ID" + id);
		} else
			return new ResponseEntity<>(produto, HttpStatus.OK);
	}

	@GetMapping("/request")
	public ResponseEntity<Produto> findByIdRequest(
			@RequestParam @NotBlank(message = "O id deve ser preenchido.") Integer id) {
		return new ResponseEntity<>(null, HttpStatus.CONTINUE);
	}

	@Operation(summary = "Inserir um produto na Database", description = "No corpo da requisição passe as informações necessárias para inserir um produto", responses = {
			@ApiResponse(responseCode = "200", description = "Post realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao realizar o Post.") })
	@PostMapping
	public ResponseEntity<Produto> saveProduto(@Valid @RequestBody Produto produto) {
		produtoService.updateProduto(produto);
		return new ResponseEntity<>(produtoService.saveProduto(produto), HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	public ResponseEntity<ProdutoDTO> saveProdutoDTO(@RequestBody ProdutoDTO produtoDTO) {
		return new ResponseEntity<>(produtoService.saveProdutoDTO(produtoDTO), HttpStatus.CREATED);
	}

	@Operation(summary = "Atualiza um produto na Database", description = " No corpo da requisição passe as informações necessárias para atualizar um produto", responses = {
			@ApiResponse(responseCode = "200", description = "Atualização realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao atualizar um produto.") })
	@PutMapping
	public ResponseEntity<Produto> updateProduto(@RequestBody Produto produto) {
		Produto produtoUpdate = produtoService.updateProduto(produto);
		return new ResponseEntity<>(produtoUpdate, HttpStatus.OK);
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<Produto> update(@PathVariable Integer id, @RequestBody Produto produto){
//		Produto produtoAtualizado = produtoService.updateComId(produto, id);
//		if(null == produtoAtualizado)
//			return new ResponseEntity<>(produtoAtualizado, HttpStatus.BAD_REQUEST);
//		else
//			return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
//	}

	@Operation(summary = "Deleta um produto da Database", description = "Informe o ID de um produto que deseja deletar", responses = {
			@ApiResponse(responseCode = "200", description = "Exclusão realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao deletar um produto.") })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProdutoById(Integer id) {
		produtoService.deleteProdutoById(id);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

}
