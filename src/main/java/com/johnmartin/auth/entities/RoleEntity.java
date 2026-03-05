package com.johnmartin.auth.entities;

import java.math.BigInteger;

import com.johnmartin.auth.constants.entities.RoleEntityConstants;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = RoleEntityConstants.TABLE_NAME)
public class RoleEntity {

    @Id
    private BigInteger id;
    private String name;
    private String description;

    @Override
    public String toString() {
        return "RoleEntity{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}
