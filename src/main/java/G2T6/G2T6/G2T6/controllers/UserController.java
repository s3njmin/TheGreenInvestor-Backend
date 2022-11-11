package G2T6.G2T6.G2T6.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G2T6.G2T6.G2T6.payload.response.ProfileResponse;
import G2T6.G2T6.G2T6.repository.UserRepository;
import G2T6.G2T6.G2T6.exceptions.UserNotFoundException;
import G2T6.G2T6.G2T6.models.security.User;
import G2T6.G2T6.G2T6.security.AuthHelper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    /**
     * Get the user profile image index
     * 
     * @return the user profile image index
     */
    @GetMapping("/profileImageIndex")
    public ResponseEntity<?> getProfileImageIndex() {
        UserDetails userDetails = AuthHelper.getUserDetails();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException(userDetails.getUsername()));
        return new ResponseEntity<>(user.getProfileImageIndex(), HttpStatus.OK);
    }

    /**
     * Update the user profile image index
     * @param id
     * @return
     */
    @PutMapping("/profileImageIndex/{id}")
    public ResponseEntity<?> setProfileImageIndex(@PathVariable(value = "id") int id) {
        UserDetails userDetails = AuthHelper.getUserDetails();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException(userDetails.getUsername()));
        user.setProfileImageIndex(id);
        userRepository.save(user);
        return new ResponseEntity<>(user.getProfileImageIndex(), HttpStatus.OK);
    }

    /**
     * Get the user profile
     * 
     * @return the user profile with highscore, gamesplayed and profileImageIndex
     */
    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile() {
        UserDetails userDetails = AuthHelper.getUserDetails();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException(userDetails.getUsername()));

        // return profileresponse dto
        return new ResponseEntity<>(
                new ProfileResponse(user.getHighScore(), user.getGamesPlayed(), user.getProfileImageIndex()),
                HttpStatus.OK);
    }

}