package br.com.legalmanager.devProject.exception;

public class CPFAlreadyExistsException extends RuntimeException {
    public CPFAlreadyExistsException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}