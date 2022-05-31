package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.comercio.dtos.CadastroEmpresaReceitaDTO;
import com.residencia.comercio.dtos.FornecedorDTO;
import com.residencia.comercio.entities.Fornecedor;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.FornecedorService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/fornecedor")
@OpenAPIDefinition(
        info = @Info(
                title = "API Comércio",
                description = "API comercio feita para disciplina Desenvolvimento de API Restful da Residência em TIC Software do Serratec 2022.1",
                version = "1.000",
                contact = @Contact(name = "Grupo 01", url = "http://serratec.org/", email = "grupo01.serratec.turma01@gmail.com")
                ))
public class FornecedorController {
	@Autowired
	FornecedorService fornecedorService;

	@Operation(summary = "Resgata a lista de fornecedores", description = "Resgata a lista de fornecedores", responses = {
			@ApiResponse(responseCode = "200", description = "Get realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao realizar o Get.")
	})
	@GetMapping
	public ResponseEntity<List<Fornecedor>> findAllFornecedor() {
		List<Fornecedor> fornecedorList = fornecedorService.findAllFornecedor();
		return new ResponseEntity<>(fornecedorList, HttpStatus.OK);
	}

	@GetMapping("/dto/{id}")
	public ResponseEntity<FornecedorDTO> findFornecedorDTOById(@PathVariable Integer id) {
		FornecedorDTO fornecedorDTO = fornecedorService.findFornecedorDTOById(id);
		return new ResponseEntity<>(fornecedorDTO, HttpStatus.OK);
	}
	
	@Operation(summary = "resgata o fornecedor pelo seu CNPJ", description = "Informe o CNPJ do fornecedor para obter as informações sobre ele", responses = {
			@ApiResponse(responseCode = "200", description = "Get realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao realizar o Get.")
	})
	@GetMapping("cnpj/{cnpj}")
	public ResponseEntity<CadastroEmpresaReceitaDTO> consultarDadosPorCnpj(String cnpj) {
		CadastroEmpresaReceitaDTO cadEmpresaDTO = fornecedorService.consultarDadosPorCnpj(cnpj);
		if (null == cadEmpresaDTO)
			throw new NoSuchElementFoundException("Não foram encontrados dados para o CNPJ informado.");
		else
			return new ResponseEntity<>(cadEmpresaDTO, HttpStatus.OK);
	}

	@Operation(summary = "resgata o fornecedor pelo seu ID", description = "Informe o ID do fornecedor para obter as informações sobre ele", responses = {
			@ApiResponse(responseCode = "200", description = "Get realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao realizar o Get.")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Fornecedor> findFornecedorById(@PathVariable Integer id) {
		Fornecedor fornecedor = fornecedorService.findFornecedorById(id);
		if (null == fornecedor)
			throw new NoSuchElementFoundException("Não foi encontrado Fornecedor com o id " + id);
		else
			return new ResponseEntity<>(fornecedor, HttpStatus.OK);
	}

	@Operation(summary = "Inserir um fornecedor na Database", description = "No corpo da requisição passe as informações necessárias para inserir um fornecedor", responses = {
            @ApiResponse(responseCode = "200", description = "Post realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar o Post.")
    })
    @PostMapping
    public ResponseEntity<Fornecedor> saveFornecedor(@Valid @RequestBody Fornecedor fornecedor) {
        return new ResponseEntity<>(fornecedorService.saveFornecedor(fornecedor), HttpStatus.CREATED);
    }

	@PostMapping("/completo")
	public ResponseEntity<Fornecedor> saveFornecedorCompleto(@RequestBody Fornecedor fornecedor) {
		Fornecedor novoFornecedor = fornecedorService.saveFornecedor(fornecedor);
		return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	public ResponseEntity<FornecedorDTO> saveFornecedorDTO(@RequestBody FornecedorDTO fornecedorDTO) {
		FornecedorDTO novoFornecedorDTO = fornecedorService.saveFornecedorDTO(fornecedorDTO);
		return new ResponseEntity<>(novoFornecedorDTO, HttpStatus.CREATED);
	}
	
	@PostMapping("/cnpj/{cnpj}")
    public ResponseEntity<FornecedorDTO> saveFornecedorCnpj(@PathVariable String cnpj) {
        return new ResponseEntity<>(fornecedorService.saveFornecedorByCnpj(cnpj), HttpStatus.CREATED);
    }
	

	@Operation(summary = "Atualiza um fornecedor na Database", description = " No corpo da requisição passe as informações necessárias para atualizar um fornecedor", responses = {
			@ApiResponse(responseCode = "200", description = "Atualização realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao atualizar um fornecedor.")
	})
	@PutMapping
	public ResponseEntity<Fornecedor> updateFornecedor(@RequestBody Fornecedor fornecedor) {
		Fornecedor novoFornecedor = fornecedorService.updateFornecedor(fornecedor);
		return new ResponseEntity<>(novoFornecedor, HttpStatus.OK);
	}
	
	
	@Operation(summary = "Deleta um fornecedor da Database", description = "Informe o ID de um fornecedor que deseja deletar", responses = {
			@ApiResponse(responseCode = "200", description = "Exclusão realizado com sucesso."),
			@ApiResponse(responseCode = "400", description = "Erro ao deletar um fornecedor.")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteFornecedor(@PathVariable Integer id) {
		if (null == fornecedorService.findFornecedorById(id))
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);

		fornecedorService.deleteFornecedor(id);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

	
	

}
