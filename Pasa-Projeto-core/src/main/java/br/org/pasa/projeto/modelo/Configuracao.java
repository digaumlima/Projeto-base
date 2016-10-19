package br.org.pasa.projeto.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CONFIGURACAO")
public class Configuracao extends BaseEntity {
	
	private static final long serialVersionUID = 7143220844739110113L;

	@Id
    @Column(name = "ID_CONFIGURACAO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NM_CAMINHO_UPLOAD")
    private String caminhoUpload;
    
    @Column(name = "NM_CAMINHO_URL_AUTH")
    private String caminhoUrlAutenticacao;

    @Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaminhoUpload() {
		return caminhoUpload;
	}

	public void setCaminhoUpload(String caminhoUpload) {
		this.caminhoUpload = caminhoUpload;
	}

	public String getCaminhoUrlAutenticacao() {
		return caminhoUrlAutenticacao;
	}

	public void setCaminhoUrlAutenticacao(String caminhoUrlAutenticacao) {
		this.caminhoUrlAutenticacao = caminhoUrlAutenticacao;
	}
}
