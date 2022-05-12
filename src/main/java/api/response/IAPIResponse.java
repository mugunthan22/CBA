package api.response;

import io.restassured.response.Response;

public interface IAPIResponse<T>{
		public T getBody();
		
		public String getContent();
		
		public int getStatusCode();
		
		public boolean isSuccessful();
		
		public String getStatusDescription();
		
		public Response getResponse();
		
		public Exception getException();
	}