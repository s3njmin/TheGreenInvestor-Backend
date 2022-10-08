package G2T6.G2T6.G2T6.controllers;

import G2T6.G2T6.G2T6.models.CurrentState;
import G2T6.G2T6.G2T6.services.StateService;
import G2T6.G2T6.G2T6.exceptions.StateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class StateController {
    private StateService stateService;

    @Autowired
    public  StateController(StateService ss){ this.stateService = ss; }

    @GetMapping("/id")
    public List<CurrentState> getAllState(){
        return stateService.listCurrentState();
    }


    @GetMapping("/id/{id}")
    public CurrentState getState(@PathVariable Long id){
        CurrentState state = stateService.getCurrentState(id);
        if(state == null)  throw new StateNotFoundException(id);
        return state;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/id")
    public CurrentState addState(@Valid @RequestBody CurrentState state){
        return stateService.addCurrentState(state);
    }

    @PutMapping("/id/{id}")
    public CurrentState updateState(@PathVariable Long id, @Valid @RequestBody CurrentState state){
        CurrentState currentState = stateService.updateCurrentState(id, state);
        if(currentState == null)  throw new StateNotFoundException(id);
        return currentState;
    }

    @DeleteMapping("/id/{id}")
    public void deleteState(@PathVariable Long id){
        try{
            stateService.deleteCurrentState(id);
        }catch (EmptyResultDataAccessException e){
            throw new StateNotFoundException(id);
        }
    }

}
