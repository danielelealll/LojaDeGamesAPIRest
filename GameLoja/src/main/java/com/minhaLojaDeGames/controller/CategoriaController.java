package com.minhaLojaDeGames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minhaLojaDeGames.model.CategoriaModel;
import com.minhaLojaDeGames.repository.CategoriaRepository;

@RestController // informa para o spring que esta classe se trata de um controle
@RequestMapping("/categoria") // por qual url esta classe será acessada
public class CategoriaController { // classe

	@Autowired // responsabilidade de instanciamento de interface para a anotação spring. Será acessado a partir do controller
	private CategoriaRepository repository; // serviço

	@GetMapping // mostrando que sempre que vir uma requisição externa e se o método for get, ele vai disparar o método get mapping
	public ResponseEntity<List<CategoriaModel>> GetAll() { //tipo de método get all, pegar todos os dados
		return ResponseEntity.ok(repository.findAll()); // find all, encontrar todos os dados
	}

	// para uma busca por ID
	@GetMapping("/{id}") // método http enviado para a nossa api
	private ResponseEntity<CategoriaModel> getById(@PathVariable long id){ //método pegar por id //Pathvariable: caminho da variável da url
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)) // esse método pode devolver tanto postagem, quanto um not found caso o objeto não exista ou exista um erro na requisição
				// caso capture resposta positiva, devolver como recurso na requisição
				.orElse(ResponseEntity.notFound().build()); // caso não tiver dados
		// assim que for feita alguma requisição do tipo get em "/categoria",e se passar um atributo, no caso o id, vai ser acessado este método que irá capturar qual é a variável que estamos recebendo dentro do @pathvariable, assim, retornando a interface injetada com @autowired
		}

	@GetMapping("/{descricao}") //subcaminho e atributo, para não dar ambiguidade de  caminho com o acima
	public ResponseEntity<List<CategoriaModel>> GetByCategoria(@PathVariable String descricao) { // método pegar por descrição
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(descricao)); //find all, encontrar todos os dados referentes a descrição e não discernir por letra maiúscula ou minúscula
	}

	@PostMapping // anotação para mapear post solicitações de http em métodos de tratamente específicos
	public CategoriaModel postCategoria(@RequestBody CategoriaModel categoria) { // objeto grande, com todos os atributos inseridos a ele
		repository.save(categoria);
		return categoria; // end point de postagem
	}

	@PutMapping //anotação para mapear put solicitações de http em métodos de tratamento específicos
	public CategoriaModel putCategoria(@PathVariable long id, @RequestBody CategoriaModel categoria) { //objeto grande, com todos os atributos inseridos a ele
		categoria.setId(id);
		repository.save(categoria); // end point de postagem
		return categoria;
	}
	@DeleteMapping("/{id}") //anotações mapeia solicitações http delete para métodos de tratamento específicos
	public void deleteCategoria(@PathVariable long id) { // retornar status ok, não retorna nada
			repository.deleteById(id); // pedindo no repositório o delete pelo id
		}
	
}

