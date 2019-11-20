package com.todo.http.handler;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

public class DefaultHttpRequestHandler implements HttpRequestHandler {

	@Override
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext arg2)
			throws HttpException, IOException {
		System.out.println("In DefaultHttpRequestHandler:handle()");
		httpResponse.setStatusCode(HttpStatus.SC_OK);
		httpResponse.setEntity(new StringEntity("Default Request Handler", ContentType.TEXT_PLAIN));

	}

}
