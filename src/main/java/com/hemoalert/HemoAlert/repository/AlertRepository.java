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
        String sql = "INSERT INTO alerta (id, ddd, tipo_sanguineo, hemocentro_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, alert.getId());
            preparedStatement.setString(2, alert.getCity());
            preparedStatement.setString(3, alert.getBloodType().getDisplayName());
            preparedStatement.setObject(4, alert.getCenterUuid());
            preparedStatement.executeUpdate();
            return alert.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving alert", e);
        }
    }

    public Optional<Alert> findById(UUID alertId) {
        String sql = "SELECT id, ddd, tipo_sanguineo, hemocentro_id FROM alerta WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, alertId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("ddd"),
                        BloodType.fromDisplayName(resultSet.getString("tipo_sanguineo").trim()),
                        UUID.fromString(resultSet.getString("hemocentro_id"))
                );
                return Optional.of(alert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
