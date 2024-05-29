package es.metrica.mar24.SimuladorRestaurante.log;


public class LoginRequest {


	private String username;
	private String password;


	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static class LoginRequestBuilder {
		private String username;
		private String password;

		public LoginRequestBuilder setUsername(String username) {
			this.username = username;
			return this;
		}

		public LoginRequestBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public LoginRequest build() {
			return new LoginRequest(username, password);
		}
	}

	public static LoginRequestBuilder builder() {
		return new LoginRequestBuilder();
	}

	



}
