package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

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
        return "sucesso";
    }
}

    @GetMapping("/saldo")
    public Double getSaldo() {
        // Aqui você precisaria ter acesso ao cliente específico
        // Isso é apenas um exemplo básico
        if (cliente != null) {
            return cliente.getSaldo();
        }
        return null; // Ou lançar uma exceção apropriada
    }
}

    /*@GetMapping("/saldo")
    public String consultarSaldo() {
        if (cliente == null) return "Nenhum cliente cadastrado.";
        return "Saldo atual: R$" + cliente.getSaldo();
    }

    @GetMapping("/deposito")
    public String realizarDeposito(@RequestParam double valor) {
        if (cliente == null) return "Nenhum cliente cadastrado.";
        return "Saldo atual: R$ " + cliente.getSaldo();/*
    }
}

     */