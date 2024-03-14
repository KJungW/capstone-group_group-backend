package capstone.letcomplete.group_group.exception.handler;

import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.exception.ErrorResult;
import capstone.letcomplete.group_group.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionGlobalControllerAdvice {

    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataNotFoundException.class, InvalidInputException.class, MethodArgumentNotValidException.class})
    public ErrorResult WrongInputExceptionHandler(Exception e) {
        return new ErrorResult("BAD_INPUT", "데이터가 존재하지 않거나 입력이 잘못되었습니다.");
    }
}
