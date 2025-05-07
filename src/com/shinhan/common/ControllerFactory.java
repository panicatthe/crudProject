package com.shinhan.common;

import com.shinhan.day19.DeptController;
import com.shinhan.emp.EmpController;
import com.shinhan.job.JobController;

public class ControllerFactory {

	public static CommonControllerInterface make(String business) {
		CommonControllerInterface controller = null;
		switch(business) {
		case "emp" ->{
			controller = new EmpController();
		}
		case "dept" ->{
			controller = new DeptController();
		}
		case "job" ->{
			controller = new JobController();
		}
		}
		return controller;
	}

}
