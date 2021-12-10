package bank_management.dto;

import bank_management.entity.CreditAccount;
import bank_management.entity.Customer;
import bank_management.entity.PaymentAccount;
import bank_management.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Enumerated;
import java.util.Date;

@Data
@AllArgsConstructor
public class CustomerDto {
   private String ID;
   private AccountDto account;
   private String identityNumber;
   private Date dateOfBirth;
   private String email;
   private String phoneNumber;
   @Enumerated
   private Gender gender;
   private String customerCode;
   private FullnameDto fullName;
   private AddressDto address;

   public CustomerDto(Customer customer){
       customer.getAccount().setPassword("");
       this.ID = customer.getID();
       this.account = new AccountDto(customer.getAccount());
       this.identityNumber = customer.getIdentityNumber();
       this.dateOfBirth = customer.getDateOfBirth();
       this.email = customer.getEmail();
       this.phoneNumber = customer.getPhoneNumber();
       this.gender = customer.getGender();
       this.customerCode = customer.getCustomerCode();
       this.fullName = new FullnameDto(customer.getFullName());
       this.address = new AddressDto(customer.getAddress());
   }
}
