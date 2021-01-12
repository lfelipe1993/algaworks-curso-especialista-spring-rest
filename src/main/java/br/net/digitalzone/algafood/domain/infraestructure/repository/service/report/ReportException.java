package br.net.digitalzone.algafood.domain.infraestructure.repository.service.report;

public class ReportException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ReportException(String message) {
		super(message);
	}

	public ReportException(String message, Throwable cause) {
		super(message, cause);
	}
}
