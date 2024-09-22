package br.com.ibmec.transacao_api.mapper;

import br.com.ibmec.transacao_api.dto.AccountResponse;
import br.com.ibmec.transacao_api.dto.CreateAccountRequest;
import br.com.ibmec.transacao_api.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toResponse(Account account);
    Account toEntity(CreateAccountRequest request);
}
