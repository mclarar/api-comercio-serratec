package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.residencia.comercio.dtos.CategoriaDTO;
import com.residencia.comercio.entities.Categoria;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	
	@Autowired
	CategoriaService categoriaService;
	
	
	@Operation(summary = "Resgata a lista de categorias", description = "Resgata a lista de categorias", responses = {
			@ApiResponse(responseCode = "200", description = "Get realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao realizar o Get.") })
	@GetMapping
	public ResponseEntity<List<Categoria>> findAllCategoria() {
		List<Categoria> categoriaList = categoriaService.findAllCategoria();
		return new ResponseEntity<>(categoriaList, HttpStatus.OK);
	}

	@GetMapping("/dto/{id}")
	public ResponseEntity<CategoriaDTO> findCategoriaDTOById(@PathVariable Integer id) {
		CategoriaDTO categoriaDTO = categoriaService.findCategoriaDTOById(id);
		return new ResponseEntity<>(categoriaDTO, HttpStatus.OK);
	}

	@Operation(summary = "Resgata a categoria pelo seu ID", description = "Informe o ID da categoria para obter as informações sobre ela", responses = {
			@ApiResponse(responseCode = "200", description = "Get realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao realizar o Get.") })
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> findCategoriaById(@PathVariable Integer id) {
		Categoria categoria = categoriaService.findCategoriaById(id);
		if (null == categoria)
			throw new NoSuchElementFoundException("Não foi encontrado Categoria com o id " + id);
		else
			return new ResponseEntity<>(categoria, HttpStatus.OK);
	}

	@Operation(summary = "Insere uma categoria da Database", description = "Informe o ID de uma categoria que deseja inserir", responses = {
			@ApiResponse(responseCode = "200", description = "Exclusão realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao deletar uma categoria.")
	})
	@PostMapping
	public ResponseEntity<Categoria> saveCategoria(@Valid @RequestBody Categoria categoria) {
		Categoria novoCategoria = categoriaService.saveCategoria(categoria);
		return new ResponseEntity<>(novoCategoria, HttpStatus.CREATED);
	}
	
	
	

	@PostMapping("/dto")
	public ResponseEntity<CategoriaDTO> saveCategoriaDTO(@RequestBody CategoriaDTO categoriaDTO) {
		CategoriaDTO novoCategoriaDTO = categoriaService.saveCategoriaDTO(categoriaDTO);
		return new ResponseEntity<>(novoCategoriaDTO, HttpStatus.CREATED);
	}

	@PostMapping(value = "/com-foto", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Categoria> saveCategoriaComFoto(@RequestPart("categoria") String categoria, @RequestPart("file") MultipartFile file) throws Exception{
			Categoria novaCategoria = categoriaService.saveCategoriaComFoto(categoria, file);
			return new ResponseEntity<>(novaCategoria, HttpStatus.CREATED);
		
	}

	@Operation(summary = "Atualiza uma categoria na Database", description = " No corpo da requisição passe as informações necessárias para atualizar uma categoria", responses = {
			@ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao atualizar uma categoria.")
	})
	@PutMapping
	public ResponseEntity<Categoria> updateCategoria(@Valid @RequestBody Categoria categoria) {
		Categoria novoCategoria = categoriaService.updateCategoria(categoria);
		return new ResponseEntity<>(novoCategoria, HttpStatus.OK);
	}

	
	@Operation(summary = "Deleta uma categoria da Database", description = "Informe o ID de uma categoria que deseja deletar", responses = {
			@ApiResponse(responseCode = "200", description = "Exclusão realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao deletar uma categoria.")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategoria(@PathVariable Integer id) {
		if (null == categoriaService.findCategoriaById(id))
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);

		categoriaService.deleteCategoria(id);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

}
