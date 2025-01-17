package pl.cleankod.exchange.core.service;

import pl.cleankod.exchange.core.domain.Account;
import pl.cleankod.exchange.core.domain.ApplicationError;
import pl.cleankod.exchange.core.usecase.FindAccountAndConvertCurrencyUseCase;
import pl.cleankod.exchange.core.usecase.FindAccountUseCase;
import pl.cleankod.util.Preconditions;
import pl.cleankod.util.domain.Result;

import java.util.Currency;
import java.util.Optional;

public class AccountService {

    private final FindAccountAndConvertCurrencyUseCase findAccountAndConvertCurrencyUseCase;
    private final FindAccountUseCase findAccountUseCase;

    public AccountService(FindAccountAndConvertCurrencyUseCase findAccountAndConvertCurrencyUseCase,
                          FindAccountUseCase findAccountUseCase) {
        this.findAccountAndConvertCurrencyUseCase = findAccountAndConvertCurrencyUseCase;
        this.findAccountUseCase = findAccountUseCase;
    }

    public Result<Account, ApplicationError> find(Account.Id accountId, Currency currency) {
        Preconditions.requireNonNull(accountId);

        return Optional.ofNullable(currency)
                .map(c -> findAccountAndConvertCurrencyUseCase.execute(accountId, c))
                .orElseGet(() -> findAccountUseCase.execute(accountId));
    }

    public Result<Account, ApplicationError> find(Account.Number accountNumber, Currency currency) {
        Preconditions.requireNonNull(accountNumber);

        return Optional.ofNullable(currency)
                .map(c -> findAccountAndConvertCurrencyUseCase.execute(accountNumber, c))
                .orElseGet(() -> findAccountUseCase.execute(accountNumber));
    }
}
