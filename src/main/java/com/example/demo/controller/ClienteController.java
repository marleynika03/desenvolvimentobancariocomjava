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
    @GetMapping("/saldo/{clienteId}")
    public ResponseEntity<Double> getSaldoCliente(@PathVariable Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Double saldo = cliente.getSaldo();
        return ResponseEntity.ok(saldo);
    }

    // Exibe o formulário de saque para um cliente específico
    @GetMapping("/sacar/{clienteId}")
    public String formSacar(@PathVariable Long clienteId,
                            @RequestParam(required = false) String erro,
                            @RequestParam(required = false) String sucesso,
                            @RequestParam(required = false) Double valor,
                            Model model) {
        model.addAttribute("clienteId", clienteId);
        if ("true".equals(sucesso)) {
            model.addAttribute("mensagem", "Saque de R$" + valor + "realizado com sucesso!");
            model.addAttribute("sucesso", true);
        } else if ("cliente-nao-encontrando".equals(erro)) {
            model.addAttribute("mensagem", "Cliente não encontrado!");
            model.addAttribute("erro", true);
        } else if ("valor-invalido".equals(erro)) {
            model.addAttribute("mensagem", "Valor do saque inválido!");
            model.addAttribute("erro", true);
        }
        return "sacar";
    }

    // Processa o formulário de depósito
    @PostMapping("/sacar/{clienteId}")
    public String sacarSaldo(@PathVariable Long clienteId,
                             @RequestParam Double valor) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente == null) {
            return "redirect:/clientes/sacar" + clienteId + "?erro=cliente-nao-encontrado";
        }

        if (valor != null && valor > 0) {
            cliente.sacarSaldo(valor);
            clienteRepository.save(cliente);
            return "redirect:/clientes/sacar/" + clienteId + "?sucesso=true&valor=" + valor;
        } else {
            return "redirect:/clientes/sacar" + clienteId + "?erro=valor-invalido";
        }
    }

    // Exibe o formulário de depósito para um cliente específico
    @GetMapping("/depositar/{clienteId}")
    public String formDepositar(@PathVariable Long clienteId,
                                @RequestParam(required = false) String erro,
                                @RequestParam(required = false) String sucesso,
                                @RequestParam(required = false) Double valor,
                                Model model) {
        model.addAttribute("clienteId", clienteId);

        // Tratamento das mensagens
        if ("true".equals(sucesso)) {
            model.addAttribute("mensagem", "Depósito de R$" + valor + " realizado com sucesso!");
            model.addAttribute("sucesso", true);
        } else if ("cliente-nao-encontrado".equals(erro)) {
            model.addAttribute("mensagem", "Cliente não encontrado!");
            model.addAttribute("erro", true);
        } else if ("valor-invalido".equals(erro)) {
            model.addAttribute("mensagem", "Valor do depósito inválido!");
            model.addAttribute("erro", true);
        }

        return "depositar";
    }

    // Processa o formulário de depósito
    @PostMapping("/depositar/{clienteId}")
    public String depositarSaldo(@PathVariable Long clienteId,
                                 @RequestParam Double valor) {
        // Busca o cliente pelo ID
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        // Verifica se o cliente existe
        if (cliente == null) {
            return "redirect:/clientes/depositar/" + clienteId + "?erro=cliente-nao-encontrado";
        }

        // Valida e processa o depósito
        if (valor != null && valor > 0) {
            cliente.adicionarSaldo(valor);
            clienteRepository.save(cliente);
            return "redirect:/clientes/depositar/" + clienteId + "?sucesso=true&valor=" + valor;
        } else {
            return "redirect:/clientes/depositar/" + clienteId + "?erro=valor-invalido";
        }
    }
}