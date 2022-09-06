package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import model.Funcao;
import model.Pessoa;
import org.json.simple.JSONObject;
import utils.ConexaoSocket;
import utils.ConversorClasseJSON;

/**
 *
 * @author Lucas de Liz Frutuoso e Matheus Henrique Maas
 */
public class ControllerFuncao {

    private String msg = "";
    private Funcao funcao;
    private ConversorClasseJSON conversorCJ;
    Scanner entrada = new Scanner(System.in);

    public void insereFuncao() {

        String operacao = "INSERT";
        funcao = new Funcao();
        conversorCJ = new ConversorClasseJSON();

        System.out.println("\nInsira o nome da Função: ");
        String nomefuncao = entrada.nextLine();
        funcao.setNome(nomefuncao);

        System.out.println("Informe o Setor da Função: ");
        String setorFuncao = entrada.nextLine();
        funcao.setSetor(setorFuncao);

        System.out.println("Informe o Salário da Função: ");
        double salarioFuncao = entrada.nextDouble();
        funcao.setSalario(salarioFuncao);

        msg = conversorCJ.FuncaoParaJson(funcao, operacao);
        System.out.println(msg);

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String retorno = conexaoSocket.call();
            System.out.println(retorno);

        } catch (IOException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listaFuncoes() {

        JSONObject funcaoJson = new JSONObject();
        funcaoJson.put("operacao", "LIST");
        funcaoJson.put("classe", "funcao");
        msg = funcaoJson.toJSONString();

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String resposta = conexaoSocket.call();

            System.out.println(resposta);
        } catch (IOException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscaFuncao() {

        System.out.println("Informe o nome da Função: ");
        String nomeFuncao = entrada.next();

        JSONObject pessoaJson = new JSONObject();
        pessoaJson.put("operacao", "GET");
        pessoaJson.put("classe", "funcao");
        pessoaJson.put("nome", nomeFuncao);
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

    public void deletaFuncao() {

        System.out.println("Informe o nome da Função: ");
        String nomeFuncao = entrada.next();

        JSONObject pessoaJson = new JSONObject();
        pessoaJson.put("operacao", "DELETE");
        pessoaJson.put("classe", "funcao");
        pessoaJson.put("nome", nomeFuncao);
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

    public void atualizaFuncao() {

        String operacao = "UPDATE";
        funcao = new Funcao();
        conversorCJ = new ConversorClasseJSON();

        System.out.println("\nInsira o nome da Função: ");
        String nomefuncao = entrada.nextLine();
        funcao.setNome(nomefuncao);

        System.out.println("Informe o Setor da Função: ");
        String setorFuncao = entrada.nextLine();
        funcao.setSetor(setorFuncao);

        System.out.println("Informe o Salário da Função: ");
        double salarioFuncao = entrada.nextDouble();
        funcao.setSalario(salarioFuncao);

        msg = conversorCJ.FuncaoParaJson(funcao, operacao);
        System.out.println(msg);

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String retorno = conexaoSocket.call();
            System.out.println(retorno);
            if (retorno != null) {
                System.out.println("Dados da Função: " + funcao.getNome() + " atualizados com sucesso!");
                funcao = new Funcao();
            }

        } catch (IOException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void associaFuncaoPessoa(Pessoa pessoa) {

        System.out.println(".............Aqui será mostrado as pessoas.........");

        System.out.println("Informe o CPF: ");
        String cpfPessoa = entrada.next();

        System.out.println(".............Aqui será mostrado as funções.........");

        System.out.println("Informe o nome da Função: ");
        String nomeFuncao = entrada.next();

        associaPessoa(cpfPessoa, nomeFuncao);

    }

    private String associaPessoa(String cpfPessoa, String nomeFuncao) {

        JSONObject funcaoJson = new JSONObject();
        funcaoJson.put("operacao", "INSERT");
        funcaoJson.put("entidade", "funcao");
        funcaoJson.put("cpf", cpfPessoa);
        funcaoJson.put("nomeFuncao", nomeFuncao);
        msg = funcaoJson.toJSONString();
        String resposta = "";

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            resposta = conexaoSocket.call();
        } catch (IOException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resposta;
    }
}
