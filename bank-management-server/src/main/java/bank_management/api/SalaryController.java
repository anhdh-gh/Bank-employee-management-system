package bank_management.api;

import bank_management.payload.DetailSalary;
import bank_management.payload.ResponseResult;
import bank_management.dto.SalaryDto;
import bank_management.enumeration.ResponseStatus;
import bank_management.payload.SalaryRequest;
import bank_management.payload.SearchSalaryRequest;
import bank_management.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/search")
    public ResponseResult searchSalaries(SearchSalaryRequest searchSalaryRequest) {
        List<SalaryDto> salaryDtoList = salaryService.searchSalaries(searchSalaryRequest);
        return new ResponseResult(
                salaryDtoList,
                "Lấy thông tin lương tất cả nhân viên thành công",
                ResponseStatus.Success
        );
    }

//    @GetMapping("/{id}")
//    public ResponseEntity getSalaryListForEachEmployee(@PathVariable(value = "id") String employeeID) {
//        List<SalaryDto> salaryDtoList = salaryService.getSalaryLístForEachEmployee(employeeID);
//        if (salaryDtoList == null) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new ResponseResult("Không tồn tại nhân viên có ID là " + employeeID, ResponseStatus.Error));
//        } else return ResponseEntity
//                .ok()
//                .body(new ResponseResult(
//                        salaryDtoList,
//                        "Lấy thông tin bảng lương của nhân viên có ID "+ employeeID + " thành công!",
//                        ResponseStatus.Success));
//    }

    @GetMapping("/detail/{id}")
    public ResponseEntity getDetailSalary(@PathVariable(value = "id") String salaryID) {
        DetailSalary detailSalary = salaryService.getDetailSalary(salaryID);
        if (detailSalary == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseResult("Không tồn tại bảng lương có ID là " + salaryID, ResponseStatus.Error));
        } else return ResponseEntity
                .ok()
                .body(new ResponseResult(
                        detailSalary,
                        "Lấy thông tin chi tiết lương thành công!",
                        ResponseStatus.Success));
    }
}
