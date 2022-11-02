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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "user_id")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Min(0) @Max(10) @NotNull
    private int yearValue;

    @Enumerated(EnumType.STRING) @NotNull
    private State currentState;

    @NotNull
    private int questionSetId; // somewhere randomise and add in

    private String userResponse = ""; // gonna be something like this 1,2,4,2 use split // everytime user update

    @OneToOne(mappedBy = "currentState", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private GameStats gameStats;

    public CurrentState(Long id, User user, int year, State state, int questionSetId, String userResponse) {
        this.id = id;
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

    public CurrentState(int year, State state) {
        this.yearValue = year;
        this.currentState = state;
    }

    public void changeState(State state){
        this.currentState = state;
    }

    public List<String> getUserAnswers(){
        if (userResponse.isEmpty()) return null;
        List<String> answers = Arrays.stream(userResponse.split(",")).toList();
        return answers;
    }

    public String addNewUserResponse(Integer currentAns){
        if(!userResponse.isEmpty()) {
            userResponse += ",";
        }
        userResponse += currentAns;
        return userResponse;
    }

    public String addNewUserResponse(String currentAns){
        if(!userResponse.isEmpty()) {
            userResponse += ",";
        }
        userResponse += currentAns;
        return userResponse;
    }
}
