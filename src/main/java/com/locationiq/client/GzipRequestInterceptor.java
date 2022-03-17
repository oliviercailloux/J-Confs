/*
 * LocationIQ
 * LocationIQ provides flexible enterprise-grade location based solutions. We work with developers, startups and enterprises worldwide serving billions of requests everyday. This page provides an overview of the technical aspects of our API and will help you get started.
 *
 * The version of the OpenAPI document: 1.1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.locationiq.client;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Encodes request bodies using gzip.
 *
 * Taken from https://github.com/square/okhttp/issues/350
 */
class GzipRequestInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request originalRequest = chain.request();
		if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
			return chain.proceed(originalRequest);
		}

		Request compressedRequest = originalRequest.newBuilder().header("Content-Encoding", "gzip")
				.method(originalRequest.method(), forceContentLength(gzip(originalRequest.body()))).build();
		return chain.proceed(compressedRequest);
	}

	private RequestBody forceContentLength(final RequestBody requestBody) throws IOException {
		final Buffer buffer = new Buffer();
		requestBody.writeTo(buffer);
		return new RequestBody() {
			@Override
			public MediaType contentType() {
				return requestBody.contentType();
			}

			@Override
			public long contentLength() {
				return buffer.size();
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				sink.write(buffer.snapshot());
			}
		};
	}

	private RequestBody gzip(final RequestBody body) {
		return new RequestBody() {
			@Override
			public MediaType contentType() {
				return body.contentType();
			}

			@Override
			public long contentLength() {
				return -1; // We don't know the compressed length in advance!
			}

			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
				body.writeTo(gzipSink);
				gzipSink.close();
			}
		};
	}
}
