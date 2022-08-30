package utils;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import model.Funcao;
import model.Pessoa;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Matheus Henrique Maas
 */
public class ConversorClasseJSON {

    public String PessoaParaJson(Pessoa pessoa, String tipoOperacao) {

        JSONObject jsonPessoa = new JSONObject();
        LinkedHashMap test = new LinkedHashMap();

        test.put("operacao", tipoOperacao);
        test.put("nome", pessoa.getNome());
        test.put("cpf", pessoa.getCpf());
        test.put("endereco", pessoa.getEndereco());

        jsonPessoa = new JSONObject(test);

        return jsonPessoa.toJSONString();
    }

    public Pessoa JsonParaPessoa(String mensagem) throws ParseException {

        JSONObject json = new JSONObject();

        Pessoa pessoa = new Pessoa();
        pessoa.setCpf((String) json.get("cpf"));
        pessoa.setNome((String) json.get("nome"));
        pessoa.setEndereco((String) json.get("endereco"));

        return pessoa;
    }

    public String FuncaoParaJson(Funcao funcao) {

        JSONObject jsonFuncao = new JSONObject();
        jsonFuncao.put("nome", funcao.getNome());
        jsonFuncao.put("setor", funcao.getSetor());
        jsonFuncao.put("salario", funcao.getSalario());

        return jsonFuncao.toJSONString();
    }

    public Funcao JsonParaFuncao(String mensagem) throws org.json.simple.parser.ParseException {

        JSONObject json = new JSONObject();

        Funcao funcao = new Funcao();
        funcao.setNome((String) json.get("nome"));
        funcao.setSetor((String) json.get("setor"));
        funcao.setSalario((Double) json.get("salario"));

        return funcao;
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
