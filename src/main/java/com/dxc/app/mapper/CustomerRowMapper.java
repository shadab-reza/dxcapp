package com.dxc.app.mapper;

import com.dxc.app.model.CustomerEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<CustomerEntity> {


	@Override
	public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomerEntity customerEntity =new CustomerEntity();
		customerEntity.setSno(rs.getString("sno"));
		customerEntity.setFname(rs.getString("fname"));
		customerEntity.setLname(rs.getString("lname"));
		customerEntity.setSpend(rs.getString("spend"));
		customerEntity.setDumy1(rs.getString("dumy1"));
		customerEntity.setDumy2(rs.getString("dumy2"));
		customerEntity.setDumy3(rs.getString("dumy3"));
		customerEntity.setDumy4(rs.getString("dumy4"));
		customerEntity.setDumy5(rs.getString("dumy5"));
		customerEntity.setDumy6(rs.getString("dumy6"));


		return customerEntity;
	}
}