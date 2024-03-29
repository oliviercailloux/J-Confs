/*
 * LocationIQ LocationIQ provides flexible enterprise-grade location based solutions. We work with
 * developers, startups and enterprises worldwide serving billions of requests everyday. This page
 * provides an overview of the technical aspects of our API and will help you get started.
 *
 * The version of the OpenAPI document: 1.1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech Do not edit the class manually.
 */

package com.locationiq.client;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen",
    date = "2020-02-21T10:08:21.449715+05:30[Asia/Kolkata]")
public class Configuration {
  private static ApiClient defaultApiClient = new ApiClient();

  /**
   * Get the default API client, which would be used when creating API instances without providing
   * an API client.
   *
   * @return Default API client
   */
  public static ApiClient getDefaultApiClient() {
    return defaultApiClient;
  }

  /**
   * Set the default API client, which would be used when creating API instances without providing
   * an API client.
   *
   * @param apiClient API client
   */
  public static void setDefaultApiClient(ApiClient apiClient) {
    defaultApiClient = apiClient;
  }
}
