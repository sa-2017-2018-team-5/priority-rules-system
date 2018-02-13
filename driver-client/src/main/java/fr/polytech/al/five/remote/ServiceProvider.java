package fr.polytech.al.five.remote;

public class ServiceProvider {

    private ServiceProvider() {
        // No ServiceProvider object!
    }

    private static String getEnvironment(String variable, String defaultValue) {
        String content = System.getenv(variable);
        if (content == null) {
            content = defaultValue;
        }

        return content;
    }

    private static String getHost() {
        return getEnvironment("BACKEND_HOST", "localhost");
    }

    private static String getPort() {
        return getEnvironment("BACKEND_PORT", "8080");
    }

    public static String getURL() {
        return "http://" + getHost() + ":" + getPort() + "/prs-routes/routes";
    }

}
