package com.ensah.eservice.services.members;

import com.ensah.eservice.dto.users.enseignant.EnseignantDTO;
import com.ensah.eservice.dto.users.enseignant.EnseignantMapper;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantMapper;
import com.ensah.eservice.exceptions.alreadyExists.CneAlreadyExistsException;
import com.ensah.eservice.exceptions.alreadyExists.EmailAlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Enseignant;
import com.ensah.eservice.models.Etudiant;
import com.ensah.eservice.models.File;
import com.ensah.eservice.models.Role;
import com.ensah.eservice.repositories.EnseignantRepository;
import com.ensah.eservice.repositories.EtudiantRepository;
import com.ensah.eservice.repositories.ImageRepository;
import com.ensah.eservice.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EnseignantService {

   private final EnseignantRepository enseignantRepository;

   private final UtilisateurRepository utilisateurRepository;

   private final ImageRepository imageRepository;

   private final EnseignantMapper enseignantMapper;

   private final Logger logger = Logger.getLogger(EtudiantService.class.getName());


   public EnseignantDTO getById(Long id) throws NotFoundException {
      return enseignantMapper.toEnseignantDTO(enseignantRepository.findById(id).orElseThrow(NotFoundException::new));
   }

   public void create(EnseignantDTO enseignantDTO) throws CneAlreadyExistsException, EmailAlreadyExistsException {
      if (enseignantRepository.existsByCin(enseignantDTO.getCin())) throw new CneAlreadyExistsException();
      if (utilisateurRepository.existsByEmail(enseignantDTO.getEmail())) throw new EmailAlreadyExistsException();

      Enseignant enseignant = enseignantMapper.toEnseignant(enseignantDTO);
      enseignant.setRole(Role.ENSEIGNANT);
      enseignantRepository.save(enseignant);
   }


   public Page<EnseignantDTO> getAll(int page, int size) {
      return enseignantRepository.findAll(PageRequest.of(page, size)).map(enseignantMapper::toEnseignantDTO);
   }

   public Page<EnseignantDTO> findByAttributesContains(int page, int size, String keyword) {
      return enseignantRepository.findByNomContainsOrPrenomContainsOrCinContains(keyword, keyword, keyword, PageRequest.of(page, size))
              .map(enseignantMapper::toEnseignantDTO);
   }


   public void delete(Long id) throws NotFoundException {
      Enseignant enseignant = enseignantRepository.findById(id).orElseThrow(NotFoundException::new);
      enseignantRepository.delete(enseignant);
   }


   public void update(EnseignantDTO enseignantDTO, MultipartFile file) throws NotFoundException, EmailAlreadyExistsException, CneAlreadyExistsException, IOException {
      Enseignant enseignant = enseignantRepository.findById(enseignantDTO.getId()).orElseThrow(NotFoundException::new);
      if(!enseignant.getEmail().equals(enseignantDTO.getEmail())
              && utilisateurRepository.existsByEmail(enseignantDTO.getEmail()))
         throw new EmailAlreadyExistsException();
      if(!enseignant.getCin().equals(enseignantDTO.getCin())
              && enseignantRepository.existsByCin(enseignantDTO.getCin()))
         throw new CneAlreadyExistsException();

      enseignantMapper.update(enseignantDTO, enseignant);

      if(file.getSize() != 0) {
         File image = new File();
         image.setData(file.getBytes());
         image.setType(file.getContentType());
         enseignant.setImage(imageRepository.save(image));
      }
      enseignantRepository.save(enseignant);
   }

}
