package G2T6.G2T6.G2T6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StateNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public StateNotFoundException(Long id) {
        super("Could not find game stats " + id);
    }

}

