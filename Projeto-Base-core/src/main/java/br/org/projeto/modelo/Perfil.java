package br.org.projeto.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PERFIL")
public class Perfil extends BaseEntity {

	private static final long serialVersionUID = 6758144021126036045L;

	@Id
    @Column(name = "ID_PERFIL")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NM_PERFIL")
    private String nome;
    
    public Perfil(Long id) {
		this.id = id;
	}
    
    public Perfil() {
	}

	@Override    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
    
}
