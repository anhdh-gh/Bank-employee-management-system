package bank_management.payload;

import bank_management.enumeration.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult {
    private Object data;
    private String message;
    private ResponseStatus responseStatus;

    public ResponseResult(String message, ResponseStatus responseStatus) {
        this.message = message;
        this.responseStatus = responseStatus;
    }
}
