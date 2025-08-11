package com.exemplo.demo.controller;

import com.exemplo.demo.model.Cliente;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private Cliente cliente; // simulação de 1 cliente (simulação temporária)

    @PostMapping("/criar")
    public String criarCliente(@RequestParam String nome,
                               @RequestParam String sobrenome,
                               @RequestParam String cpf) {
        cliente = new Cliente(nome, sobrenome, cpf);
        return "Cliente criado com sucesso";
    }

    @GetMapping("/saldo")
    public String consultarSaldo() {
        if (cliente == null) return "Nenhum cliente cadastrado.";
        return "Saldo atual: R$" + cliente.getSaldo();
    }

    @GetMapping("/deposito")
    public String realizarDeposito(@RequestParam double valor) {
        if (cliente == null) return "Nenhum cliente cadastrado.";
        return "Saldo atual: R$ " + cliente.getSaldo();
    }
}