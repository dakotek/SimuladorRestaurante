package es.metrica.mar24.SimuladorRestaurante.log;


public class LoginRequest {


	private String email;
	private String password;


	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static class LoginRequestBuilder {
		private String email;
		private String password;

		public LoginRequestBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public LoginRequestBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public LoginRequest build() {
			return new LoginRequest(email, password);
		}
	}

	public static LoginRequestBuilder builder() {
		return new LoginRequestBuilder();
	}

	



}
