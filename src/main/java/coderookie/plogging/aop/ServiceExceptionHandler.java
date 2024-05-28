package coderookie.plogging.aop;

import coderookie.plogging.dto.response.ResponseDto;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceExceptionHandler {

    @AfterThrowing(pointcut = "execution(* coderookie.plogging.service..*(..))", throwing = "exception")
    public ResponseEntity<?> handleServiceExceptions(Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }
}
