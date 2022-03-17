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
import com.locationiq.client.model.DirectionsMatrix;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixApi {
	private ApiClient localVarApiClient;

	public MatrixApi() {
		this(Configuration.getDefaultApiClient());
	}

	public MatrixApi(ApiClient apiClient) {
		this.localVarApiClient = apiClient;
	}

	public ApiClient getApiClient() {
		return localVarApiClient;
	}

	public void setApiClient(ApiClient apiClient) {
		this.localVarApiClient = apiClient;
	}

	/**
	 * Build call for matrix
	 * 
	 * @param coordinates        String of format
	 *                           {longitude},{latitude};{longitude},{latitude}[;{longitude},{latitude}
	 *                           ...] or polyline({polyline}) or
	 *                           polyline6({polyline6}). polyline follows
	 *                           Google&#39;s polyline format with precision 5
	 *                           (required)
	 * @param bearings           Limits the search to segments with given bearing in
	 *                           degrees towards true north in clockwise direction.
	 *                           List of positive integer pairs separated by
	 *                           semi-colon and bearings array should be equal to
	 *                           length of coordinate array. Input Value :-
	 *                           {bearing};{bearing}[;{bearing} ...] Bearing follows
	 *                           the following format : bearing {value},{range}
	 *                           integer 0 .. 360,integer 0 .. 180 (optional)
	 * @param radiuses           Limits the search to given radius in meters
	 *                           Radiuses array length should be same as coordinates
	 *                           array, eaach value separated by semi-colon. Input
	 *                           Value - {radius};{radius}[;{radius} ...] Radius has
	 *                           following format :- double &gt;&#x3D; 0 or
	 *                           unlimited (default) (optional)
	 * @param generateHints      Adds a Hint to the response which can be used in
	 *                           subsequent requests, see hints parameter. Input
	 *                           Value - true (default), false Format - Base64
	 *                           String (optional)
	 * @param approaches         Keep waypoints on curb side. Input Value -
	 *                           {approach};{approach}[;{approach} ...] Format -
	 *                           curb or unrestricted (default) (optional)
	 * @param exclude            Additive list of classes to avoid, order does not
	 *                           matter. input Value - {class}[,{class}] Format - A
	 *                           class name determined by the profile or none.
	 *                           (optional)
	 * @param annotations        Returns additional metadata for each coordinate
	 *                           along the route geometry. [ true, false (default),
	 *                           nodes, distance, duration, datasources, weight,
	 *                           speed ] (optional)
	 * @param sources            Use location with given index as source. [
	 *                           {index};{index}[;{index} ...] or all (default) ]
	 *                           &#x3D;&gt; index 0 &lt;&#x3D; integer &lt;
	 *                           #locations (optional)
	 * @param destinations       Use location with given index as destination. [
	 *                           {index};{index}[;{index} ...] or all (default) ]
	 *                           (optional)
	 * @param fallbackSpeed      If no route found between a source/destination
	 *                           pair, calculate the as-the-crow-flies distance,
	 *                           then use this speed to estimate duration. double
	 *                           &gt; 0 (optional)
	 * @param fallbackCoordinate When using a fallback_speed, use the user-supplied
	 *                           coordinate (input), or the snapped location
	 *                           (snapped) for calculating distances. [ input
	 *                           (default), or snapped ] (optional, default to
	 *                           &quot;\&quot;input\&quot;&quot;)
	 * @param _callback          Callback for upload/download progress
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
	public okhttp3.Call matrixCall(String coordinates, String bearings, String radiuses, String generateHints,
			String approaches, String exclude, String annotations, Integer sources, Integer destinations,
			BigDecimal fallbackSpeed, String fallbackCoordinate, final ApiCallback _callback) throws ApiException {
		Object localVarPostBody = null;

		// create path and map variables
		String localVarPath = "/matrix/driving/{coordinates}".replaceAll("\\{" + "coordinates" + "\\}",
				localVarApiClient.escapeString(coordinates.toString()));

		List<Pair> localVarQueryParams = new ArrayList<Pair>();
		List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
		if (bearings != null) {
			localVarQueryParams.addAll(localVarApiClient.parameterToPair("bearings", bearings));
		}

		if (radiuses != null) {
			localVarQueryParams.addAll(localVarApiClient.parameterToPair("radiuses", radiuses));
		}

		if (generateHints != null) {
			localVarQueryParams.addAll(localVarApiClient.parameterToPair("generate_hints", generateHints));
		}

		if (approaches != null) {
			localVarQueryParams.addAll(localVarApiClient.parameterToPair("approaches", approaches));
		}

		if (exclude != null) {
			localVarQueryParams.addAll(localVarApiClient.parameterToPair("exclude", exclude));
		}

		if (annotations != null) {
			localVarQueryParams.addAll(localVarApiClient.parameterToPair("annotations", annotations));
		}

		if (sources != null) {
			localVarQueryParams.addAll(localVarApiClient.parameterToPair("sources", sources));
		}

		if (destinations != null) {
			localVarQueryParams.addAll(localVarApiClient.parameterToPair("destinations", destinations));
		}

		if (fallbackSpeed != null) {
			localVarQueryParams.addAll(localVarApiClient.parameterToPair("fallback_speed", fallbackSpeed));
		}

		if (fallbackCoordinate != null) {
			localVarQueryParams.addAll(localVarApiClient.parameterToPair("fallback_coordinate", fallbackCoordinate));
		}

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
	private okhttp3.Call matrixValidateBeforeCall(String coordinates, String bearings, String radiuses,
			String generateHints, String approaches, String exclude, String annotations, Integer sources,
			Integer destinations, BigDecimal fallbackSpeed, String fallbackCoordinate, final ApiCallback _callback)
			throws ApiException {

		// verify the required parameter 'coordinates' is set
		if (coordinates == null) {
			throw new ApiException("Missing the required parameter 'coordinates' when calling matrix(Async)");
		}

		okhttp3.Call localVarCall = matrixCall(coordinates, bearings, radiuses, generateHints, approaches, exclude,
				annotations, sources, destinations, fallbackSpeed, fallbackCoordinate, _callback);
		return localVarCall;

	}

	/**
	 * Matrix Service Computes duration of the fastest route between all pairs of
	 * supplied coordinates. Returns the durations or distances or both between the
	 * coordinate pairs. Note that the distances are not the shortest distance
	 * between two coordinates, but rather the distances of the fastest routes.
	 * 
	 * @param coordinates        String of format
	 *                           {longitude},{latitude};{longitude},{latitude}[;{longitude},{latitude}
	 *                           ...] or polyline({polyline}) or
	 *                           polyline6({polyline6}). polyline follows
	 *                           Google&#39;s polyline format with precision 5
	 *                           (required)
	 * @param bearings           Limits the search to segments with given bearing in
	 *                           degrees towards true north in clockwise direction.
	 *                           List of positive integer pairs separated by
	 *                           semi-colon and bearings array should be equal to
	 *                           length of coordinate array. Input Value :-
	 *                           {bearing};{bearing}[;{bearing} ...] Bearing follows
	 *                           the following format : bearing {value},{range}
	 *                           integer 0 .. 360,integer 0 .. 180 (optional)
	 * @param radiuses           Limits the search to given radius in meters
	 *                           Radiuses array length should be same as coordinates
	 *                           array, eaach value separated by semi-colon. Input
	 *                           Value - {radius};{radius}[;{radius} ...] Radius has
	 *                           following format :- double &gt;&#x3D; 0 or
	 *                           unlimited (default) (optional)
	 * @param generateHints      Adds a Hint to the response which can be used in
	 *                           subsequent requests, see hints parameter. Input
	 *                           Value - true (default), false Format - Base64
	 *                           String (optional)
	 * @param approaches         Keep waypoints on curb side. Input Value -
	 *                           {approach};{approach}[;{approach} ...] Format -
	 *                           curb or unrestricted (default) (optional)
	 * @param exclude            Additive list of classes to avoid, order does not
	 *                           matter. input Value - {class}[,{class}] Format - A
	 *                           class name determined by the profile or none.
	 *                           (optional)
	 * @param annotations        Returns additional metadata for each coordinate
	 *                           along the route geometry. [ true, false (default),
	 *                           nodes, distance, duration, datasources, weight,
	 *                           speed ] (optional)
	 * @param sources            Use location with given index as source. [
	 *                           {index};{index}[;{index} ...] or all (default) ]
	 *                           &#x3D;&gt; index 0 &lt;&#x3D; integer &lt;
	 *                           #locations (optional)
	 * @param destinations       Use location with given index as destination. [
	 *                           {index};{index}[;{index} ...] or all (default) ]
	 *                           (optional)
	 * @param fallbackSpeed      If no route found between a source/destination
	 *                           pair, calculate the as-the-crow-flies distance,
	 *                           then use this speed to estimate duration. double
	 *                           &gt; 0 (optional)
	 * @param fallbackCoordinate When using a fallback_speed, use the user-supplied
	 *                           coordinate (input), or the snapped location
	 *                           (snapped) for calculating distances. [ input
	 *                           (default), or snapped ] (optional, default to
	 *                           &quot;\&quot;input\&quot;&quot;)
	 * @return DirectionsMatrix
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
	public DirectionsMatrix matrix(String coordinates, String bearings, String radiuses, String generateHints,
			String approaches, String exclude, String annotations, Integer sources, Integer destinations,
			BigDecimal fallbackSpeed, String fallbackCoordinate) throws ApiException {
		ApiResponse<DirectionsMatrix> localVarResp = matrixWithHttpInfo(coordinates, bearings, radiuses, generateHints,
				approaches, exclude, annotations, sources, destinations, fallbackSpeed, fallbackCoordinate);
		return localVarResp.getData();
	}

	/**
	 * Matrix Service Computes duration of the fastest route between all pairs of
	 * supplied coordinates. Returns the durations or distances or both between the
	 * coordinate pairs. Note that the distances are not the shortest distance
	 * between two coordinates, but rather the distances of the fastest routes.
	 * 
	 * @param coordinates        String of format
	 *                           {longitude},{latitude};{longitude},{latitude}[;{longitude},{latitude}
	 *                           ...] or polyline({polyline}) or
	 *                           polyline6({polyline6}). polyline follows
	 *                           Google&#39;s polyline format with precision 5
	 *                           (required)
	 * @param bearings           Limits the search to segments with given bearing in
	 *                           degrees towards true north in clockwise direction.
	 *                           List of positive integer pairs separated by
	 *                           semi-colon and bearings array should be equal to
	 *                           length of coordinate array. Input Value :-
	 *                           {bearing};{bearing}[;{bearing} ...] Bearing follows
	 *                           the following format : bearing {value},{range}
	 *                           integer 0 .. 360,integer 0 .. 180 (optional)
	 * @param radiuses           Limits the search to given radius in meters
	 *                           Radiuses array length should be same as coordinates
	 *                           array, eaach value separated by semi-colon. Input
	 *                           Value - {radius};{radius}[;{radius} ...] Radius has
	 *                           following format :- double &gt;&#x3D; 0 or
	 *                           unlimited (default) (optional)
	 * @param generateHints      Adds a Hint to the response which can be used in
	 *                           subsequent requests, see hints parameter. Input
	 *                           Value - true (default), false Format - Base64
	 *                           String (optional)
	 * @param approaches         Keep waypoints on curb side. Input Value -
	 *                           {approach};{approach}[;{approach} ...] Format -
	 *                           curb or unrestricted (default) (optional)
	 * @param exclude            Additive list of classes to avoid, order does not
	 *                           matter. input Value - {class}[,{class}] Format - A
	 *                           class name determined by the profile or none.
	 *                           (optional)
	 * @param annotations        Returns additional metadata for each coordinate
	 *                           along the route geometry. [ true, false (default),
	 *                           nodes, distance, duration, datasources, weight,
	 *                           speed ] (optional)
	 * @param sources            Use location with given index as source. [
	 *                           {index};{index}[;{index} ...] or all (default) ]
	 *                           &#x3D;&gt; index 0 &lt;&#x3D; integer &lt;
	 *                           #locations (optional)
	 * @param destinations       Use location with given index as destination. [
	 *                           {index};{index}[;{index} ...] or all (default) ]
	 *                           (optional)
	 * @param fallbackSpeed      If no route found between a source/destination
	 *                           pair, calculate the as-the-crow-flies distance,
	 *                           then use this speed to estimate duration. double
	 *                           &gt; 0 (optional)
	 * @param fallbackCoordinate When using a fallback_speed, use the user-supplied
	 *                           coordinate (input), or the snapped location
	 *                           (snapped) for calculating distances. [ input
	 *                           (default), or snapped ] (optional, default to
	 *                           &quot;\&quot;input\&quot;&quot;)
	 * @return ApiResponse&lt;DirectionsMatrix&gt;
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
	public ApiResponse<DirectionsMatrix> matrixWithHttpInfo(String coordinates, String bearings, String radiuses,
			String generateHints, String approaches, String exclude, String annotations, Integer sources,
			Integer destinations, BigDecimal fallbackSpeed, String fallbackCoordinate) throws ApiException {
		okhttp3.Call localVarCall = matrixValidateBeforeCall(coordinates, bearings, radiuses, generateHints, approaches,
				exclude, annotations, sources, destinations, fallbackSpeed, fallbackCoordinate, null);
		Type localVarReturnType = new TypeToken<DirectionsMatrix>() {
		}.getType();
		return localVarApiClient.execute(localVarCall, localVarReturnType);
	}

	/**
	 * Matrix Service (asynchronously) Computes duration of the fastest route
	 * between all pairs of supplied coordinates. Returns the durations or distances
	 * or both between the coordinate pairs. Note that the distances are not the
	 * shortest distance between two coordinates, but rather the distances of the
	 * fastest routes.
	 * 
	 * @param coordinates        String of format
	 *                           {longitude},{latitude};{longitude},{latitude}[;{longitude},{latitude}
	 *                           ...] or polyline({polyline}) or
	 *                           polyline6({polyline6}). polyline follows
	 *                           Google&#39;s polyline format with precision 5
	 *                           (required)
	 * @param bearings           Limits the search to segments with given bearing in
	 *                           degrees towards true north in clockwise direction.
	 *                           List of positive integer pairs separated by
	 *                           semi-colon and bearings array should be equal to
	 *                           length of coordinate array. Input Value :-
	 *                           {bearing};{bearing}[;{bearing} ...] Bearing follows
	 *                           the following format : bearing {value},{range}
	 *                           integer 0 .. 360,integer 0 .. 180 (optional)
	 * @param radiuses           Limits the search to given radius in meters
	 *                           Radiuses array length should be same as coordinates
	 *                           array, eaach value separated by semi-colon. Input
	 *                           Value - {radius};{radius}[;{radius} ...] Radius has
	 *                           following format :- double &gt;&#x3D; 0 or
	 *                           unlimited (default) (optional)
	 * @param generateHints      Adds a Hint to the response which can be used in
	 *                           subsequent requests, see hints parameter. Input
	 *                           Value - true (default), false Format - Base64
	 *                           String (optional)
	 * @param approaches         Keep waypoints on curb side. Input Value -
	 *                           {approach};{approach}[;{approach} ...] Format -
	 *                           curb or unrestricted (default) (optional)
	 * @param exclude            Additive list of classes to avoid, order does not
	 *                           matter. input Value - {class}[,{class}] Format - A
	 *                           class name determined by the profile or none.
	 *                           (optional)
	 * @param annotations        Returns additional metadata for each coordinate
	 *                           along the route geometry. [ true, false (default),
	 *                           nodes, distance, duration, datasources, weight,
	 *                           speed ] (optional)
	 * @param sources            Use location with given index as source. [
	 *                           {index};{index}[;{index} ...] or all (default) ]
	 *                           &#x3D;&gt; index 0 &lt;&#x3D; integer &lt;
	 *                           #locations (optional)
	 * @param destinations       Use location with given index as destination. [
	 *                           {index};{index}[;{index} ...] or all (default) ]
	 *                           (optional)
	 * @param fallbackSpeed      If no route found between a source/destination
	 *                           pair, calculate the as-the-crow-flies distance,
	 *                           then use this speed to estimate duration. double
	 *                           &gt; 0 (optional)
	 * @param fallbackCoordinate When using a fallback_speed, use the user-supplied
	 *                           coordinate (input), or the snapped location
	 *                           (snapped) for calculating distances. [ input
	 *                           (default), or snapped ] (optional, default to
	 *                           &quot;\&quot;input\&quot;&quot;)
	 * @param _callback          The callback to be executed when the API call
	 *                           finishes
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
	public okhttp3.Call matrixAsync(String coordinates, String bearings, String radiuses, String generateHints,
			String approaches, String exclude, String annotations, Integer sources, Integer destinations,
			BigDecimal fallbackSpeed, String fallbackCoordinate, final ApiCallback<DirectionsMatrix> _callback)
			throws ApiException {

		okhttp3.Call localVarCall = matrixValidateBeforeCall(coordinates, bearings, radiuses, generateHints, approaches,
				exclude, annotations, sources, destinations, fallbackSpeed, fallbackCoordinate, _callback);
		Type localVarReturnType = new TypeToken<DirectionsMatrix>() {
		}.getType();
		localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
		return localVarCall;
	}
}
