package com.shinhan.common;

import java.lang.ModuleLayer.Controller;
import java.util.Scanner;

import com.shinhan.day19.DeptController;
import com.shinhan.emp.EmpController;

public class FrontController { // 여러 컨트롤러 통합

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CommonControllerInterface controller = null;
		boolean isStop = false;
		while (!isStop) {
			System.out.print("emp, dept >> ");
			String job = sc.next();
			switch (job) {
			case "emp" -> {
				controller = ControllerFactory.make("emp");
			}
			case "dept" -> {
				controller = ControllerFactory.make("dept");
			}
			case "job" -> {
				controller = ControllerFactory.make("job");
			}
			case "end" -> {
				isStop = true;
			}
			default -> {
				continue;
			}
			}
			controller.execute();
		}
		sc.close();
		System.out.println("========MAIN END========");
	
	}

}
