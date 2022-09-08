package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import model.Funcao;
import model.Pessoa;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.ConexaoSocket;
import utils.Conversor;

/**
 *
 * @author Lucas de Liz Frutuoso e Matheus Henrique Maas
 */
public class ControllerFuncao {

    private String msg = "";
    private Funcao funcao;
    private Conversor conversorCJ;
    Scanner entrada;

    public void insereFuncao() {
        entrada = new Scanner(System.in);
        String operacao = "INSERT";
        funcao = new Funcao();
        conversorCJ = new Conversor();

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

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String retorno = conexaoSocket.call();

        } catch (IOException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listaFuncoes() throws java.text.ParseException, ParseException {

        JSONObject funcaoJson = new JSONObject();
        funcaoJson.put("operacao", "LIST");
        funcaoJson.put("classe", "funcao");
        msg = funcaoJson.toJSONString();

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String resposta = conexaoSocket.call();

            if (!"0".equals(resposta)) {
                String menuFuncoes = getFuncao(resposta);
                System.out.println(menuFuncoes);
            } else {
                System.out.println(resposta);
            }

        } catch (IOException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscaFuncao() throws java.text.ParseException, ParseException {
        entrada = new Scanner(System.in);
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

            if (!"Função não encontrada".equals(resposta) && !"Sem funções cadastradas".equals(resposta)) {
                Conversor c1 = new Conversor();
                Funcao f2 = c1.JsonParaFuncao(resposta);

                System.out.println(f2.getNome() + ";" + f2.getSetor() + ";" + f2.getSalario());
            } else {
                System.out.println(resposta);
            }

        } catch (IOException ex) {
            Logger.getLogger(ControllerPessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletaFuncao() {
        entrada = new Scanner(System.in);
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
        entrada = new Scanner(System.in);
        String operacao = "UPDATE";
        funcao = new Funcao();
        conversorCJ = new Conversor();

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

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            String retorno = conexaoSocket.call();

            System.out.println(retorno);

        } catch (IOException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void associaFuncaoPessoa() throws java.text.ParseException, ParseException {
        entrada = new Scanner(System.in);
        String nomeFuncao = null;
        String cpfPessoa = null;

        System.out.println("Deseja vincular uma pessoa a uma Função? [s/n]");
        String opcao = entrada.nextLine();

        if (opcao.equalsIgnoreCase("s")) {

            String msg = listaFuncoesServidor();

            try {
                ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
                conexaoSocket.setMensagem(msg);
                String retorno = conexaoSocket.call();

                String menuFunções = getFuncao(retorno);
                System.out.println(menuFunções);

                System.out.println("Informe o nome da Função:");
                nomeFuncao = entrada.next();

            } catch (IOException ex) {
                Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
            }

            String msg2 = listaPessoasServidor();

            try {
                ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
                conexaoSocket.setMensagem(msg2);
                String retorno = conexaoSocket.call();

                ControllerPessoa controllerPessoa = new ControllerPessoa();

                String menuPessoas = controllerPessoa.getPessoa(retorno);
                System.out.println(menuPessoas);

                System.out.println("Informe o CPF da pessoa que deseja vincular para a função: ");
                cpfPessoa = entrada.next();

            } catch (IOException ex) {
                Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
            }

            String resposta = associaPessoa(cpfPessoa, nomeFuncao);

            System.out.println(resposta);
        }
    }

    private String associaPessoa(String cpfPessoa, String nomeFuncao) {

        JSONObject funcaoJson = new JSONObject();
        funcaoJson.put("operacao", "ASSOCIA");
        funcaoJson.put("cpf", cpfPessoa);
        funcaoJson.put("nome", nomeFuncao);
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

    public String listaFuncoesServidor() {

        JSONObject funcaoJSON = new JSONObject();
        funcaoJSON.put("operacao", "LIST");
        funcaoJSON.put("classe", "funcao");
        msg = funcaoJSON.toJSONString();

        return msg;
    }

    public String listaPessoasServidor() {

        JSONObject funcaoJSON = new JSONObject();
        funcaoJSON.put("operacao", "LIST");
        funcaoJSON.put("classe", "pessoa");
        msg = funcaoJSON.toJSONString();

        return msg;
    }

    public String getFuncao(String mensagem) throws java.text.ParseException, ParseException {

        String menuFuncoes;

        Conversor conversorJSONListFuncao = new Conversor();
        List<Funcao> listaFuncoes = conversorJSONListFuncao.JsonParaFuncaoList(mensagem);

        menuFuncoes = "----Funções Existentes----\n";
        menuFuncoes += "Quantidades de Funções: " + listaFuncoes.size() + "\n";
        menuFuncoes += "------------------------------------\n";

        for (int i = 0; i < listaFuncoes.size(); i++) {
            Funcao funcao = listaFuncoes.get(i);

            menuFuncoes += "Nome: " + funcao.getNome() + "\n";
            menuFuncoes += "Setor: " + funcao.getSetor() + "\n";
            menuFuncoes += "Salário: " + funcao.getSalario() + "\n";
            menuFuncoes += "------------------------------------\n";
        }

        return menuFuncoes;
    }

    public String listaGeralFuncaoPessoa() throws java.text.ParseException, ParseException {

        String menuListaGeral;
        String retorno = null;

        JSONObject funcaoJSON = new JSONObject();
        funcaoJSON.put("operacao", "LIST_GERAL");

        msg = funcaoJSON.toJSONString();

        try {
            ConexaoSocket conexaoSocket = ConexaoSocket.getInstance();
            conexaoSocket.setMensagem(msg);
            retorno = conexaoSocket.call();
            System.out.println(retorno);

        } catch (IOException ex) {
            Logger.getLogger(ControllerFuncao.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Conversor conversorJSONListGeral = new Conversor();
        // List<Funcao> listaFuncoesPessoas = conversorJSONListGeral.JsonParaListGeral(retorno);
        // menuListaGeral = "----Funções Existentes----\n";
        // for (int i = 0; i < listaFuncoesPessoas.size(); i++) {
        //      Funcao funcao = listaFuncoesPessoas.get(i);
//
        //      menuListaGeral += "Nome: " + funcao.getNome() + "\n";
        //      menuListaGeral += "Setor: " + funcao.getSetor() + "\n";
        //      menuListaGeral += "Salário: " + funcao.getSalario() + "\n";
        //      menuListaGeral += "------------------------------------\n";
        //    }
        return retorno;
    }
}
