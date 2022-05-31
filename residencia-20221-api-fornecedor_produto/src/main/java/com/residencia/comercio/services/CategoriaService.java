package com.residencia.comercio.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.residencia.comercio.dtos.CategoriaDTO;
import com.residencia.comercio.entities.Categoria;
import com.residencia.comercio.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ArquivoService arquivoService;
	
	@Autowired
	MailService mailService;

	public List<Categoria> findAllCategoria() {
		return categoriaRepository.findAll();
	}

	public Categoria findCategoriaById(Integer id) {
		return categoriaRepository.findById(id).isPresent() ? categoriaRepository.findById(id).get() : null;
	}

	public CategoriaDTO findCategoriaDTOById(Integer id) {
		Categoria categoria = categoriaRepository.findById(id).isPresent() ? categoriaRepository.findById(id).get()
				: null;

		CategoriaDTO categoriaDTO = new CategoriaDTO();
		if (null != categoria) {
			categoriaDTO = converterEntidadeParaDto(categoria);
		}
		return categoriaDTO;
	}

	public Categoria saveCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public CategoriaDTO saveCategoriaDTO(CategoriaDTO categoriaDTO) {

        Categoria categoria = new Categoria();
        categoria=convertDTOToEntidade(categoriaDTO);
        //categoria.setIdCategoria(categoriaDTO.getIdCategoria());
        categoriaRepository.save(categoria);

        //Categoria novoCategoria = categoriaRepository.save(categoria);

        return converterEntidadeParaDto(categoria);
    }

	public Categoria updateCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public void deleteCategoria(Integer id) {
		Categoria inst = categoriaRepository.findById(id).get();
		categoriaRepository.delete(inst);
	}

	public void deleteCategoria(Categoria categoria) {
		categoriaRepository.delete(categoria);
	}

	private Categoria convertDTOToEntidade(CategoriaDTO categoriaDTO) {
		Categoria categoria = new Categoria();

		categoria.setIdCategoria(categoriaDTO.getIdCategoria());
		categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
		return categoria;
	}

	private CategoriaDTO converterEntidadeParaDto(Categoria categoria) {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setIdCategoria(categoria.getIdCategoria());
		categoriaDTO.setNomeCategoria(categoria.getNomeCategoria());
		
		return categoriaDTO;
	}

	public Categoria saveCategoriaComFoto(String categoriaString, MultipartFile file) throws Exception {

		Categoria categoriaConvertida = new Categoria();

		try {
			ObjectMapper objMapper = new ObjectMapper();
			categoriaConvertida = objMapper.readValue(categoriaString, Categoria.class);
		} catch (IOException e) {
			System.out.println("Ocorreu um erro na conversão.");
		}

		Categoria categoriaBD = categoriaRepository.save(categoriaConvertida);
		categoriaBD.setNomeImagem(categoriaBD.getIdCategoria() + "_" + file.getOriginalFilename());
		Categoria categoriaAtualizada = categoriaRepository.save(categoriaBD);

//		Chamando o método que fará a cópia do arquivo para a pasta definida
		try {
            arquivoService.criarArquivo(categoriaBD.getIdCategoria() +"_"+file.getOriginalFilename(),file);
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro ao tentar copiar o arquivo - " + e.getStackTrace());
        }
        //String corpoEmail= "Foi cadastrada uma nova categoria"+categoriaAtualizada.toString();
        //emailService.enviarEmailTexto("teste@teste.com","Cadastro de Categoria", corpoEmail);

        mailService.enviarEmailTextoHtml("teste@teste.com","Cadastro de Categoria");
		return categoriaAtualizada;
	}
}
