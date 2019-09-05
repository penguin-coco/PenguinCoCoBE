package com.penguin.penguincoco.model.admin;

import com.penguin.penguincoco.model.base.AbstractUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Admin extends AbstractUser {

    public Admin(String account, String password, String name) {
        super(account, password, name);
    }
}
