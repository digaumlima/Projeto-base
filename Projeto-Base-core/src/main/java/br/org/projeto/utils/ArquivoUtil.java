package br.org.projeto.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ArquivoUtil {
	
    /** caminho. */
    private String caminho;

    /** arquivo. */
    private byte[] arquivo;

    /** nome. */
    private String nome;

    /** separador sistema operacional. */
    public final String separadorSistemaOperacional = System.getProperty("file.separator");

    public ArquivoUtil() {
    	super();
    }

    /**
     * Obtem nome.
     * 
     * @return nome
     */
    public String getNome() {
        return this.nome;
    }
	
    /**
     * Obtem um inputstream
     * @return
     */
    public ByteArrayInputStream getInpuStreamArquivo(){
    	return new ByteArrayInputStream(arquivo);
    }

    /**
     * Obtem bytes from input stream.
     * 
     * @param is
     *            is
     * @return bytes from input stream
     * @throws IOException 
     */
    public byte[] getBytesFromInputStream(InputStream is) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[0xFFFF];
            int len = is.read(buffer);
            while (len != -1) {
                os.write(buffer, 0, len);
                len = is.read(buffer);
            }

            os.flush();
            return os.toByteArray();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * File upload.
     * 
     * @param inputStream
     *            input stream
     * @param type
     *            type
     * @param diretorio
     *            diretorio
     * @param nomeArquivo
     *            nome arquivo
     */
    public void fileUpload(String caminhoUpload, InputStream inputStream, String diretorio, String nomeArquivo) throws IOException {
        try {
            if (inputStream != null) {
                this.nome = nomeArquivo;
                this.caminho = caminhoUpload + diretorio + this.getNome();
                this.arquivo = this.getBytesFromInputStream(inputStream);
                File file = new File(caminhoUpload + diretorio);
                file.mkdirs();
            } else {
            	String strError = "Não foi possível fazer o upload do arquivo " + nomeArquivo + " para o diretório " + diretorio + ", pois o stream está nulo.";
                throw new IOException(strError);
            }
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    /**
     * Delete dir.
     * 
     * @param dir
     *            dir
     * @return true, se bem sucedido
     */
    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = this.deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * Gravar.
     */
    public void gravar() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(this.caminho)){
            if (this.caminho != null && this.arquivo != null) {
                fos.write(this.arquivo);
                fos.close();
            } else {
            	String strError = "Não foi possível gravar o arquivo, pois o arquivo ou caminho está nulo. Caminho = " + this.caminho + " e arquivo = " + this.arquivo;
            	fos.close();
                throw new IOException(strError);
            }
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }
}
