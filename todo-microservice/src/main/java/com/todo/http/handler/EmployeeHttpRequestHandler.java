package com.todo.http.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.RequestLine;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

public class EmployeeHttpRequestHandler implements HttpRequestHandler {
	PrintStream out = System.out;

	@Override
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext context) throws IOException {
		System.out.println("In EmployeeHttpRequestHandler:handle()");
		RequestLine requestLine = httpRequest.getRequestLine();
		out.print("requestLine: " + requestLine);
		out.print("ProtocolVersion: " + httpRequest.getProtocolVersion());
		Header[] allHeaders = httpRequest.getAllHeaders();
		for (Header header : allHeaders) {
			System.out.println(header.getName() + " : " + header.getValue());
		}
//		out.print("httpRequest: " + httpRequest.getClass().getCanonicalName());

		BasicHttpEntityEnclosingRequest newReq = (BasicHttpEntityEnclosingRequest) httpRequest;
		HttpEntity entity = newReq.getEntity();
		InputStream inputStream = entity.getContent();
		printContent(inputStream);
		httpResponse.setStatusCode(HttpStatus.SC_OK);
		httpResponse.setEntity(new StringEntity("some important message", ContentType.TEXT_PLAIN));

	}

	private void printContent(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String data = null;

		while ((data = br.readLine()) != null) {
			System.out.print(data);
		}

	}

	public void print(Object obj) {
		System.out.println(obj);
		System.out.println("\n");
	}
}
