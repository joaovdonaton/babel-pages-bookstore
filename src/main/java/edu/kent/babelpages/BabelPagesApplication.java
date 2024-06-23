package edu.kent.babelpages;

import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

class TestObject{
    private int id;
    private String name;

    @Override
    public String toString() {
        return "TestObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

class TestObjectMapper implements RowMapper<TestObject>{
    @Override
    public TestObject mapRow(ResultSet rs, int rowNum) throws SQLException {
        TestObject to = new TestObject();

        to.setId(rs.getInt("id"));
        to.setName(rs.getString("name"));

        return to;
    }
}

@SpringBootApplication
public class BabelPagesApplication {
    private final JdbcTemplate jdbcTemplate;

    BabelPagesApplication(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // test jdbc connection
    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        System.out.println("Inerting user into test table...");
        jdbcTemplate.update("INSERT INTO test (name) VALUES (?)", "Richard");

        System.out.println("Retrieving values from test table...");
        List<TestObject> res = jdbcTemplate.query("SELECT * FROM test", new TestObjectMapper());
        res.forEach(System.out::println);
    }

    public static void main(String[] args) {
        SpringApplication.run(BabelPagesApplication.class, args);
    }
}
