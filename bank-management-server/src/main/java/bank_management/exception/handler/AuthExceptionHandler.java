package bank_management.exception.handler;

import bank_management.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthExceptionHandler { // Bắt những lỗi về Authentication, Authorization, Register, ForgotPassword

	@ExceptionHandler({
		BadCredentialsException.class,
		LockedException.class,
		DisabledException.class,
		AuthenticationException.class
	})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseResult handleAuthException(Exception  e) {
		return new ResponseResult (
			"Username hoặc password không đúng",
			bank_management.enumeration.ResponseStatus.Invalid
		);
	}
}

/*
	A DisabledException must be thrown if an account is disabled and the AuthenticationManager can test for this state.
	A LockedException must be thrown if an account is locked and the AuthenticationManager can test for account locking.
	A BadCredentialsException must be thrown if incorrect credentials arepresented. Whilst the above exceptions are optional, an AuthenticationManager must always test credentials.
*/