package com.nagarro.miniassignment.exception;

//import java.net.http.HttpTimeoutException;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.Date;

import javax.net.ssl.SSLHandshakeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.nagarro.miniassignment.dto.APIReponse;

import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.ssl.SslHandshakeTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.WriteTimeoutException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<APIReponse> IllegalArgumentExceptionHandler(IllegalArgumentException e)
	{
		return new ResponseEntity<APIReponse>(new APIReponse(e.getMessage(), HttpStatus.BAD_REQUEST.value() , new Date()), HttpStatus.BAD_REQUEST);
	}
		
	@ExceptionHandler(PageNotFoundException.class)
	public ResponseEntity<APIReponse> PageNotFoundExceptionHandler(PageNotFoundException e){
		return new ResponseEntity<APIReponse>(new APIReponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), new Date()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<APIReponse> MethoArgumentInvalidHandler(MethodArgumentTypeMismatchException e){

		APIReponse apiReponse= new APIReponse("For \""+e.getValue()+"\" "+e.getRequiredType()+" type value is expected!!", HttpStatus.BAD_REQUEST.value(), new Date());
		return new ResponseEntity<APIReponse>(apiReponse, HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler(HttpTimeoutException.class)
//	public ResponseEntity<APIReponse> HttpTimeoutExceptionHandler(HttpTimeoutException ex){
//		return new ResponseEntity<APIReponse>(new APIReponse(ex.getMessage(), HttpStatus.REQUEST_TIMEOUT.value(), new Date()), HttpStatus.REQUEST_TIMEOUT);
//	}
	
	@ExceptionHandler(ReadTimeoutException.class)
	public ResponseEntity<APIReponse> ReadTimeoutExceptionHandler(ReadTimeoutException ex){
		return new ResponseEntity<APIReponse>(new APIReponse(ex.getMessage(), HttpStatus.REQUEST_TIMEOUT.value(), new Date()), HttpStatus.REQUEST_TIMEOUT);
	}
	
	@ExceptionHandler(SslHandshakeTimeoutException.class)
	public ResponseEntity<APIReponse> SSLHandshakeExceptionTimeoutHandler(SslHandshakeTimeoutException ex){
		return new ResponseEntity<APIReponse>(new APIReponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), new Date()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SSLHandshakeException.class)
	public ResponseEntity<APIReponse> SSLHandshakeExceptionHandler(SSLHandshakeException ex){
		return new ResponseEntity<APIReponse>(new APIReponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), new Date()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConnectTimeoutException.class)
	public ResponseEntity<APIReponse> ConnectTimeoutExceptionHandler(ConnectTimeoutException ex){
		return new ResponseEntity<APIReponse>(new APIReponse(ex.getMessage(), HttpStatus.REQUEST_TIMEOUT.value(), new Date()), HttpStatus.REQUEST_TIMEOUT);
	}
	
	@ExceptionHandler(WriteTimeoutException.class)
	public ResponseEntity<APIReponse> WriteTimeoutExceptionHandler(WriteTimeoutException ex){
		return new ResponseEntity<APIReponse>(new APIReponse(ex.getMessage(), HttpStatus.REQUEST_TIMEOUT.value(), new Date()), HttpStatus.REQUEST_TIMEOUT);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<APIReponse> ServerWebInputExceptionHandler(MissingServletRequestParameterException e){
		return new ResponseEntity<APIReponse>(new APIReponse(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value(), new Date()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InterruptedByTimeoutException.class)
	public ResponseEntity<APIReponse> InterruptedByTimeoutExceptionHandler(InterruptedByTimeoutException e) {
		return new ResponseEntity<APIReponse>(new APIReponse(e.getMessage(), HttpStatus.REQUEST_TIMEOUT.value(), new Date()), HttpStatus.REQUEST_TIMEOUT);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<APIReponse> InternalServerErrorHandler(Exception e){
		return new ResponseEntity<APIReponse>(new APIReponse("SomeThing went Wrong!! Please try again later...", HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
