package com.deploywatch.config;
import com.deploywatch.service.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> notFound(ResourceNotFoundException ex){return response(HttpStatus.NOT_FOUND,ex.getMessage());}
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> badRequest(IllegalArgumentException ex){return response(HttpStatus.BAD_REQUEST,ex.getMessage());}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException ex){
        String message=ex.getBindingResult().getFieldErrors().stream().findFirst().map(e->e.getField()+" "+e.getDefaultMessage()).orElse("Invalid request");
        return response(HttpStatus.BAD_REQUEST,message);
    }
    private ResponseEntity<Map<String,Object>> response(HttpStatus status,String message){
        Map<String,Object> body=new LinkedHashMap<>();body.put("timestamp",LocalDateTime.now());body.put("status",status.value());body.put("error",message);
        return ResponseEntity.status(status).body(body);
    }
}
