package bank_management.dto;

import bank_management.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
    private String ID;
    private String username;
    private String password;

    public AccountDto(Account account) {
        this.ID = account.getID();
        this.username = account.getUsername();
        this.password = account.getPassword();
    }

}
