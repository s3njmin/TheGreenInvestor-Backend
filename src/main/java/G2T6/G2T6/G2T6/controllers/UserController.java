package G2T6.G2T6.G2T6.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import G2T6.G2T6.G2T6.payload.response.MessageResponse;
import G2T6.G2T6.G2T6.repository.UserRepository;
import G2T6.G2T6.G2T6.models.security.User;
import G2T6.G2T6.G2T6.security.AuthHelper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    // Get all users subscribed to email notifications
    @GetMapping("/subscribedlist")
    public ResponseEntity<?> getSubscribedUserEmailList() {

        try {

            List<String> subscribedUserEmailList = userRepository.findAll().stream()
                    .filter(user -> user.isSubscribedEmail()).map(user -> user.getEmail()).collect(Collectors.toList());

            return new ResponseEntity<>(subscribedUserEmailList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    // change user subscription status
    @PutMapping("/subscribe")
    public ResponseEntity<?> changeUserSubscriptionStatus() {

        try {

            UserDetails currUser = AuthHelper.getCurrentUser();

            if (currUser == null) {
                return new ResponseEntity<>(new MessageResponse("Error: User not found"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            System.out.println(currUser.getUsername());

            User user = userRepository.findByUsername(currUser.getUsername()).get();

            user.setSubscribedEmail(!user.isSubscribedEmail());

            userRepository.save(user);

            return new ResponseEntity<>(new MessageResponse("User subscription status changed successfully!"),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}