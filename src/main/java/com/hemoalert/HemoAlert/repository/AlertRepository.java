package com.hemoalert.HemoAlert.repository;

import com.hemoalert.HemoAlert.model.Alert;
import com.hemoalert.HemoAlert.model.BloodType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.UUID;

public class AlertRepository {
    private final Connection connection;

    public AlertRepository(Connection connection) {
        this.connection = connection;
    }

    public UUID saveAlert(Alert alert) {
        String sql = "INSERT INTO alerta (id, center_name, city, blood_type, center_uuid) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, alert.getId());
            preparedStatement.setString(2, alert.getCenterName());
            preparedStatement.setString(3, alert.getCity());
            preparedStatement.setString(4, alert.getBloodType().name());
            preparedStatement.setObject(5, alert.getCenterUuid());
            preparedStatement.executeUpdate();
            return alert.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving alert", e);
        }
    }

    public Optional<Alert> findById(UUID alertId) {
        String sql = "SELECT id, center_name, city, blood_type, center_uuid FROM alerta WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, alertId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("center_name"),
                        resultSet.getString("city"),
                        Enum.valueOf(BloodType.class, resultSet.getString("blood_type")),
                        UUID.fromString(resultSet.getString("center_uuid"))
                );
                return Optional.of(alert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
