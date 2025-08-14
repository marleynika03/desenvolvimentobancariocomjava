package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente") // Nome da tabela em minúsculo no banco
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    @Column(length = 11, unique = true, nullable = false)
    private String cpf;
    private double saldo;
    private String senha;
    private String email;

    public Cliente() {
    }

    public Cliente(String nome, String sobrenome, String cpf, String email, String senha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.saldo = 0.0;
        this.senha = senha;
        this.email = email;
    }

    public void adicionarSaldo(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito de R$" + valor + "Realizado com sucesso");
        } else {
            System.out.println("Valor do depósito inválido");
        }
    }

    public void sacarSaldo(double valor) {
        if (valor > 0) {
            saldo -= valor;
            System.out.println("Saque de R$" + valor + "Realizado com sucesso");
        } else {
            System.out.println("Valor do saque inválido");
        }
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getSenha() { return senha; }
    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }
    public void depositar(Double valor) {
    }
}
