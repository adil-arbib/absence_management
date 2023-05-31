package com.ensah.eservice.services.members;

import com.ensah.eservice.dto.users.UserDTO;
import com.ensah.eservice.dto.users.comptes.CompteDTO;
import com.ensah.eservice.dto.users.comptes.CompteMapper;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Compte;
import com.ensah.eservice.models.Etudiant;
import com.ensah.eservice.models.Utilisateur;
import com.ensah.eservice.repositories.*;
import com.ensah.eservice.security.PasswordEncoder;
import com.ensah.eservice.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompteService {

   private final CompteRepository compteRepository;


   private final EtudiantRepository etudiantRepository;

   private final EnseignantRepository enseignantRepository;

   private final CadreAdministrateurRepository cadreAdministrateurRepository;

   private final UtilisateurRepository utilisateurRepository;

   private final CompteMapper compteMapper;

   private final PasswordEncoder passwordEncoder;


   public CompteDTO suggestCompte(String cin, String cne) throws NotFoundException {
      Utilisateur utilisateur;
      if(!cne.isEmpty()) {
         utilisateur = etudiantRepository.findByCne(cne).orElseThrow(()->
                 new NotFoundException("étudiant avec cne "+cne+" introuvable"));
      }else {
         utilisateur = enseignantRepository.findByCin(cin).orElse(null);
         if(utilisateur == null) {
            utilisateur = cadreAdministrateurRepository.findByCin(cin).orElseThrow(()->
                    new NotFoundException("enseignant ou cadre administrateur avec cin "+cin+" est introuvable"));
         }
      }
      if(compteRepository.existsByUtilisateurId(utilisateur.getId()))
         throw  new NotFoundException("l'utilisateur a déjà un compte");

      String username = utilisateur.getPrenom() + utilisateur.getNom();
      int i=1;
      while (compteRepository.existsByUsername(username)) {
         username = username+i;
         i++;
      }
      String password = PasswordGenerator.generateRandomPassword();
      UserDTO userDTO = new UserDTO();
      userDTO.setId(utilisateur.getId());
      CompteDTO compteDTO = new CompteDTO();
      compteDTO.setUsername(username);
      compteDTO.setPassword(password);
      compteDTO.setUtilisateur(userDTO);
      return compteDTO;
   }


   public void createCompte(CompteDTO compteDTO) throws AlreadyExistsException, NotFoundException {
      if(compteRepository.existsByUsername(compteDTO.getUsername()))
         throw new AlreadyExistsException("Ce nom d'utilisateur existe déjà");

      Compte compte = compteMapper.toCompte(compteDTO);
      compte.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(compteDTO.getPassword()));
      compte.setUtilisateur(utilisateurRepository.findById(compteDTO.getUtilisateur().getId())
              .orElseThrow(NotFoundException::new));
      compte.setEnabled(true);
      compte.setAccountNotLocked(true);
      compte.setAccountNotExpired(true);
      compteRepository.save(compte);
   }

   public Page<CompteDTO> getAll(int page, int size, String keyword) {
      return compteRepository.findByUsernameContains(keyword, PageRequest.of(page, size))
              .map(compteMapper::toCompteDTO);
   }

   public void delete(Long id) throws NotFoundException {
      Compte compte = compteRepository.findById(id).orElseThrow(NotFoundException::new);
      compteRepository.delete(compte);
   }

   public CompteDTO getById(Long id) throws NotFoundException {
      return compteMapper.toCompteDTO(compteRepository.findById(id).orElseThrow(NotFoundException::new));
   }

   public void update(CompteDTO compteDTO) throws NotFoundException, AlreadyExistsException {
      Compte compte = compteRepository.findById(compteDTO.getId())
              .orElseThrow(NotFoundException::new);
      if(!compte.getUsername().equals(compteDTO.getUsername()) && compteRepository.existsByUsername(compteDTO.getUsername()))
         throw new AlreadyExistsException("Ce nom d'utilisateur existe déjà");
      compteMapper.update(compteDTO, compte);
      if(!compteDTO.getPassword().isEmpty()) {
         compte.setPassword(passwordEncoder.bCryptPasswordEncoder()
                 .encode(compteDTO.getPassword()));
      }
      compte.setAccountNotExpired(true);
      compte.setAccountNotLocked(true);
      compteRepository.save(compte);
   }




}
