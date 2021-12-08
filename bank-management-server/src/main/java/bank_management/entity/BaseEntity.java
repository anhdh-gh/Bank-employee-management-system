package bank_management.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass // Class cha không phải là entity. https://techmaster.vn/posts/36499/hibernate-inheritance-mapping
@Data  // All together now: A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, @Setter on all non-final fields, and @RequiredArgsConstructor!
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
	
	@Id
	protected String ID;
	
	@Column(name = "CreateDate")
	protected Date createDate;
	
	@Column(name = "EditDate")
	protected Date editDate;

	@PrePersist // Thực thi trước khi entity được persist (được lưu vào database) bởi method persist()
	protected void init() {
		this.ID = UUID.randomUUID().toString();
		this.createDate = new Date();
	}
	
	@PreUpdate // Thực thi trước khi entity được update
	protected void editDate() {
		this.editDate = new Date();
	}	
}