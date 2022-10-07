package G2T6.G2T6.G2T6.StateMachine;

import G2T6.G2T6.G2T6.stats.GameStats;

import java.util.List;

public interface StateService {
    List<PlayerCurrentState> listPlayerCurrentState();
    PlayerCurrentState getPlayerCurrentState(Long id);
    PlayerCurrentState addPlayerCurrentState(PlayerCurrentState state);
    PlayerCurrentState updatePlayerCurrentState(Long id, PlayerCurrentState state);
    void deletePlayerCurrentState(Long id);
    void factoryReset(Long id);
}
