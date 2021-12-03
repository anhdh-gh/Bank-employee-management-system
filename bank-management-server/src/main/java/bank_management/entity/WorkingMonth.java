package bank_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class WorkingMonth extends BaseEntity{
    @Column(name = "Month")
    @NotBlank(message = "month không được để trống!")
    private int month;

    @Column(name = "Year")
    @NotBlank(message = "year không được để trống!")
    private int year;
}
