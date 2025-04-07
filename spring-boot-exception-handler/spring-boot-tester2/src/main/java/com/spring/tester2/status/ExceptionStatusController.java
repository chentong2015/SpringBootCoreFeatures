package com.spring.tester2.status;

import com.spring.tester2.status.exception.BadArgumentsException;
import com.spring.tester2.status.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionStatusController {

    // 测试异常的抛出会自动被解析成对应的ResponseEntity返回
    @GetMapping("/exception/{exception_id}")
    public ResponseEntity<String> getSpecificException(@PathVariable("exception_id") String pException) {
        if("not_found".equals(pException)) {
            throw new ResourceNotFoundException("resource not found");
        } else if("bad_arguments".equals(pException)) {
            throw new BadArgumentsException("bad arguments");
        } else {
            System.out.println("Request successfully");
            return new ResponseEntity<>("Request OK", HttpStatus.OK);
        }
    }
}
