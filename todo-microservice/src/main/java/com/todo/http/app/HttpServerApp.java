package com.todo.http.app;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import org.apache.http.ConnectionClosedException;
import org.apache.http.ExceptionLogger;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.ImmutableHttpProcessor;

import com.todo.http.handler.EmployeeHttpRequestHandler;

public class HttpServerApp {

	public static void main(String[] args) throws Exception {

		System.out.println("START");
		HttpRequestHandler requestHandler = new EmployeeHttpRequestHandler();
		HttpRequestInterceptor httpInterCeptor = new DefaultHttpRequestInterceptor();
		ImmutableHttpProcessor httpProcessor = new ImmutableHttpProcessor(httpInterCeptor);
		SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(15000).setTcpNoDelay(true).build();
		final HttpServer server = ServerBootstrap.bootstrap().setListenerPort(1234).setHttpProcessor(httpProcessor)
				.setSocketConfig(socketConfig).setExceptionLogger(new StdErrorExceptionLogger())
				.registerHandler("*", requestHandler).create();
		server.start();
		configureShutDownHook(server);

		server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

		System.out.println("END");

	}

	private static void configureShutDownHook(final HttpServer server) {
		System.out.println("before hook");
		Runtime.getRuntime().addShutdownHook(new Thread() {

			{
				System.out.println("addShutdownHook");
			}

			@Override
			public void run() {
				System.out.println("run: shutdonw()");
				server.shutdown(5, TimeUnit.SECONDS);
			}
		});
	}

	static class StdErrorExceptionLogger implements ExceptionLogger {

		// @Override
		public void log(final Exception ex) {
			if (ex instanceof SocketTimeoutException) {
				System.err.println("Connection timed out");
			} else if (ex instanceof ConnectionClosedException) {
				System.err.println(ex.getMessage());
			} else {
				ex.printStackTrace();
			}
		}

	}

}
