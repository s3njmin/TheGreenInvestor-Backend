package G2T6.G2T6.G2T6.services;


import G2T6.G2T6.G2T6.misc.CONSTANTVARIABLES;
import G2T6.G2T6.G2T6.misc.State;
import G2T6.G2T6.G2T6.models.CurrentState;
import G2T6.G2T6.G2T6.repository.StateRepository;
import G2T6.G2T6.G2T6.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImplementation implements StateService {
    private StateRepository stateRepository;

    @Autowired
    public StateServiceImplementation(StateRepository rs){
        this.stateRepository = rs;
    }

    @Override
    public List<CurrentState> listCurrentState() {
        return stateRepository.findAll();
    }

    @Override
    public CurrentState getCurrentState(Long id) {
        return stateRepository.findById(id).orElse(null);
    }

    @Override
    public CurrentState addCurrentState(CurrentState state) {
        return stateRepository.save(state);
    }

    @Override
    public CurrentState updateCurrentState(Long id, CurrentState state) {
        return stateRepository.findById(id).map(newState -> {
                newState.changeState(state.getCurrentState());
                newState.setYearValue(state.getYearValue());
            return stateRepository.save(newState );
        }).orElse(null);
    }

    @Override
    public void deleteCurrentState(Long id) {
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

    @Override
    public CurrentState getDefaultState() {
        //init default state
        CurrentState newState = new CurrentState();
        newState.changeState(CONSTANTVARIABLES.DEFAULTSTATE);
        newState.setYearValue(CONSTANTVARIABLES.DEFAULTYEAR);
        return newState;
    }

    @Override
    public List<CurrentState> listCurrentStateByUserId(Long userid) {
        return stateRepository.findByUserId(userid);
    }

    @Override
    public Optional<CurrentState> getStateByIdAndUserId(Long id, Long userId) {
        return stateRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public List<CurrentState> listCompletedState() {
        List<CurrentState> allStats = listCurrentState();
        List<CurrentState> completedState = new ArrayList<>();
        for(CurrentState c: allStats){
            if(c.getCurrentState() == State.completed){
                completedState.add(c);
            }
        }
        return completedState;
    }

}
