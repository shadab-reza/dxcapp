package com.dxc.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerModel {
    private String sno;
    private String fname;
    private String lname;
    private String totalSpend;
    public CustomerModel(){
        super();
    }
}
