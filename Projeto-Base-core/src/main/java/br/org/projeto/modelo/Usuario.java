package br.org.projeto.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * The persistent class for the Usuario database table.
 * 
 */
@Entity
@Table(name="USUARIO")
public class Usuario extends BaseEntity {
	
	private static final long serialVersionUID = 7585087595620565070L;

	public static final Integer USR_SISTEMA = 1;
	
	@Id
    @Column(name = "ID_USUARIO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NM_MATRICULA_USUARIO")
    private String matricula;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PERFIL_USUARIO", joinColumns = { @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO") }, inverseJoinColumns = { @JoinColumn(name = "ID_PERFIL") })
    private Set<Perfil> perfil;

    @Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Set<Perfil> getPerfil() {
		return perfil;
	}

	public void setPerfil(Set<Perfil> perfil) {
		this.perfil = perfil;
	}
	
	public List<Perfil> getPerfilList() {
		List<Perfil> perf = new ArrayList<>(this.perfil);
	    return perf;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Usuario [id=");
		sb.append(id);
		sb.append(", matricula=");
		sb.append(matricula);
		sb.append(", perfil=");
		if(perfil != null && !perfil.isEmpty()){
			for (Perfil perfil2 : perfil) {
				sb.append(perfil2.getId());
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
		
}
