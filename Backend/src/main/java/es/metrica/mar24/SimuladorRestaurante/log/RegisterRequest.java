package es.metrica.mar24.SimuladorRestaurante.log;



public class RegisterRequest {

    private String username;
    private String password;
    private String email;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Builder pattern
    public static class RegisterRequestBuilder {
        private String username;
        private String password;
        private String email;

        public RegisterRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        public RegisterRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegisterRequest build() {
            return new RegisterRequest(username, password, email);
        }
    }

    public static RegisterRequestBuilder builder() {
        return new RegisterRequestBuilder();
    }

}
