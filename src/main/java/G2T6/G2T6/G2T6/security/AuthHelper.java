package G2T6.G2T6.G2T6.security;

import org.springframework.security.core.context.SecurityContextHolder;

import G2T6.G2T6.G2T6.models.security.User;

public class AuthHelper {

    public static User getUserDetails() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // System.out.println(principal);

        if (!(principal instanceof User)) {
            // System.out.println("did i run here?");
            return null;
        }

        return (User) principal;

    }


}