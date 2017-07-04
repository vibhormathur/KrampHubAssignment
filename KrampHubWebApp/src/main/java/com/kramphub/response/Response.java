/**
 * Response that contains status code and response data.
 */

package com.kramphub.response;

public class Response {
	int status;
	ResponseData data;

	public 	int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ResponseData getData() {
		return data;
	}
	public void setData(ResponseData data) {
		this.data = data;
	}
}
