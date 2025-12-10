package Classes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.io.File;
import java.nio.file.Path;
public class RemoveBGAPI {
    private static final String API_URL = "https://api.withoutbg.com/v1.0/image-without-background";
    private static final String API_KEY = "sk-65e225571327dc248539dd095877002a3081de78c26f0cad058b547eb5757d91";
    // create an account at withoutbg.com for the api key..
    public static void removeBackground(String inputImagePath, String outputImagePath) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            byte[] imageBytes = Files.readAllBytes(Paths.get(inputImagePath));

            // Generate boundary
            String boundary = "Boundary-" + UUID.randomUUID();
            String LINE_FEED = "\r\n";

            // Multipart header
            String header = "--" + boundary + LINE_FEED +
                    "Content-Disposition: form-data; name=\"file\"; filename=\"" + Paths.get(inputImagePath).getFileName() + "\"" + LINE_FEED +
                    "Content-Type: application/octet-stream" + LINE_FEED + LINE_FEED;

            // Multipart footer
            String footer = LINE_FEED + "--" + boundary + "--" + LINE_FEED;

            // Combine header + file + footer
            byte[] headerBytes = header.getBytes();
            byte[] footerBytes = footer.getBytes();
            byte[] requestBody = new byte[headerBytes.length + imageBytes.length + footerBytes.length];

            System.arraycopy(headerBytes, 0, requestBody, 0, headerBytes.length);
            System.arraycopy(imageBytes, 0, requestBody, headerBytes.length, imageBytes.length);
            System.arraycopy(footerBytes, 0, requestBody, headerBytes.length + imageBytes.length, footerBytes.length);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("X-API-Key", API_KEY)
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() != 200) {
                System.out.println("Error: " + new String(response.body()));
                return;
            }

            // Save the returned PNG
            Files.write(Paths.get(outputImagePath), response.body());
            System.out.println("Background removed successfully: " + outputImagePath);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    // Returns a File object containing the processed image, or null if failed
    public static File removeBackground(File inputFile) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Read bytes directly from the File object
            byte[] imageBytes = Files.readAllBytes(inputFile.toPath());

            // Generate boundary
            String boundary = "Boundary-" + UUID.randomUUID();
            String LINE_FEED = "\r\n";

            // Multipart header
            String header = "--" + boundary + LINE_FEED +
                    "Content-Disposition: form-data; name=\"file\"; filename=\"" + inputFile.getName() + "\"" + LINE_FEED +
                    "Content-Type: application/octet-stream" + LINE_FEED + LINE_FEED;

            // Multipart footer
            String footer = LINE_FEED + "--" + boundary + "--" + LINE_FEED;

            // Combine header + file + footer
            byte[] headerBytes = header.getBytes();
            byte[] footerBytes = footer.getBytes();
            byte[] requestBody = new byte[headerBytes.length + imageBytes.length + footerBytes.length];

            System.arraycopy(headerBytes, 0, requestBody, 0, headerBytes.length);
            System.arraycopy(imageBytes, 0, requestBody, headerBytes.length, imageBytes.length);
            System.arraycopy(footerBytes, 0, requestBody, headerBytes.length + imageBytes.length, footerBytes.length);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("X-API-Key", API_KEY)
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() != 200) {
                System.out.println("Error: " + new String(response.body()));
                return null;
            }

            // Create a temporary file to store the result
            // This creates a file like "processed_12345.png" in your system's temp folder
            Path tempFile = Files.createTempFile("processed_", ".png");
            Files.write(tempFile, response.body());

            // Return the File object pointing to this temp file
            return tempFile.toFile();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
