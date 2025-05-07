package com.shinhan.emp;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO(Data Transfer Object)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmpDTO {    // -> javaBeans 기술에서 사용 가능해야 
    private int employee_id;       // EMPLOYEE_ID
    private String first_name;         // FIRST_NAME
    private String last_name;          // LAST_NAME
    private String email;             // EMAIL
    private String phone_number;       // PHONE_NUMBER
    private Date hire_date;       // HIRE_DATE
    private String job_id;             // JOB_ID
    private Double salary;            // SALARY
    private Double commission_pct;     // COMMISSION_PCT
    private int manager_id;        // MANAGER_ID
    private int department_id;     // DEPARTMENT_ID

    // 생성자, getter, setter, toString 등은 IDE 자동 생성 또는 Lombok 사용 가능
}


