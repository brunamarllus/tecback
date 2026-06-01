package br.uniesp.si.techback.exception;

public class RegraNegocioException
        extends RuntimeException {

    public RegraNegocioException(
            String mensagem
    ) {
        super(mensagem);
    }
}
