package G2T6.G2T6.G2T6.StateMachine;

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
    public List<PlayerCurrentState> getAllState(){
        return stateService.listPlayerCurrentState();
    }


    @GetMapping("/id/{id}")
    public PlayerCurrentState getState(@PathVariable Long id){
        PlayerCurrentState state = stateService.getPlayerCurrentState(id);
        if(state == null)  throw new StateNotFoundException(id);
        return state;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/id")
    public PlayerCurrentState addState(@Valid @RequestBody PlayerCurrentState state){
        return stateService.addPlayerCurrentState(state);
    }

    @PutMapping("/id/{id}")
    public PlayerCurrentState updateState(@PathVariable Long id, @Valid @RequestBody PlayerCurrentState state){
        PlayerCurrentState currentState = stateService.updatePlayerCurrentState(id, state);
        if(currentState == null)  throw new StateNotFoundException(id);
        return currentState;
    }

    @DeleteMapping("/id/{id}")
    public void deleteState(@PathVariable Long id){
        try{
            stateService.deletePlayerCurrentState(id);
        }catch (EmptyResultDataAccessException e){
            throw new StateNotFoundException(id);
        }
    }

}
