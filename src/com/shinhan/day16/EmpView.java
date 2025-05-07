package com.shinhan.day16;

import java.util.List;

//data display
public class EmpView { 
	
	public static void display(List<EmpDTO> emplist) {
		if(emplist.size() == 0) {
			display("해당하는 직원이 존재하지 않습니다.");
			return;
		}
		System.out.println("===직원 여러건 조회===");
		emplist.stream().forEach(emp->System.out.println(emp.toString()));
	}
	
	public static void display(EmpDTO emp) {
		if(emp == null) {
			display("해당하는 직원이 존재하지 않습니다.");
			return;
		}
		System.out.println("직원 정보 : "+emp.toString());
	}
	
	public static void display(String message) {
		System.out.println("알림: "+message);
	}
}
