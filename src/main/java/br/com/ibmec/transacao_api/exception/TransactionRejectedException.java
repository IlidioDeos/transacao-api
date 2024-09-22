package br.com.ibmec.transacao_api.exception;

public class TransactionRejectedException extends RuntimeException {
    private final String reason;

    public TransactionRejectedException(String reason) {
        super(reason);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}