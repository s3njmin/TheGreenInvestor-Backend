package G2T6.G2T6.G2T6.models;

import G2T6.G2T6.G2T6.misc.State;
import G2T6.G2T6.G2T6.models.security.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "currentstate")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CurrentState {
    // id of CurrentState class and primary key of currentstate table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // this Object belong to which user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // year value, can be treated the same as question number
    @Min(0) @Max(10) @NotNull
    private int yearValue;

    // the current state of user to help user get back into game after closing broswer
    @Enumerated(EnumType.STRING) @NotNull
    private State currentState;

    // the question set user playing
    @NotNull
    private int questionSetId; // somewhere randomise and add in

    // the user's response
    // it will be a string and split into different questions answer
    private String userResponse = "";

    // the game stats that this state is connected to
    @OneToOne(mappedBy = "currentState", cascade = CascadeType.ALL)
    @JsonIgnore
    private GameStats gameStats;

    public CurrentState(User user, int year, State state, int questionSetId, String userResponse) {
        this.user = user;
        this.yearValue = year;
        this.currentState = state;
        this.questionSetId = questionSetId;
        this.userResponse = userResponse;
    }
    public CurrentState(long id, int year, State state) {
        this.id = id;
        this.yearValue = year;
        this.currentState = state;
    }

    public CurrentState(int year, State state, User user) {
        this.yearValue = year;
        this.currentState = state;
        this.user = user;
    }

    public CurrentState(int year, State state) {
        this.yearValue = year;
        this.currentState = state;
    }

    // change state
    public void changeState(State state){
        this.currentState = state;
    }

    /**
     * Split the answer to individual question
     * @return list of string answer
     */
    public List<String> getUserAnswers(){
        if (userResponse.isEmpty()) return null;
        List<String> answers = Arrays.stream(userResponse.split(",")).toList();
        return answers;
    }

    /**
     *
     * @param currentAns a Integer Value
     * @return new string after adding current answer
     */
    public String addNewUserResponse(Integer currentAns){
        if(!userResponse.isEmpty()) {
            userResponse += ",";
        }
        userResponse += currentAns;
        return userResponse;
    }

    /**
     *
     * @param currentAns a String Value
     * @return new string after adding current answer
     */
    public String addNewUserResponse(String currentAns){
        if(!userResponse.isEmpty()) {
            userResponse += ",";
        }
        userResponse += currentAns;
        return userResponse;
    }
}
