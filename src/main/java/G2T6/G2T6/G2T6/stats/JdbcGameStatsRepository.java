package G2T6.G2T6.G2T6.stats;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class JdbcGameStatsRepository implements GameStatsRepository{

    private JdbcTemplate jdbcTemplate;

    public JdbcGameStatsRepository(JdbcTemplate template){
        this.jdbcTemplate = template;
    }

    @Override
    public List<GameStats> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM GameStats",
                (rs, rowNum) -> new GameStats(rs.getLong("id"), populateStatsList(rs))
        );
    }
    //new "Emission"(rs.getInt("Emission"));

    @Override
    public Optional<GameStats> findById(Long id) {
        try{
            return jdbcTemplate.queryForObject("select * from GameStats where id = ?",
                    (rs, rowNum) -> Optional.of(new GameStats(rs.getLong("id"), populateStatsList(rs))),
                    new Object[]{id});

        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public ArrayList<Stats> populateStatsList(ResultSet rs) throws SQLException {
        ArrayList<Stats> s =  new ArrayList<Stats>();
        // will change to looping using column name
        s.add(new Emission(rs.getInt("Emission")));
        s.add(new Economic(rs.getInt("Economic")));
        s.add(new Happiness(rs.getInt("Happiness")));

        return s;
    }


    @Override
    public Long save(GameStats gameStats) {
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for(Stats s: gameStats.getGameStats()){
            columns.add(s.getStatsName());
            values.add("" + s.getStatsValue());
        }

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement statement = conn.prepareStatement(String.format("insert into books (%s) values (%s)", String.join(",", columns), String.join(",", values)), Statement.RETURN_GENERATED_KEYS);
                return statement;
            }}, holder);

        Long primaryKey = holder.getKey().longValue();
        return primaryKey;
    }

    @Override
    public int Update(GameStats gameStats) {
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for(Stats s: gameStats.getGameStats()){
            columns.add(s.getStatsName());
            values.add("" + s.getStatsValue());
        }

        return jdbcTemplate.update("update GameStats set (?) values(?)", String.join(",", columns), String.join(",", values));
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("delete GameStats where id = ?", id);
    }
}
