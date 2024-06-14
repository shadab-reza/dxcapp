package com.dxc.app.batch.processor;

import com.dxc.app.model.CustomerEntity;
import com.dxc.app.model.CustomerModel;
import org.springframework.batch.item.ItemProcessor;

public class CustomerItemProcessor implements ItemProcessor<CustomerEntity, CustomerModel>{

	@Override
	public CustomerModel process(CustomerEntity customerEntity) throws Exception {
		CustomerModel customerModel=new CustomerModel();
		customerModel.setSno(customerEntity.getSno());
		customerModel.setFname(customerEntity.getFname());
		customerModel.setLname(customerEntity.getLname());
		double total=0d;

		total+=getDoubleValue(customerEntity.getSpend());
		total+=getDoubleValue(customerEntity.getDumy1());
		total+=getDoubleValue(customerEntity.getDumy2());
		total+=getDoubleValue(customerEntity.getDumy3());
		total+=getDoubleValue(customerEntity.getDumy6());

		customerModel.setTotalSpend(String.format("%.3f",total));

		return customerModel;
	}

	double getDoubleValue(String doubleString){
if(doubleString.isEmpty()||doubleString.isBlank()){
	return 0d;

}else{
	return Double.parseDouble(doubleString);
}
	}
}
