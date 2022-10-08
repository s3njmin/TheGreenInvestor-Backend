package G2T6.G2T6.G2T6;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import G2T6.G2T6.G2T6.StateMachine.*;
import G2T6.G2T6.G2T6.repository.StateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StateServiceTest {
    @Mock
    private StateRepository stateRepo;

    @InjectMocks
    private StateServiceImplementation stateServiceImplementation;

    // @Test
    // void addState_ReturnSavedSameState(){
    //     CurrentState state = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE);
    //     when(stateRepo.findByCurrentState(any(String.class))).thenReturn(new ArrayList<CurrentState>());
    //     when(stateRepo.save(any(CurrentState.class))).thenReturn(state);

    //     CurrentState savedState = stateServiceImplementation.addCurrentState(state);

    //     assertNotNull(savedState);
    //     verify(stateRepo).findById(state.getId());
    //     verify(stateRepo).save(state);
    // }
    @Test
    void updateState_NotFound_ReturnNull(){
        CurrentState state = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE);
        Long id = 10L;
        when(stateRepo.findById(id)).thenReturn(Optional.empty());

        CurrentState updateState = stateServiceImplementation.updateCurrentState(id, state);

        assertNull(updateState);
        verify(stateRepo).findById(id);
    }

}
