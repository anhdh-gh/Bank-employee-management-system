package bank_management.exception.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import bank_management.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApiExceptionHandler { // Bắt những exception chung của hệ thống

	// Xử lý lỗi validation bị fail
	@ExceptionHandler(BindException.class) 
	@ResponseStatus(HttpStatus.BAD_REQUEST)  // Nếu validate fail thì trả về 400
	public ResponseResult handleBindException(BindException e) {
		String errorMessage = "Request không hợp lệ";
		
	    // Trả về message của lỗi đầu tiên    
	    if (e.getBindingResult().hasErrors())
	    	errorMessage = e.getBindingResult()
	    					.getAllErrors()
	    					.get(0)
	    					.getDefaultMessage();

		return new ResponseResult (
			errorMessage,
			bank_management.enumeration.ResponseStatus.Invalid
		);
	}

    // Bắt Exception.class
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult handleUnwantedException(Exception e) {
    	
        // Log lỗi ra và ẩn đi message thực sự của hệ thống    	
    	Logger.getLogger(ApiExceptionHandler.class.getName()).log(Level.SEVERE, null, e);

		return new ResponseResult (
			e.getMessage(),
			bank_management.enumeration.ResponseStatus.Error
		);
    }
}

// https://loda.me/articles/sb15-exception-handling-exceptionhandler-restcontrolleradvice-controlleradvice-responsestatus
// https://viblo.asia/p/xu-ly-exception-phat-sinh-trong-ung-dung-spring-boot-6J3ZgWkLZmB
// https://www.baeldung.com/spring-response-entity
// https://hocspringboot.net/2021/04/16/spring-boot-exception-handling/

// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html