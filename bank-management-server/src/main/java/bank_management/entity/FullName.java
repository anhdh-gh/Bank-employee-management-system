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
@Table(name = "fullName")
public class FullName extends BaseEntity {

    @NotBlank(message = "FirstName không được để trống")
    @Size(max = 30, message = "FirstName tối đa 30 ký tự")
    @Column(name = "FirstName")
    private String firstName;

    @NotBlank(message = "LastName không được để trống")
    @Size(max = 30, message = "LastName tối đa 30 ký tự")
    @Column(name = "LastName")
    private String lastName;

    public FullName(String ID, Date createDate, Date editDate, String firstName, String lastName) {
        super(ID, createDate, editDate);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}