package com.example.Blogs.Resolvers;


import com.example.Blogs.CustomResponses.LoginResponse;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AuthResolver {


    @MutationMapping
    private LoginResponse login(@Argument String email, @Argument String password) {
        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && //---------> waiting to implement Jwt Filter
            !authentication instanceof AnonymousAuthenticationToken) {

            //  SUCCESS: User was authenticated by your filter
            // Generate JWT token for future requests
            String token = jwtTokenProvider.generateToken(authentication);  ------->Better be stored in Global Objet that hold
                                                                                    User Data , His Time zone , IP , Token and ID

            return new LoginResponse(token, true, "Login successful");

        } else {
            //  FAILURE: Filter authentication failed
            return new LoginResponse(null, false, "Invalid credentials"); -----> GraphQLExceptionResolver(USER_NOT_FOUND Exception)
        }
         }
         */
        return null;
    }
}
