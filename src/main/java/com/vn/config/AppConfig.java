package com.vn.config;

import java.net.http.HttpClient;
import java.nio.file.Path;
import java.time.Duration;

public final class AppConfig {
    public static final Path HISTORY_FILE = Path.of("data", "History.json");
    public static final String ITUNES_BASE_URL = "https://itunes.apple.com/search?term=%s&%s";
    public static final int SEARCH_RESULT_LIMIT = 5;
    public static final int FAVORITE_MIN_RATING = 3;
    public static final Duration API_TIMEOUT = Duration.ofSeconds(10);
    public static final HttpClient.Version HTTP_VERSION = HttpClient.Version.HTTP_2;

    private AppConfig() {
    }
}
