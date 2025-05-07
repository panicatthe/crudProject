package com.shinhan.emp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;

public class EmpController implements CommonControllerInterface{

	static Scanner sc = new Scanner(System.in);
	static EmpService empService = new EmpService();

	public void execute() {

		boolean isStop = false;
		while (!isStop) {
			menuDisplay();
			switch (sc.nextInt()) {
			case 1 -> {
				f_selectAll();
			}
			case 2 -> {
				f_selectById();
			}
			case 3 -> {
				f_selectByDept();
			}
			case 4 -> {
				f_selectByJob();
			}
			case 5 -> {
				f_selectByJobAndDept();
			}
			case 6 -> {
				f_selectByCondition();
			}
			case 7 -> {
				f_selectByConditions();
			}
			case 8 -> {
				f_deleteById();
			}
			case 9 -> {
				f_insertEMP();
			}
			case 10 -> {
				f_updateEMP();
			}case 11 -> {
				f_excuteSP();
			}
			case 12 -> {
				isStop = true;
			}

			}
		}
		System.out.println("============bye==============");
	}

	private static void f_excuteSP() {
		System.out.print("조회할 직원ID >> ");
		EmpDTO emp = empService.execute_sp(sc.nextInt());
		if(emp == null) {
			EmpView.display("직원 조회 실패");
		}
		String meg = emp.getEmail() + "---" + emp.getSalary();
		EmpView.display(meg);
	}

	private static EmpDTO makeEmp(int employee_id) {
		EmpDTO emp = new EmpDTO();
		emp.setEmployee_id(employee_id);

		System.out.print("이름(FIRST_NAME) 입력 (없으면 0) >> ");
		String input = sc.next();
		emp.setFirst_name(input.equals("0") ? null : input);

		System.out.print("성(LAST_NAME) 입력 (없으면 0) >> ");
		input = sc.next();
		emp.setLast_name(input.equals("0") ? null : input);

		System.out.print("이메일(EMAIL) 입력 (없으면 0) >> ");
		input = sc.next();
		emp.setEmail(input.equals("0") ? null : input);

		System.out.print("전화번호(PHONE_NUMBER) 입력 (없으면 0) >> ");
		input = sc.next();
		emp.setPhone_number(input.equals("0") ? null : input);

		System.out.print("입사일(HIRE_DATE) 입력 (yyyy-MM-dd, 없으면 0) >> ");
		input = sc.next();
		if (input.equals("0")) {
			emp.setHire_date(null);
		} else {
			java.sql.Date sqlDate = DateUtil.parseToSqlDate(input);
			if (sqlDate == null) {
				System.out.println("❌ 날짜 형식 오류 (예: 2000-01-01). 입력 취소됨.");
				return null;
			}
			emp.setHire_date(sqlDate);
		}

		System.out.print("직책 ID(JOB_ID, 예: IT_PROG, 없으면 0) >> ");
		input = sc.next();
		emp.setJob_id(input.equals("0") ? null : input);

		System.out.print("급여(SALARY) 입력 (없으면 0) >> ");
		emp.setSalary(sc.nextDouble());

		System.out.print("커미션 비율(COMMISSION_PCT, 없으면 0) >> ");
		emp.setCommission_pct(sc.nextDouble());

		System.out.print("매니저 ID (없으면 0) >> ");
		int mid = sc.nextInt();
		emp.setManager_id(mid == 0 ? null : mid);

		System.out.print("부서 ID (없으면 0) >> ");
		int deptId = sc.nextInt();
		emp.setDepartment_id(deptId == 0 ? null : deptId);

		return emp;
	}

	private static void f_updateEMP() {
		System.out.print("수정할 직원 ID 입력 >> ");
		int id = sc.nextInt();
		EmpDTO exist = empService.selectById(id);
		if (exist == null) {
			EmpView.display("존재하지 않는 직원");
			return;
		}
		System.out.println("=====수정할 직원======");
		EmpView.display(exist);

		EmpDTO emp = makeEmp(id);
		if (emp == null)
			return;

		int result = empService.empUpdate(emp);
		System.out.println("🔄 업데이트 결과 건수 >> " + result);
	}

	private static void f_insertEMP() {
		System.out.print("직원 ID 입력 >> ");
		int id = sc.nextInt();
		EmpDTO emp = makeEmp(id);
		if (emp == null)
			return;

		int result = empService.empInsert(emp);
		System.out.println("추가된 직원 수 >> " + result);
	}

	private static void f_deleteById() {
		System.out.print("삭제할 직원id 입력 >> ");
		System.out.println("삭제한 건수 >> " + empService.empDeleteById(sc.nextInt()));
	}

	private static void f_selectByConditions() {
		System.out.print("조회할 부서ID들 입력 (예: 10,20,30) >> ");
		String input = sc.next();
		String[] tokens = input.split(",");
		List<Integer> deptList = new ArrayList<>();
		for (String token : tokens) {
			try {
				deptList.add(Integer.parseInt(token.trim()));
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력하세요.");
				return;
			}
		}

		System.out.print("조회할 직책ID >> ");
		String jobid = sc.next();

		System.out.print("조회할 salary(이상) >> ");
		int salary = sc.nextInt();

		System.out.print("조회할 입사일(yyyy-MM-dd, 이상) >> ");
		String hdate = sc.next();

		List<EmpDTO> emplist = empService.selectByCondition(deptList, jobid, salary, hdate);
		EmpView.display(emplist);
	}

	private static void f_selectByCondition() {
		System.out.print("조회할 부서 >> ");
		int deptid = sc.nextInt();
		System.out.print("조회할 직책ID >> ");
		String jobid = sc.next();
		System.out.print("조회할 salary(이상) >> ");
		int salary = sc.nextInt();
		System.out.print("조회할 입사일(이상) >> ");
		String hdate = sc.next();
		List<EmpDTO> emplist = empService.selectByCondition(deptid, jobid, salary, hdate);
		EmpView.display(emplist);

	}

	private static void f_selectByJobAndDept() {
		System.out.print("조회할 직책ID,부서ID >> ");
		String[] data = sc.next().split(",");
		List<EmpDTO> emplist = empService.selectByJobAndDept(data[0], Integer.parseInt(data[1]));
		EmpView.display(emplist);
	}

	private static void f_selectByJob() {
		System.out.print("조회할 직책 >> ");
		List<EmpDTO> emplist = empService.selectByJob(sc.next());
		EmpView.display(emplist);
	}

	private static void f_selectByDept() {
		System.out.print("조회할 부서 >> ");
		List<EmpDTO> emplist = empService.selectByDept(sc.nextInt());
		EmpView.display(emplist);
	}

	private static void f_selectById() {
		System.out.print("조회할 ID >> ");
		EmpDTO emp = empService.selectById(sc.nextInt());
		EmpView.display(emp);
	}

	private static void f_selectAll() {
		List<EmpDTO> emplist = empService.selectAll();
		EmpView.display(emplist);
	}

	private static void menuDisplay() {
		System.out.println("---------------------------------");
		System.out.println("1.모두조회 \t2.조회(직원번호)\t3.조회(부서)\t4.조회(직책)\t5.조회(직책,부서)");
		System.out.println("6.상세조회 \t7.다중부서 \t8.삭제(직원번호)\t9.INSERT\t10.UPDATE");
		System.out.println("11.SP호출 \t12.종료");
		System.out.println("---------------------------------");
		System.out.print("작업 선택> ");
	}

}
