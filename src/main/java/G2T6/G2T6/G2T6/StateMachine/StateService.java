package G2T6.G2T6.G2T6.StateMachine;

import java.util.List;

public interface StateService {
    List<CurrentState> listCurrentState();
    CurrentState getCurrentState(Long id);
    CurrentState addCurrentState(CurrentState state);
    CurrentState updateCurrentState(Long id, CurrentState state);
    void deleteCurrentState(Long id);
    void factoryReset(Long id);
}
