package com.nyu.microservices.Model;

import java.util.List;

import com.nyu.microservices.Entity.VaccinationCenter;
import com.nyu.microservices.user.Entity.UserBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequiredResponse {
	
	private VaccinationCenter center;
	private List<Citizen> citizens;
	private List<UserBean> users;
	public List<UserBean> getUsers() {
		return users;
	}
	public void setUsers(List<UserBean> users) {
		this.users = users;
	}

}

