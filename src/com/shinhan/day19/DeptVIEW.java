package com.shinhan.day19;

import java.util.List;
import com.shinhan.emp.EmpDTO;

public class DeptVIEW {

    private static final String LINE = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";

    public static void display(List<DeptDTO> deptlist) {
        if (deptlist == null || deptlist.isEmpty()) {
            display("🌸 해당하는 부서가 존재하지 않습니다 🌸");
            return;
        }

        System.out.println("\n🌟 부서 목록 조회 결과 🌟");
        System.out.println(LINE);
        deptlist.forEach(dept -> {
            System.out.println("📌 부서 정보");
            System.out.println(dept.toString());
        });
    }

    public static void display(DeptDTO dept) {
        if (dept == null) {
            display("❌ 해당하는 부서가 존재하지 않습니다 ❌");
            return;
        }

        System.out.println("\n🎯 부서 상세 정보 🎯");
        System.out.println(LINE);
        System.out.println(dept.toString());
        System.out.println(LINE);
    }

    public static void display(String message) {
        System.out.println("\n🔔 알림: " + message + "\n");
    }
}
