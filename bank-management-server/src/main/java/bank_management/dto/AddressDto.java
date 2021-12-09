package bank_management.dto;

import bank_management.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDto {
    private String country;
    private String city;
    private String district;
    private String houseNumber;
    private String zipCode;

    public AddressDto(Address address) {
        this.country = address.getCountry();
        this.city = address.getCity();
        this.district = address.getDistrict();
        this.houseNumber = address.getHouseNumber();
        this.zipCode = address.getZipCode();
    }
}
