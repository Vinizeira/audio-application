package com.vn.repository;

import com.vn.config.AppConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class ITunesSearchRepository {
    private final HttpClient httpClient;

    protected ITunesSearchRepository() {
        this(HttpClient.newBuilder()
                .connectTimeout(AppConfig.API_TIMEOUT)
                .version(AppConfig.HTTP_VERSION)
                .build());
    }

    protected ITunesSearchRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected List<JSONObject> searchResults(String searchTerm, String queryParameters) throws IOException, InterruptedException {
        String encodedSearch = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        String address = AppConfig.ITUNES_BASE_URL.formatted(encodedSearch, queryParameters);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .timeout(AppConfig.API_TIMEOUT)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new ITunesSearchException("Could not connect to iTunes right now.", e);
        }

        if (response.statusCode() != 200) {
            throw new ITunesSearchException("iTunes returned status " + response.statusCode() + ".");
        }

        try {
            JSONObject json = new JSONObject(response.body());
            JSONArray results = json.optJSONArray("results");
            List<JSONObject> limitedResults = new ArrayList<>();
            if (results == null) {
                return limitedResults;
            }

            for (int i = 0; i < Math.min(AppConfig.SEARCH_RESULT_LIMIT, results.length()); i++) {
                JSONObject item = results.optJSONObject(i);
                if (item != null) {
                    limitedResults.add(item);
                }
            }
            return limitedResults;
        } catch (JSONException e) {
            throw new ITunesSearchException("iTunes returned an invalid response.", e);
        }
    }
}
