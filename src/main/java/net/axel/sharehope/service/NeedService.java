package net.axel.sharehope.service;

import net.axel.sharehope.domain.dtos.action.ActionCreateDTO;
import net.axel.sharehope.domain.dtos.action.ActionStatusDTO;
import net.axel.sharehope.domain.dtos.action.ActionUpdateDTO;
import net.axel.sharehope.domain.dtos.action.need.NeedResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NeedService {

    NeedResponseDTO createNeed(ActionCreateDTO dto);

    NeedResponseDTO findById(Long id);

    Page<NeedResponseDTO> findAll(int page, int size);

    List<NeedResponseDTO> findAllMyNeed(int page, int size);

    NeedResponseDTO update(Long id, ActionUpdateDTO dto);

    NeedResponseDTO updateStatus(Long id, ActionStatusDTO statusDTO);

    void delete(Long id);
}
