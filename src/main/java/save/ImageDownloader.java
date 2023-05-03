package save;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageDownloader {

public static void downloadImage(String imageUrl, String destinationFile) throws Exception {
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        HttpGet httpGet = new HttpGet(imageUrl);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try (InputStream inputStream = entity.getContent();
                     OutputStream outputStream = new FileOutputStream(new File(destinationFile))) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }
}
}