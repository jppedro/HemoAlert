package com.hemoalert.HemoAlert.service;

import com.hemoalert.HemoAlert.dto.BloodCenterDTO;
import com.hemoalert.HemoAlert.exception.BloodCenterNotFoundException;
import com.hemoalert.HemoAlert.model.BloodCenter;
import com.hemoalert.HemoAlert.repository.BloodCenterRepository;

import java.util.UUID;

public class BloodCenterService {

    private final BloodCenterRepository bloodCenterRepository;

    public BloodCenterService(BloodCenterRepository bloodCenterRepository) {
        this.bloodCenterRepository = bloodCenterRepository;
    }

    public UUID createBloodCenter(BloodCenterDTO bloodCenterDTO) {
        BloodCenter bloodCenter = new BloodCenter(
                UUID.randomUUID(),
                bloodCenterDTO.getNome(),
                bloodCenterDTO.getRua(),
                bloodCenterDTO.getNumero(),
                bloodCenterDTO.getComplemento(),
                bloodCenterDTO.getBairro(),
                bloodCenterDTO.getCidade(),
                bloodCenterDTO.getEstado(),
                bloodCenterDTO.getCep()
        );
        return bloodCenterRepository.saveBloodCenter(bloodCenter);
    }

    public BloodCenterDTO getBloodCenterById(UUID bloodCenterId) {
        BloodCenter bloodCenter = bloodCenterRepository.findById(bloodCenterId)
                .orElseThrow(() -> new BloodCenterNotFoundException("Blood Center not found with id: " + bloodCenterId));

        return new BloodCenterDTO(
                bloodCenter.getBloodCenterUuid(),
                bloodCenter.getNome(),
                bloodCenter.getRua(),
                bloodCenter.getNumero(),
                bloodCenter.getComplemento(),
                bloodCenter.getBairro(),
                bloodCenter.getCidade(),
                bloodCenter.getEstado(),
                bloodCenter.getCep()
        );
    }
}
