package br.com.legalmanager.devProject.exception;

public class CustomDataIntegrityViolationException extends RuntimeException {
    public CustomDataIntegrityViolationException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
