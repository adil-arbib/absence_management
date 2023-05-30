package com.ensah.eservice.services.members;

import com.ensah.eservice.dto.users.cadre_admin.CadreAdministrateurDTO;
import com.ensah.eservice.dto.users.cadre_admin.CadreAdministrateurMapper;
import com.ensah.eservice.dto.users.enseignant.EnseignantDTO;
import com.ensah.eservice.exceptions.alreadyExists.CneAlreadyExistsException;
import com.ensah.eservice.exceptions.alreadyExists.EmailAlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.CadreAdministrateur;
import com.ensah.eservice.models.Enseignant;
import com.ensah.eservice.models.File;
import com.ensah.eservice.models.Role;
import com.ensah.eservice.repositories.CadreAdministrateurRepository;
import com.ensah.eservice.repositories.ImageRepository;
import com.ensah.eservice.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CadreAdministrateurService {

   private final CadreAdministrateurRepository cadreAdministrateurRepository;

   private final UtilisateurRepository utilisateurRepository;

   private final CadreAdministrateurMapper cadreAdministrateurMapper;

   private final ImageRepository imageRepository;


   public CadreAdministrateurDTO getById(Long id) throws NotFoundException {
      return cadreAdministrateurMapper.toCadreAdministrateurDTO(cadreAdministrateurRepository.findById(id).orElseThrow(NotFoundException::new));
   }

   public void create(CadreAdministrateurDTO cadreAdministrateurDTO) throws EmailAlreadyExistsException, CneAlreadyExistsException {
      if (utilisateurRepository.existsByEmail(cadreAdministrateurDTO.getEmail())) throw new EmailAlreadyExistsException();
      if(cadreAdministrateurRepository.existsByCin(cadreAdministrateurDTO.getCin())) throw new CneAlreadyExistsException();
      CadreAdministrateur cadreAdministrateur = cadreAdministrateurMapper.toCadreAdministrateur(cadreAdministrateurDTO);
      cadreAdministrateur.setRole(Role.CADRE_ADMINISTRATEUR);
      cadreAdministrateurRepository.save(cadreAdministrateur);
   }


   public Page<CadreAdministrateurDTO> getAll(int page, int size) {
      return cadreAdministrateurRepository.findAll(PageRequest.of(page, size)).map(cadreAdministrateurMapper::toCadreAdministrateurDTO);
   }

   public Page<CadreAdministrateurDTO> findByAttributesContains(int page, int size, String keyword) {
      return cadreAdministrateurRepository.findByNomContainsOrPrenomContainsOrEmailContains(keyword, keyword, keyword, PageRequest.of(page, size))
              .map(cadreAdministrateurMapper::toCadreAdministrateurDTO);
   }


   public void delete(Long id) throws NotFoundException {
      CadreAdministrateur cadreAdministrateur = cadreAdministrateurRepository.findById(id).orElseThrow(NotFoundException::new);
      cadreAdministrateurRepository.delete(cadreAdministrateur);
   }


   public void update(CadreAdministrateurDTO cadreAdministrateurDTO, MultipartFile file) throws NotFoundException, EmailAlreadyExistsException, IOException, CneAlreadyExistsException {
      CadreAdministrateur cadreAdministrateur = cadreAdministrateurRepository.findById(cadreAdministrateurDTO.getId())
              .orElseThrow(NotFoundException::new);
      if(!cadreAdministrateur.getEmail().equals(cadreAdministrateurDTO.getEmail())
              && utilisateurRepository.existsByEmail(cadreAdministrateurDTO.getEmail()))
         throw new EmailAlreadyExistsException();

      if(!cadreAdministrateur.getCin().equals(cadreAdministrateurDTO.getCin())
              && cadreAdministrateurRepository.existsByCin(cadreAdministrateurDTO.getCin()))
         throw new CneAlreadyExistsException();

      cadreAdministrateurMapper.update(cadreAdministrateurDTO, cadreAdministrateur);

      if(file.getSize() != 0) {
         File image = new File();
         image.setData(file.getBytes());
         image.setType(file.getContentType());
         cadreAdministrateur.setImage(imageRepository.save(image));
      }
      cadreAdministrateurRepository.save(cadreAdministrateur);
   }

}
