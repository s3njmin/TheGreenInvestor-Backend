package G2T6.G2T6.G2T6.StateMachine;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StateClient {
    private RestTemplate template;

    StateClient(RestTemplateBuilder builder) {
        this.template = builder.build();
    }

    public PlayerCurrentState getState(final String URI, final Long id) {
        final PlayerCurrentState currentState = template.getForObject(URI + "/" + id, PlayerCurrentState.class);
        return currentState;
    }


    public PlayerCurrentState addState(final String URI, final PlayerCurrentState currentStates) {
        final PlayerCurrentState currentState = template.postForObject(URI, currentStates, PlayerCurrentState.class);
        return currentState;
    }
}
