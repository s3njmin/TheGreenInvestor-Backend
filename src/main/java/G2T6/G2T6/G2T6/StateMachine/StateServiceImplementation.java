package G2T6.G2T6.G2T6.StateMachine;


import G2T6.G2T6.G2T6.CONSTANTVARIABLES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StateServiceImplementation implements StateService {
    private StateRepository stateRepository;

    @Autowired
    public StateServiceImplementation(StateRepository rs){
        this.stateRepository = rs;
    }

    @Override
    public List<PlayerCurrentState> listPlayerCurrentState() {
        return stateRepository.findAll();
    }

    @Override
    public PlayerCurrentState getPlayerCurrentState(Long id) {
        return stateRepository.findById(id).orElse(null);
    }

    @Override
    public PlayerCurrentState addPlayerCurrentState(PlayerCurrentState state) {
        return stateRepository.save(state);
    }

    @Override
    public PlayerCurrentState updatePlayerCurrentState(Long id, PlayerCurrentState state) {
        return stateRepository.findById(id).map(newState -> {
                newState.changeState(state.getCurrentState());
                newState.setYearValue(state.getYearValue());
            return stateRepository.save(newState );
        }).orElse(null);
    }

    @Override
    public void deletePlayerCurrentState(Long id) {
        stateRepository.deleteById(id);
    }

    @Override
    public void factoryReset(Long id) {
        stateRepository.findById(id).map(newState -> {
            newState.changeState(CONSTANTVARIABLES.DEFAULTSTATE);
            newState.setYearValue(CONSTANTVARIABLES.DEFAULTYEAR);
            return stateRepository.save(newState);
        });
    }
}
