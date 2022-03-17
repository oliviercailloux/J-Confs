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

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-02-21T10:08:21.449715+05:30[Asia/Kolkata]")
public class Pair {
	private String name = "";
	private String value = "";

	public Pair(String name, String value) {
		setName(name);
		setValue(value);
	}

	private void setName(String name) {
		if (!isValidString(name)) {
			return;
		}

		this.name = name;
	}

	private void setValue(String value) {
		if (!isValidString(value)) {
			return;
		}

		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	private boolean isValidString(String arg) {
		if (arg == null) {
			return false;
		}

		if (arg.trim().isEmpty()) {
			return false;
		}

		return true;
	}
}
