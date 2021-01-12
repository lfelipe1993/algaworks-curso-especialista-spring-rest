package br.net.digitalzone.algafood.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ApiRetirementHandler extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getRequestURI().startsWith("/v1/")) {
			
			//410 o recurso nao existe mais no servidor e essa condição é permanente.
			response.setStatus(HttpStatus.GONE.value());
		}
		
		//alem de alterar o status pra gone, nao vamos continuar a execução do metodo.
		return false;
	}
}
