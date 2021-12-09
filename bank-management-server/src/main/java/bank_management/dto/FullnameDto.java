package bank_management.dto;

import bank_management.entity.FullName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FullnameDto {
    private String firstName;
    private String lastName;
    public FullnameDto(FullName fullName) {
        this.firstName = fullName.getFirstName();
        this.lastName = fullName.getLastName();
    }
}
