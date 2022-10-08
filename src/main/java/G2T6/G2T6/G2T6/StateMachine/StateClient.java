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

    public CurrentState getState(final String URI, final Long id) {
        final CurrentState currentState = template.getForObject(URI + "/" + id, CurrentState.class);
        return currentState;
    }


    public CurrentState addState(final String URI, final CurrentState currentStates) {
        final CurrentState currentState = template.postForObject(URI, currentStates, CurrentState.class);
        return currentState;
    }
}
