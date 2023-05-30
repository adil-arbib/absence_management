package com.ensah.eservice.services.members;

import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantMapper;
import com.ensah.eservice.exceptions.alreadyExists.CneAlreadyExistsException;
import com.ensah.eservice.exceptions.alreadyExists.EmailAlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Etudiant;
import com.ensah.eservice.models.File;
import com.ensah.eservice.models.Role;
import com.ensah.eservice.repositories.EtudiantRepository;
import com.ensah.eservice.repositories.ImageRepository;
import com.ensah.eservice.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EtudiantService {

   private final EtudiantRepository etudiantRepository;

   private final UtilisateurRepository utilisateurRepository;

   private final ImageRepository imageRepository;
   private final EtudiantMapper etudiantMapper;

   private final Logger logger = Logger.getLogger(EtudiantService.class.getName());


   public EtudiantDTO getById(Long id) throws NotFoundException {
      return etudiantMapper.toEtudiantDTO(etudiantRepository.findById(id).orElseThrow(NotFoundException::new));
   }

   public void create(EtudiantDTO etudiantDTO) throws CneAlreadyExistsException, EmailAlreadyExistsException {
      if (etudiantRepository.existsByCne(etudiantDTO.getCne())) throw new CneAlreadyExistsException();
      if (utilisateurRepository.existsByEmail(etudiantDTO.getEmail())) throw new EmailAlreadyExistsException();

      Etudiant etudiant = etudiantMapper.toEtudiant(etudiantDTO);
      etudiant.setRole(Role.ETUDIANT);
      etudiantRepository.save(etudiant);
   }


   public Page<EtudiantDTO> getAll(int page, int size) {
      return etudiantRepository.findAll(PageRequest.of(page, size)).map(etudiantMapper::toEtudiantDTO);
   }

   public Page<EtudiantDTO> findByAttributesContains(int page, int size, String keyword) {
      return etudiantRepository.findByNomContainsAndDeletedFalseOrPrenomContainsAndDeletedFalseOrNomArabeContainsAndDeletedFalseOrPrenomArabContainsAndDeletedFalse(keyword, keyword, keyword, keyword, PageRequest.of(page, size))
              .map(etudiantMapper::toEtudiantDTO);
   }


   public void delete(Long id) throws NotFoundException {
      Etudiant etudiant = etudiantRepository.findById(id).orElseThrow(NotFoundException::new);
      etudiant.setDeleted(true);
      etudiantRepository.save(etudiant);
   }


   public void update(EtudiantDTO etudiantDTO, MultipartFile file) throws NotFoundException, EmailAlreadyExistsException, CneAlreadyExistsException, IOException {
      Etudiant etudiant = etudiantRepository.findById(etudiantDTO.getId()).orElseThrow(NotFoundException::new);
      if(!etudiant.getEmail().equals(etudiantDTO.getEmail())
              && utilisateurRepository.existsByEmail(etudiantDTO.getEmail()))
         throw new EmailAlreadyExistsException();
      if(!etudiant.getCne().equals(etudiantDTO.getCne())
              && etudiantRepository.existsByCne(etudiantDTO.getCne()))
         throw new CneAlreadyExistsException();

      etudiantMapper.update(etudiantDTO, etudiant);
      if(file.getSize() != 0) {
         File image = new File();
         image.setData(file.getBytes());
         image.setType(file.getContentType());
         etudiant.setImage(imageRepository.save(image));
      }
      etudiantRepository.save(etudiant);
   }


   public Page<EtudiantDTO> getDeletedEtudiants(int page, int size, String keyword) {
      return etudiantRepository.findByNomContainsAndDeletedTrueOrPrenomContainsAndDeletedTrueOrNomArabeContainsAndDeletedTrueOrPrenomArabContainsAndDeletedTrue(keyword, keyword, keyword, keyword, PageRequest.of(page, size))
              .map(etudiantMapper::toEtudiantDTO);
   }

   public void recover(Long id) throws NotFoundException {
      Etudiant etudiant = etudiantRepository.findById(id).orElseThrow(NotFoundException::new);
      etudiant.setDeleted(false);
      etudiantRepository.save(etudiant);
   }


}
