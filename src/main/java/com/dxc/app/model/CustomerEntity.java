package com.dxc.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    private String sno;
    private String fname;
    private String lname;
    private String spend;
    private String dumy1;
    private String dumy2;
    private String dumy3;
    private String dumy4;
    private String dumy5;
    private String dumy6;

    public CustomerEntity(){
        super();
    }
}
