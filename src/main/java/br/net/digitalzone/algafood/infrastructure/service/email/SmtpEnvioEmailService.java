package br.net.digitalzone.algafood.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import br.net.digitalzone.algafood.core.email.EmailProperties;
import br.net.digitalzone.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	EmailProperties emailProperties;
	
	@Autowired
	Configuration freeMarkerConfig;

	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = criarMimeMessage(mensagem);
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar e-mail",e);
		}

	}

	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
		String corpo = processarTemplate(mensagem);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		// encapsula, ajuda a gente construir o email. (Atribuções)
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(emailProperties.getRemetente());
		helper.setTo(mensagem.getDestinarios().toArray(new String[0]));
		helper.setSubject(mensagem.getAssunto());
		helper.setText(corpo, true);

		return mimeMessage;
	}
	
	protected String processarTemplate(Mensagem msg) {
		try {
			Template template = freeMarkerConfig.getTemplate(msg.getCorpo());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, msg.getVariaveis());
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do email",e);
		}
	}

}
