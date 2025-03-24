package net.axel.sharehope.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.action.ActionCreateDTO;
import net.axel.sharehope.domain.dtos.action.ActionStatusDTO;
import net.axel.sharehope.domain.dtos.action.ActionUpdateDTO;
import net.axel.sharehope.domain.dtos.action.need.NeedResponseDTO;
import net.axel.sharehope.service.NeedService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(NeedController.CONTROLLER_PATH)

@RequiredArgsConstructor
public class NeedController {

    public final static String CONTROLLER_PATH = "api/v1/needs";

    private final NeedService service;

    @PostMapping
    public ResponseEntity<NeedResponseDTO> create(@Valid @ModelAttribute ActionCreateDTO requestDTO) {
        NeedResponseDTO need = service.createNeed(requestDTO);
        return new ResponseEntity<>(need, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NeedResponseDTO> update(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute ActionUpdateDTO dto
    ) {
        NeedResponseDTO updatedNeed = service.update(id, dto);
        return ResponseEntity.ok(updatedNeed);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<NeedResponseDTO> updateStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody ActionStatusDTO statusDTO
    ) {
        NeedResponseDTO updatedDonation = service.updateStatus(id, statusDTO);
        return ResponseEntity.ok(updatedDonation);
    }


    @GetMapping("/{id}")
    public ResponseEntity<NeedResponseDTO> findById(@PathVariable("id") Long id) {
        NeedResponseDTO need = service.findById(id);
        return new ResponseEntity<>(need, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<NeedResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<NeedResponseDTO> needs = service.findAll(page, size);
        return ResponseEntity.ok(needs);
    }

    @GetMapping("/myNeeds")
    public ResponseEntity<List<NeedResponseDTO>> findAllMyDonations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<NeedResponseDTO> needs = service.findAllMyNeed(page, size);
        return ResponseEntity.ok(needs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
