package es.metrica.mar24.SimuladorRestaurante.controller.AuthControllers;


public class AuthResponse {
    private String token;

    public AuthResponse() {
    }
   
    public AuthResponse(String token) {
        this.token = token;
    }

    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class AuthResponseBuilder {
        private String token;

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(token);
        }
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

}
