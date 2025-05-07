package com.shinhan.day19;

import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;

public class DeptController implements CommonControllerInterface{

	static Scanner sc = new Scanner(System.in);
	static DeptService service = new DeptService();

	public void execute() {
		while (true) {
			menuDisplay();
			int choice = Integer.parseInt(sc.nextLine());

			switch (choice) {
			case 1:
				DeptVIEW.display(service.getAllDepts());
				break;
			case 2:
				System.out.print("부서 ID 입력: ");
				int id = Integer.parseInt(sc.nextLine());
				DeptVIEW.display(service.getDeptById(id));
				break;
			case 3:
				DeptDTO newDept = DeptDTO.builder().department_id(inputInt("부서 ID")).department_name(inputStr("부서명"))
						.manager_id(inputInt("매니저 ID")).location_id(inputInt("지역 ID")).build();
				boolean inserted = service.addDept(newDept);
				DeptVIEW.display(inserted ? "부서 추가 성공" : "부서 추가 실패");
				break;
			case 4:
				DeptDTO updatedDept = DeptDTO.builder().department_id(inputInt("수정할 부서 ID"))
						.department_name(inputStr("새 부서명")).manager_id(inputInt("새 매니저 ID"))
						.location_id(inputInt("새 지역 ID")).build();
				boolean updated = service.updateDept(updatedDept);
				DeptVIEW.display(updated ? "수정 성공" : "수정 실패");
				break;
			case 5:
				int deleteId = inputInt("삭제할 부서 ID");
				boolean deleted = service.deleteDept(deleteId);
				DeptVIEW.display(deleted ? "삭제 성공" : "삭제 실패");
				break;
			case 0:
				System.out.println("종료합니다.");
				return;
			default:
				System.out.println("잘못된 선택입니다.");
			}
		}
	}

	private static int inputInt(String prompt) {
		System.out.print(prompt + ": ");
		return Integer.parseInt(sc.nextLine());
	}

	private static String inputStr(String prompt) {
		System.out.print(prompt + ": ");
		return sc.nextLine();
	}

	private static void menuDisplay() {
	    System.out.println("\n🌟━━━━━━━━━━━【 메뉴 】━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	    System.out.println("  [1] 📋 전체 조회");
	    System.out.println("  [2] 🔍 ID로 조회");
	    System.out.println("  [3] ➕ 부서 추가");
	    System.out.println("  [4] 🛠️ 부서 수정");
	    System.out.println("  [5] 🗑️ 부서 삭제");
	    System.out.println("  [0] 🚪 종료");
	    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	    System.out.print("👉 메뉴 번호를 선택하세요 > ");
	}


}
