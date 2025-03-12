package edu.bbte.idde.krim2244.controller;

import edu.bbte.idde.krim2244.dataaccess.model.Extra;
import edu.bbte.idde.krim2244.dto.extra.ExtraDetailsDTO;
import edu.bbte.idde.krim2244.dto.extra.ExtraInDTO;
import edu.bbte.idde.krim2244.mapper.ExtraMapper;
import edu.bbte.idde.krim2244.service.ExtraService;
import edu.bbte.idde.krim2244.service.exception.ServiceException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/extras")
@Profile("jpa")
public class ExtraController {
    @Autowired
    private final ExtraService extraService;

    @Autowired
    private final ExtraMapper extraMapper;

    public ExtraController(ExtraService extraService, ExtraMapper extraMapper) {
        this.extraService = extraService;
        this.extraMapper = extraMapper;
    }

    @GetMapping
    public ResponseEntity<List<ExtraDetailsDTO>> handleGet() throws ServiceException {
        log.info("Handling GET request - /extras");
        List<Extra> extras = extraService.findAllExtra();
        return ResponseEntity.ok(extraMapper.toExtraDetailsDTOList(extras));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtraDetailsDTO> handleGetById(@PathVariable Long id) throws ServiceException {
        log.info("Handling GET request - /extras/{id}");
        Extra extra = extraService.findById(id);
        return ResponseEntity.ok(extraMapper.toExtraDetailsDTO(extra));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ExtraDetailsDTO> handleGetByName(@PathVariable String name) throws ServiceException {
        log.info("Handling GET request - /extras/{id}");
        Extra extra = extraService.findByName(name);
        return ResponseEntity.ok(extraMapper.toExtraDetailsDTO(extra));
    }

    @PostMapping
    public ResponseEntity<ExtraDetailsDTO>
        handlePost(@RequestBody @Valid ExtraInDTO extraInDTO) throws ServiceException {
        log.info("Handling POST request - /extras");

        Extra extra = extraMapper.toExtra(extraInDTO);
        Extra savedExtra = extraService.addExtra(extra);
        return ResponseEntity.ok(extraMapper.toExtraDetailsDTO(savedExtra));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtraDetailsDTO>
        handlePut(@PathVariable Long id, @RequestBody @Valid ExtraDetailsDTO extraDetailsDTO)
            throws ServiceException {
        log.info("Handling PATCH request - /extras/{id}");
        Extra savedExtra = extraService.findById(id);
        Extra newExtra = extraMapper.detailsToExtra(extraDetailsDTO);
        newExtra.setId(id);
        newExtra.setCar(savedExtra.getCar());
        Extra modifiedExtra = extraService.modifyExtra(newExtra);
        return ResponseEntity.ok(extraMapper.toExtraDetailsDTO(modifiedExtra));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExtraDetailsDTO>
        handlePatch(@PathVariable Long id, @RequestBody @Valid ExtraDetailsDTO extraDetailsDTO)
            throws ServiceException {
        log.info("Handling PATCH request - /extras/{id}");
        Extra savedExtra = extraService.findById(id);
        Extra newExtra = extraMapper.detailsToExtra(extraDetailsDTO);
        newExtra.setId(id);
        newExtra.setCar(savedExtra.getCar());
        Extra modifiedExtra = extraService.modifyExtra(newExtra);
        return ResponseEntity.ok(extraMapper.toExtraDetailsDTO(modifiedExtra));
    }

    // TODO: megnezni a DELETE-t, egyikse elfogadott metodus?!?!?!
    @DeleteMapping("/{id}")
    public ResponseEntity<String> handleDelete(@PathVariable Long id) throws ServiceException {
        log.info("Handling DELETE request - /extras/{id}");

        extraService.removeExtra(id);
        return ResponseEntity.ok("Extra deleted");
    }
}