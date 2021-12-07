package bank_management.api;

import bank_management.dto.ResponseResult;
import bank_management.dto.SalaryDto;
import bank_management.enumeration.ResponseStatus;
import bank_management.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {
    @Autowired
    SalaryService salaryService;

    @GetMapping
    public ResponseResult getAllSalary() {
        List<SalaryDto> salaryDtoList = salaryService.getAllSalary();
        return new ResponseResult(
                salaryDtoList,
                "Lấy thông tin lương tất cả nhân viên thành công",
                ResponseStatus.Success
        );
    }

    @GetMapping("/calcSalary")
    public ResponseResult calcBatchSalary(int month, int year) {

        return null;
    }

    @GetMapping("/{id}")
    public ResponseResult getSalaryListForEachEmployee(@PathVariable(value = "id") String employeeID){
        List<SalaryDto> salaryDtoList = salaryService.getSalaryLístForEachEmployee(employeeID);
        return new ResponseResult(
                salaryDtoList,
                "Lấy thông tin lương của nhân viên có ID " + employeeID + " thành công",
                ResponseStatus.Success
        );
    }

    public ResponseResult getDetailSalary() {
        return null;
    }
}
