package nutrition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HealthRecordDAO {
    public boolean addOrUpdateHealthRecord(HealthRecord record) {
        String sqlSelect = "SELECT * FROM health_records WHERE user_id = ?";
        String sqlInsert = "INSERT INTO health_records (user_id, height, weight, age, created_at) VALUES (?, ?, ?, ?, NOW())";
        String sqlUpdate = "UPDATE health_records SET height = ?, weight = ?, age = ? WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(sqlSelect)) {
            selectStatement.setInt(1, record.getUserId());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                try (PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate)) {
                    updateStatement.setFloat(1, record.getHeight());
                    updateStatement.setFloat(2, record.getWeight());
                    updateStatement.setInt(3, record.getAge());
                    updateStatement.setInt(4, record.getUserId());
                    return updateStatement.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement insertStatement = connection.prepareStatement(sqlInsert)) {
                    insertStatement.setInt(1, record.getUserId());
                    insertStatement.setFloat(2, record.getHeight());
                    insertStatement.setFloat(3, record.getWeight());
                    insertStatement.setInt(4, record.getAge());
                    return insertStatement.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HealthRecord getHealthRecord(int userId) {
        String sql = "SELECT * FROM health_records WHERE user_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                HealthRecord record = new HealthRecord();
                record.setUserId(resultSet.getInt("user_id"));
                record.setHeight(resultSet.getFloat("height"));
                record.setWeight(resultSet.getFloat("weight"));
                record.setAge(resultSet.getInt("age"));
                return record;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
