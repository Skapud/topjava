package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validateInstance(user);

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            int id = newKey.intValue();
            user.setId(id);
            insertUserRoles(user);
        } else if (namedParameterJdbcTemplate.update("""
                UPDATE users SET name=:name, email=:email, password=:password,
                registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) != 0) {
            jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", user.id());
            insertUserRoles(user);
        } else {
            return null;
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("""
                SELECT * FROM users u
                LEFT JOIN user_role r ON u.id = r.user_id WHERE u.id=?
                """, getResultSetExtractor(), id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("""
                SELECT * FROM users u
                LEFT JOIN user_role r ON u.id = r.user_id WHERE email=?""", getResultSetExtractor(), email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("""
                SELECT * FROM users u
                LEFT JOIN user_role r ON u.id = r.user_id
                ORDER BY name, email""", getResultSetExtractor());
    }

    private static ResultSetExtractor<List<User>> getResultSetExtractor() {
        return rs -> {
            Map<Integer, User> usersMap = new LinkedHashMap<>();
            int i = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String role = rs.getString("role");
                User user;
                if (usersMap.containsKey(id)) {
                    user = usersMap.get(id);
                } else {
                    user = ROW_MAPPER.mapRow(rs, i++);
                    user.setRoles(new HashSet<>());
                    usersMap.put(id, user);
                }
                if (role != null) user.getRoles().add(Role.valueOf(role));
            }
            return new ArrayList<>(usersMap.values());
        };
    }

    private void insertUserRoles(User user) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        jdbcTemplate.batchUpdate("""
                INSERT INTO user_role (user_id, role) VALUES (?,?)
                """, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, user.id());
                ps.setString(2, String.valueOf(roles.get(i)));
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }
}
