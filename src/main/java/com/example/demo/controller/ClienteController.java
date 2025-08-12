package com.example.demo.controller;

import com.example.demo.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/criar")
    public String formCriarCliente() {
        return "criar";
    }

    @PostMapping("/criar")
    public String criarCliente(@RequestParam String nome,
                               @RequestParam String sobrenome,
                               @RequestParam String cpf,
                               Model model) {
        model.addAttribute("nome", nome);
        model.addAttribute("sobrenome", sobrenome);
        model.addAttribute("cpf", cpf);

        Cliente cliente = new Cliente(nome, sobrenome,cpf);
        clienteRepository.save(cliente);

        return "sucesso";
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<Double> getSaldoCliente(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Double saldo = cliente.getSaldo();
        return ResponseEntity.ok(saldo);
    }
}

