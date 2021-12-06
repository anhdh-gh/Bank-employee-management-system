package bank_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @NotBlank(message = "City không được để trống")
    @Size(max = 50, message = "City tối đa 50 ký tự")
    @Column(name = "City")
    private String city;

    @NotBlank(message = "District không được để trống")
    @Size(max = 50, message = "District tối đa 50 ký tự")
    @Column(name = "District")
    private String district;

    @NotBlank(message = "Country không được để trống")
    @Size(max = 50, message = "Country tối đa 50 ký tự")
    @Column(name = "Country")
    private String country;

    @NotBlank(message = "HouseNumber không được để trống")
    @Size(max = 50, message = "HouseNumber tối đa 50 ký tự")
    @Column(name = "HouseNumber")
    private String houseNumber;

    @NotBlank(message = "ZipCode không được để trống")
    @Size(max = 20, message = "ZipCode tối đa 20 ký tự")
    @Column(name = "ZipCode")
    private String zipCode;

    public Address(String ID, Date createDate, Date editDate, String city, String district, String country, String houseNumber, String zipCode) {
        super(ID, createDate, editDate);
        this.city = city;
        this.district = district;
        this.country = country;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
    }
}