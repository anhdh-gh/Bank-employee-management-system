package bank_management.api;

import bank_management.dto.DetailSalaryDto;
import bank_management.dto.ResponseResult;
import bank_management.dto.SalaryDto;
import bank_management.enumeration.ResponseStatus;
import bank_management.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity calcBatchSalary(int month, int year) {
        List<SalaryDto> salaryDtoList = salaryService.calcSalary(month, year);
        if (salaryDtoList == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Lương tháng " + month + "/" + year + " đã được tính!", ResponseStatus.Error));
        } else {
            return ResponseEntity
                    .ok()
                    .body(new ResponseResult(
                          salaryDtoList,
                          "Lương tháng " + month + "/" + year + "được tính thành công",
                          ResponseStatus.Success
                    ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getSalaryListForEachEmployee(@PathVariable(value = "id") String employeeID) {
        List<SalaryDto> salaryDtoList = salaryService.getSalaryLístForEachEmployee(employeeID);
        if (salaryDtoList == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Không tồn tại nhân viên có ID là " + employeeID, ResponseStatus.Error));
        } else return ResponseEntity
                .ok()
                .body(new ResponseResult(
                        salaryDtoList,
                        "Lấy thông tin bảng lương của nhân viên có ID "+ employeeID + " thành công!",
                        ResponseStatus.Success));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity getDetailSalary(@PathVariable(value = "id") String salaryID) {
        DetailSalaryDto detailSalaryDto = salaryService.getDetailSalary(salaryID);
        if (detailSalaryDto == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Không tồn tại bảng lương có ID là " + salaryID, ResponseStatus.Error));
        } else return ResponseEntity
                .ok()
                .body(new ResponseResult(
                        detailSalaryDto,
                        "Lấy thông tin chi tiết lương thành công!",
                        ResponseStatus.Success));
    }
}
