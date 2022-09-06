package controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import model.Funcao;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.ConexaoSocket;
import utils.ConversorClasseJSON;
import static view.MenuPrincipal.entrada;

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

    public void associaFuncaoPessoa() {

        System.out.println("Deseja vincular uma pessoa a uma Função? [s/n]");
        String opcao = entrada.nextLine();

        if (opcao.equalsIgnoreCase("s")) {

            String msg = listaFuncoesMensagem();

            try {
                ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
                conexaoSocket.setMensagem(msg);
                String retorno = conexaoSocket.call();
                System.out.println(retorno);

                String menuEmpresa = getFuncao(retorno);

                System.out.println("Informe o nome da Função");
                System.out.println(menuEmpresa);
                String nomeFuncao = entrada.next();

                System.out.println("Informe o CPF: ");
                String cpfPessoa = entrada.next();

                associaPessoa(cpfPessoa, nomeFuncao);

            } catch (IOException ex) {
                Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private String associaPessoa(String cpfPessoa, String nomeFuncao) {

        JSONObject funcaoJson = new JSONObject();
        funcaoJson.put("operacao", "ASSOCIA");
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

    public String listaFuncoesMensagem() {

        JSONObject funcaoJSON = new JSONObject();
        funcaoJSON.put("operacao", "LIST");
        funcaoJSON.put("entidade", "funcao");
        msg = funcaoJSON.toJSONString();

        return msg;
    }

    public String getFuncao(String mensagem) {

        String menu = "";

        try {
            List<Funcao> listaFuncoes = (List<Funcao>) conversorCJ.JsonParaFuncao(mensagem);
            menu = "Funções Existentes\n";

            for (int i = 0; i < listaFuncoes.size(); i++) {
                Funcao funcao = listaFuncoes.get(i);

                menu += "Nome: " + funcao.getNome() + "\n";
                menu += "Setor: " + funcao.getSetor() + "\n";
                menu += "Salário: " + funcao.getSalario() + "\n";
            }
        } catch (ParseException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return menu;
    }
}
