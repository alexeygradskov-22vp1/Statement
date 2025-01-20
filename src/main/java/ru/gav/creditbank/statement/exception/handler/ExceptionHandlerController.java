package ru.gav.creditbank.statement.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gav.creditbank.statement.exception.model.ErrorModel;

@ControllerAdvice
public class ExceptionHandlerController {
 private final String PRESCORE_ERROR_MESSAGE="Произошла ошибка при прескоринге\n";

    @ExceptionHandler()
    public ResponseEntity<ErrorModel> methodArgumentNotValidHandler(MethodArgumentNotValidException e){
        StringBuilder stringBuilder = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            stringBuilder.append(String.format("%s:%s",fieldName, errorMessage));
        });
        return ResponseEntity.badRequest().body(new ErrorModel(PRESCORE_ERROR_MESSAGE,stringBuilder.toString()));
    }
}
