package com.hemoalert.HemoAlert.repository;

import com.hemoalert.HemoAlert.model.BloodCenter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.UUID;

public class BloodCenterRepository {
    private final Connection connection;

    public BloodCenterRepository(Connection connection) {
        this.connection = connection;
    }

    public UUID saveBloodCenter(BloodCenter bloodCenter) {
        String sql = "INSERT INTO hemocentro (id, nome, rua, numero, complemento, bairro, cidade, estado, cep) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, bloodCenter.getBloodCenterUuid());
            preparedStatement.setString(2, bloodCenter.getNome());
            preparedStatement.setString(3, bloodCenter.getRua());
            preparedStatement.setString(4, bloodCenter.getNumero());
            preparedStatement.setString(5, bloodCenter.getComplemento());
            preparedStatement.setString(6, bloodCenter.getBairro());
            preparedStatement.setString(7, bloodCenter.getCidade());
            preparedStatement.setString(8, bloodCenter.getEstado());
            preparedStatement.setString(9, bloodCenter.getCep());
            preparedStatement.executeUpdate();
            return bloodCenter.getBloodCenterUuid();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving blood center", e);
        }
    }

    public Optional<BloodCenter> findById(UUID bloodCenterId) {
        String sql = "SELECT id, nome, rua, numero, complemento, bairro, cidade, estado, cep " +
                "FROM hemocentro WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, bloodCenterId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                BloodCenter bloodCenter = new BloodCenter(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getString("nome"),
                        resultSet.getString("rua"),
                        resultSet.getString("numero"),
                        resultSet.getString("complemento"),
                        resultSet.getString("bairro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("estado"),
                        resultSet.getString("cep")
                );
                return Optional.of(bloodCenter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
