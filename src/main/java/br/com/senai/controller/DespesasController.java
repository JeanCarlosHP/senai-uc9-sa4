package br.com.senai.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.senai.model.Despesas;
import br.com.senai.repository.DespesaRepository;

@Controller
public class DespesasController {
	
	@Autowired
	private DespesaRepository despesaRepository;

	@GetMapping("/")
	public String paginaInicial() {
		return "index.html";
	}

	@GetMapping("/lista")
	public String listaDespesas(Model model) {
		List<Despesas> despesas = despesaRepository.findAll();
		model.addAttribute("despesas", despesas);
	
		double valorTotal = despesas.stream().mapToDouble(Despesas::getValor).sum();
		model.addAttribute("valorTotal", valorTotal);
		return "lista";	
	}		
	
	@GetMapping("/cadastroDespesa")
	public String cadastrar() {
		return "cadastro.html";
	}
	
	@PostMapping("/cadastrarDespesa")
	public String cadastraProduto(
		Despesas despesas,
		BindingResult result, 
		Model model
	) {
		if(result.hasErrors()) {
			return "/cadastro.html";
		}

		despesaRepository.save(despesas);
		return "redirect:/cadastroDespesa";
	}

	@GetMapping("/editarListaDespesas")
	public String listaDesepesas(Model model) {
		List<Despesas> despesas = despesaRepository.findAll();
		model.addAttribute("despesas", despesas);
		return "editarLista";
	}
	
	@PostMapping("/atualizar/{id}")
	public String atualizaDespesao(
		@PathVariable("id") long id, 
		@Valid Despesas despesas, 
		BindingResult result, 
		Model model
	) {
		if(result.hasErrors()) {
			despesas.setId(id);
			return "editar_despesa";
		}

		despesaRepository.save(despesas);
		return "redirect:/editarListaDespesas";
	}
	
	@PostMapping
	public void salvar(Despesas despesas) {
		despesaRepository.save(despesas);
	}
	
	@GetMapping("/editar/{id}")
	public String editarDespesa(@PathVariable ("id") long id, Model model) {
		Despesas despesas = despesaRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Identificador não é válido" + id));

		model.addAttribute("despesas", despesas);
		return "editar_despesa";
	}
	
	@GetMapping("/deletar/{id}")
	public String deletarDespesa(@PathVariable("id") long id, Model model) {
		Despesas despesas = despesaRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Identificador não é válido" + id));

		despesaRepository.delete(despesas);
		return "redirect:/editarListaDespesas";
	}
}
