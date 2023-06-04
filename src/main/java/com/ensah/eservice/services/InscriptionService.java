package com.ensah.eservice.services;

import com.ensah.eservice.dto.inscription.InscriptionDTO;
import com.ensah.eservice.dto.inscription.InscriptionMapper;
import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.dto.niveau.NiveauMapper;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Inscription;
import com.ensah.eservice.models.Niveau;
import com.ensah.eservice.repositories.EtudiantRepository;
import com.ensah.eservice.repositories.InscriptionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
@AllArgsConstructor
public class InscriptionService {

    private InscriptionRepository inscriptionRepository;
    private EtudiantRepository etudiantRepository;
    private InscriptionMapper inscriptionMapper;
    private NiveauMapper niveauMapper;
    public List<InscriptionDTO> getAll(){
        return inscriptionMapper.toInscriptionDTOList(inscriptionRepository.findAll());
    }

    public List<InscriptionDTO> getByNiveau(NiveauDTO niveauDTO){
        Integer year = LocalDate.now().getYear();
        Niveau niveau= niveauMapper.createNiveau(niveauDTO);
        List<Inscription> list= inscriptionRepository.findAllByAnneeAndNiveau(year,niveau);
        return inscriptionMapper.toInscriptionDTOList(list);
    }

    public InscriptionDTO getByEtudiant(String cne) throws NotFoundException {
       return inscriptionMapper.toInscriptionDTO(inscriptionRepository.
                findByEtudiantAndAnnee(etudiantRepository.findByCne(cne).
                        orElseThrow(NotFoundException::new), Year.now().getValue()).
                orElseThrow(NotFoundException::new));
    }
}
