import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleWebServer {
    public static void main(String[] args) throws Exception {
        // Create an HTTP server listening on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Define a context (endpoint) for the root URL "/"
        server.createContext("/", new MyHandler());

        // Start the server
        server.start();

        System.out.println("Server is running at http://localhost:8080");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            try {
                String response = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "    <title>Simple Static Webpage</title>\n" +
                        "    <style>\n" +
                        "        body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 50px; }\n" +
                        "        h1 { color: #333; }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <h1>Welcome to My Simple Static Webpage!</h1>\n" +
                        "    <p>This is a static webpage served by a simple Java HTTP server.</p>\n" +
                        "</body>\n" +
                        "</html>";

                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes().length);

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
