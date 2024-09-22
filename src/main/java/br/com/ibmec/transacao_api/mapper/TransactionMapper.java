package br.com.ibmec.transacao_api.mapper;

import br.com.ibmec.transacao_api.dto.TransactionResponse;
import br.com.ibmec.transacao_api.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponse toResponse(Transaction transaction);
}
