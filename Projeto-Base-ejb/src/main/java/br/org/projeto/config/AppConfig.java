package br.org.projeto.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import br.org.projeto.dao.JPADAO;
import br.org.projeto.dao.impl.ConfiguracaoDAOImpl;
import br.org.projeto.modelo.Configuracao;

@Named
@ApplicationScoped
public class AppConfig {

	private static final Logger logger = Logger.getLogger(AppConfig.class);
	
	@Inject @JPADAO
	private ConfiguracaoDAOImpl configDAOImpl;
	
	private Configuracao configuracao;

	@Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
		
		try {
			this.configuracao = this.configDAOImpl.buscarConfiguracaoSistema();
		} catch (NoResultException ne) {
			logger.error("Não foi possível encontrar o conteudo da tabela de configuração do sistema, o script de insert esta localizado no EAR da aplicação.");
	    	//TODO: Esse trecho soh existe para nao causar erro no projeto padrao, deve ser retirado quando da criacao do projeto
			Configuracao conf = new Configuracao();
	    	conf.setCaminhoUpload("/Pasa/documentos");
	    	conf.setCaminhoUrlAutenticacao("http://login.planopasa.com.br/restapi/acesso/validar");
	    	this.configDAOImpl.inserir(conf);
	    	this.configuracao = conf;
		} catch (Exception e) {
			logger.error("Não foi possível encontrar o conteudo da tabela de configuração do sistema, o script de insert esta localizado no EAR da aplicação.", e);
		}
    	
    }
 
    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
    	this.configuracao = null;
    }

    public Configuracao getConfiguracao(){
        return configuracao;
    }
	
}
