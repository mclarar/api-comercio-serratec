package com.residencia.comercio.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.residencia.comercio.dtos.CategoriaDTO;
import com.residencia.comercio.dtos.ProdutoDTO;
import com.residencia.comercio.entities.Categoria;
import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.repositories.CategoriaRepository;
import com.residencia.comercio.repositories.FornecedorRepository;
import com.residencia.comercio.repositories.ProdutoRepository;


@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	FornecedorRepository fornecedorRepository;

	public List<Produto> findAll() {

		return produtoRepository.findAll();
	}

	public Produto findById(Integer Id) {
		return produtoRepository.findById(Id).isPresent() ? produtoRepository.findById(Id).get() : null;
	}

	public ProdutoDTO findProdutoDTOById(Integer id) {
		return produtoRepository.findById(id).isPresent() ? EntityProdutoToDTO(produtoRepository.findById(id).get())
				: null;
	}

	public Produto saveProduto(Produto produto) {
		return produtoRepository.save(produto);

	}
	
	public ProdutoDTO saveProdutoDTO(ProdutoDTO produtoDTO) {

        Produto produto = new Produto();
        produto=produtoDTOtoEntity(produtoDTO);
        //categoria.setIdCategoria(categoriaDTO.getIdCategoria());
        produtoRepository.save(produto);

        //Categoria novoCategoria = categoriaRepository.save(categoria);

        return EntityProdutoToDTO(produto);
    }

	public Produto updateProduto(Produto produto) {
		
		return produtoRepository.save(produto);
	}

	public void deleteProdutoById(Integer id) {
		 Produto produto = produtoRepository.findById(id).get();
		produtoRepository.deleteById(id);
	}

	private ProdutoDTO EntityProdutoToDTO(Produto produto) {
		ProdutoDTO produtoDTO = new ProdutoDTO();

		produtoDTO.setCategoriaId(produto.getCategoria().getIdCategoria());
		produtoDTO.setCategoriaNome(produto.getCategoria().getNomeCategoria());
		produtoDTO.setFornecedorId(produto.getFornecedor().getIdFornecedor());
		produtoDTO.setFornecedorNome(produto.getFornecedor().getNomeFantasia());
		produtoDTO.setProdutoId(produto.getProdutoId());
		produtoDTO.setNomeProduto(produto.getNomeProduto());
		produtoDTO.setSku(produto.getSku());

		return produtoDTO;
	}

	private Produto produtoDTOtoEntity(ProdutoDTO produtoDTO) {
		Produto produto = new Produto();

		if (produtoDTO.getCategoriaId() != null) {
			produto.setCategoria(categoriaRepository.findById(produtoDTO.getCategoriaId()).get());
		}
		if (produtoDTO.getFornecedorId() != null) {
			produto.setFornecedor(fornecedorRepository.findById(produtoDTO.getFornecedorId()).get());
		}

		produto.setProdutoId(produtoDTO.getProdutoId());
		produto.setNomeProduto(produtoDTO.getNomeProduto());
		produto.setSku(produtoDTO.getSku());

		return produto;
	}

	public List<Produto> produtoDTOtoEntityList(List<ProdutoDTO> produtosDTO) {
		Produto produto = new Produto();
		List<Produto> listaProduto = new ArrayList<>();

		for (ProdutoDTO produtoDTO : produtosDTO) {
			if (produtoDTO.getCategoriaId() != null) {
				produto.setCategoria(categoriaRepository.findById(produtoDTO.getCategoriaId()).get());
			}
			if (produtoDTO.getFornecedorId() != null) {
				produto.setFornecedor(fornecedorRepository.findById(produtoDTO.getFornecedorId()).get());
			}

			produto.setProdutoId(produtoDTO.getProdutoId());
			produto.setNomeProduto(produtoDTO.getNomeProduto());
			produto.setSku(produtoDTO.getSku());

			listaProduto.add(produto);
		}

		return listaProduto;
	}
	
	public ResponseEntity<Produto> findByIdQuery(
			@RequestParam
			@NotBlank(message = "O sku deve ser preenchido.")
			String sku){
		return new ResponseEntity<>(null, HttpStatus.CONTINUE);
	}
}
