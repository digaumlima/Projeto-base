package br.org.pasa.projeto.acesso;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.org.pasa.projeto.exception.AcessoNegadoException;

public class AutenticacaoLDAP {

	public boolean autenticar(String caminhoConexao, String matricula, String senha) throws AcessoNegadoException {
		HttpURLConnection conn = null;
		try {
			conn = getConnection(caminhoConexao);
			OutputStream os = conn.getOutputStream();
			os.write(getJson(matricula, senha));
			os.flush();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return true;
			}
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
				return false;
			}

			throw new AcessoNegadoException(
				new StringBuilder("Failed : HTTP error code : ")
					.append(conn.getResponseCode()).toString());

		} catch (IOException e) {
			throw new AcessoNegadoException(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	private HttpURLConnection getConnection(String caminhoConexao) throws AcessoNegadoException, IOException{
		URL url = new URL(caminhoConexao);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setDoOutput(true); 
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json"); 
		return conn;
	}
	
	private byte[] getJson(String matricula, String senha){
		return new StringBuilder("{\"matricula\":\"")
			.append(matricula).append("\",\"senha\":\"")
			.append(senha).append("\"}").toString().getBytes();
	}
}
