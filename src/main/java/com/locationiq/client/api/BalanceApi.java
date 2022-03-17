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

package com.locationiq.client.api;

import com.google.gson.reflect.TypeToken;
import com.locationiq.client.ApiCallback;
import com.locationiq.client.ApiClient;
import com.locationiq.client.ApiException;
import com.locationiq.client.ApiResponse;
import com.locationiq.client.Configuration;
import com.locationiq.client.Pair;
import com.locationiq.client.model.Balance;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalanceApi {
	private ApiClient localVarApiClient;

	public BalanceApi() {
		this(Configuration.getDefaultApiClient());
	}

	public BalanceApi(ApiClient apiClient) {
		this.localVarApiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return localVarApiClient;
	}

	public void setApiClient(ApiClient apiClient) {
		this.localVarApiClient = apiClient;
	}

	/**
	 * Build call for balance
	 * 
	 * @param _callback Callback for upload/download progress
	 * @return Call to execute
	 * @throws ApiException If fail to serialize the request body object
	 * @http.response.details
	 *                        <table summary="Response Details" border="1">
	 *                        <tr>
	 *                        <td>Status Code</td>
	 *                        <td>Description</td>
	 *                        <td>Response Headers</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>200</td>
	 *                        <td>OK</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>400</td>
	 *                        <td>Bad Request</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>401</td>
	 *                        <td>Unauthorized</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>403</td>
	 *                        <td>The request has been made from an unauthorized
	 *                        domain.</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>404</td>
	 *                        <td>No location or places were found for the given
	 *                        input</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>429</td>
	 *                        <td>Request exceeded the rate-limits set on your
	 *                        account</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>500</td>
	 *                        <td>Internal Server Error</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        </table>
	 */
	public okhttp3.Call balanceCall(final ApiCallback _callback) throws ApiException {
		Object localVarPostBody = null;

		// create path and map variables
		String localVarPath = "/balance.php";

		List<Pair> localVarQueryParams = new ArrayList<Pair>();
		List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
		Map<String, String> localVarHeaderParams = new HashMap<String, String>();
		Map<String, String> localVarCookieParams = new HashMap<String, String>();
		Map<String, Object> localVarFormParams = new HashMap<String, Object>();
		final String[] localVarAccepts = { "application/json" };
		final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
		if (localVarAccept != null) {
			localVarHeaderParams.put("Accept", localVarAccept);
		}

		final String[] localVarContentTypes = {

		};
		final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
		localVarHeaderParams.put("Content-Type", localVarContentType);

		String[] localVarAuthNames = new String[] { "key" };
		return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams,
				localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames,
				_callback);
	}

	@SuppressWarnings("rawtypes")
	private okhttp3.Call balanceValidateBeforeCall(final ApiCallback _callback) throws ApiException {

		okhttp3.Call localVarCall = balanceCall(_callback);
		return localVarCall;

	}

	/**
	 * 
	 * The Balance API provides a count of request credits left in the user&#39;s
	 * account for the day. Balance is reset at midnight UTC everyday (00:00 UTC).
	 * 
	 * @return Balance
	 * @throws ApiException If fail to call the API, e.g. server error or cannot
	 *                      deserialize the response body
	 * @http.response.details
	 *                        <table summary="Response Details" border="1">
	 *                        <tr>
	 *                        <td>Status Code</td>
	 *                        <td>Description</td>
	 *                        <td>Response Headers</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>200</td>
	 *                        <td>OK</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>400</td>
	 *                        <td>Bad Request</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>401</td>
	 *                        <td>Unauthorized</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>403</td>
	 *                        <td>The request has been made from an unauthorized
	 *                        domain.</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>404</td>
	 *                        <td>No location or places were found for the given
	 *                        input</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>429</td>
	 *                        <td>Request exceeded the rate-limits set on your
	 *                        account</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>500</td>
	 *                        <td>Internal Server Error</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        </table>
	 */
	public Balance balance() throws ApiException {
		ApiResponse<Balance> localVarResp = balanceWithHttpInfo();
		return localVarResp.getData();
	}

	/**
	 * 
	 * The Balance API provides a count of request credits left in the user&#39;s
	 * account for the day. Balance is reset at midnight UTC everyday (00:00 UTC).
	 * 
	 * @return ApiResponse&lt;Balance&gt;
	 * @throws ApiException If fail to call the API, e.g. server error or cannot
	 *                      deserialize the response body
	 * @http.response.details
	 *                        <table summary="Response Details" border="1">
	 *                        <tr>
	 *                        <td>Status Code</td>
	 *                        <td>Description</td>
	 *                        <td>Response Headers</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>200</td>
	 *                        <td>OK</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>400</td>
	 *                        <td>Bad Request</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>401</td>
	 *                        <td>Unauthorized</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>403</td>
	 *                        <td>The request has been made from an unauthorized
	 *                        domain.</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>404</td>
	 *                        <td>No location or places were found for the given
	 *                        input</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>429</td>
	 *                        <td>Request exceeded the rate-limits set on your
	 *                        account</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>500</td>
	 *                        <td>Internal Server Error</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        </table>
	 */
	public ApiResponse<Balance> balanceWithHttpInfo() throws ApiException {
		okhttp3.Call localVarCall = balanceValidateBeforeCall(null);
		Type localVarReturnType = new TypeToken<Balance>() {
		}.getType();
		return localVarApiClient.execute(localVarCall, localVarReturnType);
	}

	/**
	 * (asynchronously) The Balance API provides a count of request credits left in
	 * the user&#39;s account for the day. Balance is reset at midnight UTC everyday
	 * (00:00 UTC).
	 * 
	 * @param _callback The callback to be executed when the API call finishes
	 * @return The request call
	 * @throws ApiException If fail to process the API call, e.g. serializing the
	 *                      request body object
	 * @http.response.details
	 *                        <table summary="Response Details" border="1">
	 *                        <tr>
	 *                        <td>Status Code</td>
	 *                        <td>Description</td>
	 *                        <td>Response Headers</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>200</td>
	 *                        <td>OK</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>400</td>
	 *                        <td>Bad Request</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>401</td>
	 *                        <td>Unauthorized</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>403</td>
	 *                        <td>The request has been made from an unauthorized
	 *                        domain.</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>404</td>
	 *                        <td>No location or places were found for the given
	 *                        input</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>429</td>
	 *                        <td>Request exceeded the rate-limits set on your
	 *                        account</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        <tr>
	 *                        <td>500</td>
	 *                        <td>Internal Server Error</td>
	 *                        <td>-</td>
	 *                        </tr>
	 *                        </table>
	 */
	public okhttp3.Call balanceAsync(final ApiCallback<Balance> _callback) throws ApiException {

		okhttp3.Call localVarCall = balanceValidateBeforeCall(_callback);
		Type localVarReturnType = new TypeToken<Balance>() {
		}.getType();
		localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
		return localVarCall;
	}
}
