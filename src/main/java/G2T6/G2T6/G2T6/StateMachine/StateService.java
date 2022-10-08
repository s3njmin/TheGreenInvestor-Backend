package G2T6.G2T6.G2T6.StateMachine;

import java.util.List;

public interface StateService {
    List<CurrentState> listPlayerCurrentState();
    CurrentState getPlayerCurrentState(Long id);
    CurrentState addPlayerCurrentState(CurrentState state);
    CurrentState updatePlayerCurrentState(Long id, CurrentState state);
    void deletePlayerCurrentState(Long id);
    void factoryReset(Long id);
}
