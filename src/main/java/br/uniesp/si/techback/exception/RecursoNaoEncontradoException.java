package br.uniesp.si.techback.exception;

public class RecursoNaoEncontradoException
        extends RuntimeException {

    public RecursoNaoEncontradoException(
            String mensagem
    ) {
        super(mensagem);
    }
}
