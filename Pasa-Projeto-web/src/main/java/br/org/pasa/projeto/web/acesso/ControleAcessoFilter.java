/**
 * 
 */
package br.org.pasa.projeto.web.acesso;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.org.pasa.projeto.acesso.UsuarioLogado;

/**
 * Titulo: ControleAcessoFilter.java
 * 
 * @date 20/09/2016
 * @autor rodrigo.cordovil
 */
@WebFilter(urlPatterns={"/view/*"})
public class ControleAcessoFilter implements Filter {

	@Inject
	private UsuarioLogado usuarioLogado;
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
		String contextPath = ((HttpServletRequest) request).getContextPath();

        if (usuarioLogado == null || !usuarioLogado.isLogado()) {            
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        } else {                        
            //String pagina;

            //String[] url = ((HttpServletRequest) request).getRequestURI().split("/");
            //pagina = url[url.length-1];

            //verifica qual url o usuario digitou/acessou
            //if (pagina.equals("home") || usuarioBean.temAcesso(pagina)) {
                // Caso ele tenha acesso, apenas deixamos que o fluxo continue
                chain.doFilter(request, response);                
            //} else {
                //redireciona se nao tiver acesso
                //((HttpServletResponse) response).sendRedirect(contextPath + "/home/");
            //}
        }
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
}
