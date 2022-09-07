package utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import model.Funcao;
import model.Pessoa;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Lucas de Liz Frutuoso e Matheus Henrique Maas
 */
public class Conversor {

    public String PessoaParaJson(Pessoa pessoa, String tipoOperacao) {

        JSONObject jsonPessoa = new JSONObject();
        jsonPessoa.put("operacao", tipoOperacao);
        jsonPessoa.put("classe", "pessoa");
        jsonPessoa.put("nome", pessoa.getNome());
        jsonPessoa.put("cpf", pessoa.getCpf());
        jsonPessoa.put("endereco", pessoa.getEndereco());

        return jsonPessoa.toJSONString();
    }

    public Pessoa JsonParaPessoa(String mensagem) throws ParseException, org.json.simple.parser.ParseException {

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(mensagem);
        
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf((String) json.get("cpf"));
        pessoa.setNome((String) json.get("nome"));
        pessoa.setEndereco((String) json.get("endereco"));

        return pessoa;
    }

    public String FuncaoParaJson(Funcao funcao, String tipoOperacao) {

        JSONObject jsonFuncao = new JSONObject();
        jsonFuncao.put("operacao", tipoOperacao);
        jsonFuncao.put("classe", "funcao");
        jsonFuncao.put("nome", funcao.getNome());
        jsonFuncao.put("setor", funcao.getSetor());
        jsonFuncao.put("salario", funcao.getSalario());

        return jsonFuncao.toJSONString();
    }

     public Funcao JsonParaFuncao(String mensagem) throws ParseException, org.json.simple.parser.ParseException {

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(mensagem);

        Funcao funcao = new Funcao();
        funcao.setNome((String) json.get("nome"));
        funcao.setSetor((String) json.get("setor"));
        funcao.setSalario((Double) json.get("salario"));

        return funcao;
   }
     
    public List<Funcao> JsonParaFuncaoList(String mensagem) throws ParseException, org.json.simple.parser.ParseException {
        
        List<Funcao> listaFuncao = new ArrayList<>();
        
        JSONObject jsonObjeto;
        JSONParser parser = new JSONParser();
        jsonObjeto = (JSONObject) parser.parse(mensagem);

        for (int i = 0; i < jsonObjeto.size(); i++) {
            String funcao = jsonObjeto.get(String.valueOf(i)).toString();
            Funcao f1 = new Funcao();
            f1 = JsonParaFuncao(funcao);

            listaFuncao.add(f1);
        }

        return listaFuncao;
    }
    
     public List<Pessoa> JsonParaPessoaList(String mensagem) throws ParseException, org.json.simple.parser.ParseException {
        
        List<Pessoa> listaPessoas = new ArrayList<>();
        
        JSONObject jsonObjeto;
        JSONParser parser = new JSONParser();
        jsonObjeto = (JSONObject) parser.parse(mensagem);

        for (int i = 0; i < jsonObjeto.size(); i++) {
            String pessoa = jsonObjeto.get(String.valueOf(i)).toString();
            Pessoa p1 = new Pessoa();
            p1 = JsonParaPessoa(pessoa);

            listaPessoas.add(p1);
        }

        return listaPessoas;
    }

    public String cpfPessoa(String mensagem) throws org.json.simple.parser.ParseException {

        JSONObject jsonObjeto;
        JSONParser parser = new JSONParser();
        jsonObjeto = (JSONObject) parser.parse(mensagem);
        String cpf = (String) jsonObjeto.get("cpf");

        return cpf;
    }

    public String retornaOperacao(String mensagem) throws org.json.simple.parser.ParseException {

        JSONObject jsonObjeto;
        JSONParser parser = new JSONParser();
        jsonObjeto = (JSONObject) parser.parse(mensagem);

        String operacao = (String) jsonObjeto.get("operacao");
        return operacao;
    }

    public String retornaEntidade(String mensagem) throws org.json.simple.parser.ParseException {

        JSONObject jsonObjeto;
        JSONParser parser = new JSONParser();
        jsonObjeto = (JSONObject) parser.parse(mensagem);
        String entidade = (String) jsonObjeto.get("entidade");

        return entidade;
    }

}
