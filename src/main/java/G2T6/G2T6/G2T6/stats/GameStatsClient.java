package G2T6.G2T6.G2T6.stats;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class GameStatsClient {
    private RestTemplate template;

    GameStatsClient(RestTemplateBuilder builder) {
        this.template = builder.build();
    }

    public GameStats getBook(final String URI, final Long id) {
        final GameStats gameStats = template.getForObject(URI + "/" + id, GameStats.class);
        return gameStats;
    }


    public GameStats addBook(final String URI, final GameStats gameStats) {
        final GameStats returned = template.postForObject(URI, gameStats, GameStats.class);
        return returned;
    }
}
