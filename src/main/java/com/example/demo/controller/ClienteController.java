package com.example.demo.controller;

import com.example.demo.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.ClienteRepository;

// Controlador para operações relacionadas a clientes
@Controller
@RequestMapping("/clientes") // Define o prefixo para todas as rotas deste controlador
public class ClienteController {

    // Injeção do reposítório para acesso ao banco de dados
    @Autowired
    private ClienteRepository clienteRepository;

    //Exibe o formulário de criação de cliente
    @GetMapping("/criar")
    public String formCriarCliente() {
        return "criar";
    }

    // Processa o formulário de criação de cliente
    @PostMapping("/criar")
    public String criarCliente(@RequestParam String nome,
                               @RequestParam String sobrenome,
                               @RequestParam String cpf,
                               Model model) {
        // Adiciona os dados ao modelo para possível uso na view
        model.addAttribute("nome", nome);
        model.addAttribute("sobrenome", sobrenome);
        model.addAttribute("cpf", cpf);

        // Cria e salva o novo cliente no banco de dados
        Cliente cliente = new Cliente(nome, sobrenome, cpf);
        clienteRepository.save(cliente);

        return "sucesso";
    }

    // Endpoint para consultar o saldo de um cliente
    @GetMapping("/{id}/saldo")
    public ResponseEntity<Double> getSaldoCliente(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Double saldo = cliente.getSaldo();
        return ResponseEntity.ok(saldo);
    }

    // Exibe o formulário de depósito para um cliente específico
    @GetMapping("/depositar/{clienteId}")
    public String formDepositar(@PathVariable Long clienteId, Model model) {
        model.addAttribute("clienteId", clienteId); // Passa o ID para a view
        return "depositar"; // retorna a view de depósito;
    }

    //Processa o formulário de depósito
    @PostMapping("/depositar/{clienteId}")
    public String depositarSaldo(@PathVariable Long clienteId,
                                 @RequestParam Double valor,
                                 Model model) {
        // Busca o cliente pelo ID
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);


        // Verifica se o cliente existe
        if (cliente == null) {
            model.addAttribute("mensagem", "Cliente não encontrado!");
            model.addAttribute("erro", true);
            return "depositar";
        }

        // Valida e processa o depósito
        if (valor != null && valor > 0) {
            cliente.depositar(valor);
            clienteRepository.save(cliente);
            model.addAttribute("mensagem", "Depósito de R$" + valor + " Realizado com sucesso");
            model.addAttribute("sucesso", true);
        } else {
            model.addAttribute("mensagem", "Valor do depósito inválido");
            model.addAttribute("erro", true);
        }
        return "depositar"; // Retorna para a mesma página com mensagem
    }
}


