package G2T6.G2T6.G2T6.models;

import G2T6.G2T6.G2T6.misc.CONSTANTVARIABLES;
import G2T6.G2T6.G2T6.misc.State;
import G2T6.G2T6.G2T6.models.orders.OptionOrder;
import G2T6.G2T6.G2T6.models.orders.QuestionOrder;
import G2T6.G2T6.G2T6.models.security.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "currentstate")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class CurrentState {
    // id of CurrentState class and primary key of currentstate table
    @Id
    @Column(name = "current_state_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Game id to identify which game this state belongs to
    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "dateTime")
    // @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dateTime;

    // this Object belong to which user=
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // year value, can be treated the same as question number
    @Min(0)
    @Max(10)
    @NotNull
    private int yearValue;

    // the current state of user to help user get back into game after closing
    // broswer
    @Enumerated(EnumType.STRING)
    @NotNull
    private State currentState;

    // // the question set user playing
    // @NotNull
    // private int questionSetId; // somewhere randomise and add in
    //Shared primary key
    // @OneToOne(mappedBy = "currentState", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // @PrimaryKeyJoinColumn
    // private QuestionSet questionSet;

    //question order
    @ManyToOne
    @JoinColumn(name = "question_order_id")
    private QuestionOrder questionOrder;

    // the user's response
    // it will be a string and split into different questions answer
    private String userResponse = "";

    // the game stats that this state is connected to
    @OneToOne(mappedBy = "currentState", cascade = CascadeType.ALL)
    @JsonIgnore
    private GameStats gameStats;

    // public CurrentState(User user, int year, State state, int questionSetId, String userResponse) {
    //     this.user = user;
    //     this.yearValue = year;
    //     this.currentState = state;
    //     this.questionSetId = questionSetId;
    //     this.userResponse = userResponse;
    // }
    
    private java.util.Date parseTimestamp(String timestamp) {
        try {
            return new Timestamp(CONSTANTVARIABLES.DATE_TIME_FORMAT.parse(timestamp).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public CurrentState(Long gameid, User user, int year, State state, QuestionOrder questionOrder) {
        this(year, state);
        this.user = user;
        this.gameId = gameid;
        this.questionOrder = questionOrder;
    }

    public CurrentState(long id, int year, State state) {
        this(year, state);
        this.id = id;
    }

    public CurrentState(int year, State state, User user) {
        this.yearValue = year;
        this.currentState = state;
        this.user = user;
    }

    public CurrentState(int year, State state) {
        this();
        this.yearValue = year;
        this.currentState = state;

    }

    public CurrentState() {
        String dateTime = CONSTANTVARIABLES.DATE_TIME_FORMAT.format(new java.util.Date());
        this.dateTime = parseTimestamp(dateTime);
    }

    // change state
    public void changeState(State state) {
        this.currentState = state;
    }

    public void refreshTime() {
        String dateTime = CONSTANTVARIABLES.DATE_TIME_FORMAT.format(new java.util.Date());
        this.dateTime = parseTimestamp(dateTime);
    }

    /**
     * Split the answer to individual question
     * 
     * @return list of string answer
     */
    public List<String> getUserAnswers() {
        if (userResponse.isEmpty())
            return null;
        List<String> answers = Arrays.stream(userResponse.split(",")).toList();
        return answers;
    }

    /**
     *
     * @param currentAns a Integer Value
     * @return new string after adding current answer
     */
    public String addNewUserResponse(Integer currentAns) {
        if (!userResponse.isEmpty()) {
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
    public String addNewUserResponse(String currentAns) {
        if (!userResponse.isEmpty()) {
            userResponse += ",";
        }
        userResponse += currentAns;
        return userResponse;
    }
}
