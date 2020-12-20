package org.example.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: MaiYu
 * @Date: Create in 10:35 2020/12/20
 */
@Data
@Component
public class User {

    private int id;
    private String username;
    private String password;
    private int type;
    String val="eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MTEwMzY0MTEsInN1YiI6bnVsbCwiY3JlYXRlZCI6MTYwODQ0NDQxMTE0Mn0.tNOcZn0KkcEA8JEX1A6xdy1sUWgEx9RlQCrwAscZsEghxdGoc2prMmrCMPJm1yD6KPfeL0kwnzjhwgkaRYWgYQ";
    String cas="eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MTEwMzQ3NTUsInN1YiI6bnVsbCwiY3JlYXRlZCI6MTYwODQ0Mjc1NTgxM30._FQHhJROzE8YTiJU0TtouTcuuT4PCF0zJywKhMDb-_9XChfoCMIB-WZ_WDXDf46DnAdztaRM1iXI4WqFmYLRCA";
}

