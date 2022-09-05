package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import model.Pessoa;
import org.json.simple.JSONObject;
import utils.ConexaoSocket;
import utils.ConversorClasseJSON;

/**
 *
 * @author Lucas de Liz Frutuoso e Matheus Henrique Maas
 */
public class ControllerPessoa {

    private String msg = "";
    private Pessoa pessoa;
    private ConversorClasseJSON conversorCJ;
    Scanner entrada = new Scanner(System.in);

    public void inserePessoa() {

        String operacao = "INSERT";
        pessoa = new Pessoa();
        conversorCJ = new ConversorClasseJSON();

        //entrada = new Scanner(System.in);
        System.out.println("\nInsira o nome da pessoa: ");
        String nomePessoa = entrada.nextLine();
        pessoa.setNome(nomePessoa);

        System.out.println("Informe o CPF da pessoa: ");
        String cpf = entrada.nextLine();
        pessoa.setCpf(cpf);

        System.out.println("Informe o endereço da pessoa: ");
        String enderecoPessoa = entrada.nextLine();
        pessoa.setEndereco(enderecoPessoa);

        msg = conversorCJ.PessoaParaJson(pessoa, operacao);
        System.out.println(msg);

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String retorno = conexaoSocket.call();
            System.out.println(retorno);

        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listaPessoas() {

        JSONObject pessoasJson = new JSONObject();
        pessoasJson.put("operacao", "LIST");
        pessoasJson.put("classe", "pessoa");
        msg = pessoasJson.toJSONString();

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String resposta = conexaoSocket.call();

            System.out.println(resposta);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscaPessoa() {
        //entrada = new Scanner(System.in);

        System.out.println("Informe o CPF da pessoa: ");
        String cpfPessoa = entrada.nextLine();

        JSONObject pessoaJson = new JSONObject();
        pessoaJson.put("operacao", "GET");
        pessoaJson.put("classe", "pessoa");
        pessoaJson.put("cpf", cpfPessoa);
        msg = pessoaJson.toJSONString();

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String resposta = conexaoSocket.call();

            System.out.println(resposta);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletaPessoa() {
        //entrada = new Scanner(System.in);

        System.out.println("Informe o CPF da pessoa: ");
        String cpfPessoa = entrada.next();

        JSONObject pessoaJson = new JSONObject();
        pessoaJson.put("operacao", "DELETE");
        pessoaJson.put("classe", "pessoa");
        pessoaJson.put("cpf", cpfPessoa);
        msg = pessoaJson.toJSONString();

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String resposta = conexaoSocket.call();

            System.out.println(resposta);
        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualizaPessoa() {

        String operacao = "UPDATE";
        pessoa = new Pessoa();
        conversorCJ = new ConversorClasseJSON();

        //entrada = new Scanner(System.in);
        System.out.println("Informe o CPF da pessoa: ");
        String cpf = entrada.nextLine();
        pessoa.setCpf(cpf);

        System.out.println("\nInsira o nome da pessoa: ");
        String nomePessoa = entrada.nextLine();
        pessoa.setNome(nomePessoa);

        System.out.println("Informe o endereço da pessoa: ");
        String enderecoPessoa = entrada.nextLine();
        pessoa.setEndereco(enderecoPessoa);

        msg = conversorCJ.PessoaParaJson(pessoa, operacao);
        System.out.println(msg);
        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String retorno = conexaoSocket.call();
            System.out.println(retorno);
            if (retorno != null) {
                System.out.println("Dados de " + pessoa.getNome() + " portador do CPF: " + pessoa.getCpf() + " atualizado com sucesso!");
                pessoa = new Pessoa();
            }

        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
