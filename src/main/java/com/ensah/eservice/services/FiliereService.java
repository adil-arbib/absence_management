package com.ensah.eservice.services;


import com.ensah.eservice.dto.filieres.FiliereDTO;
import com.ensah.eservice.dto.filieres.FiliereMapper;
import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.dto.niveau.NiveauMapper;
import com.ensah.eservice.dto.users.enseignant.EnseignantDTO;
import com.ensah.eservice.dto.users.enseignant.EnseignantMapper;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.*;
import com.ensah.eservice.repositories.AccreditationRepository;
import com.ensah.eservice.repositories.EnseignantRepository;
import com.ensah.eservice.repositories.FiliereRepository;
import com.ensah.eservice.repositories.NiveauRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FiliereService {

    private final FiliereRepository filiereRepository;
    private final NiveauRepository niveauRepository;
    private final EnseignantRepository enseignantRepository;
    private final AccreditationRepository accreditationRepository;
    private final FiliereMapper filiereMapper;
    private final NiveauMapper niveauMapper;
    private final EnseignantMapper enseignantMapper;


    public FiliereDTO getFiliereById(Long id) throws NotFoundException {
        return filiereMapper.toFiliereDTO(filiereRepository.
                findById(id).orElseThrow(NotFoundException::new));
    }

    public List<FiliereDTO> getAll(){
            return filiereMapper.toFilierDTOList(filiereRepository.findAll());
    }

    public Page<FiliereDTO> getAll(int page, int size){
        return filiereRepository.findAll(PageRequest.of(page, size)).map(filiereMapper::toFiliereDTO);
    }

    public Page<FiliereDTO> findByNomContains(int page, int size, String keyword){

        Page<Filiere> filierePage = filiereRepository.
                findByNomContains(keyword, PageRequest.of(page, size));

        return filierePage.map(filiereMapper::toFiliereDTO);
    }

    public Page<FiliereDTO> getFilierePage(int page ,int size, String alias){

        return alias.isEmpty() ?
                getAll(page, size) : findByNomContains(page, size, alias);
    }

    public FiliereDTO create(FiliereDTO filiereDTO, Long enseignantId,
                             List<Long> niveauxIds)
            throws AlreadyExistsException, NotFoundException {

        if(filiereRepository.existsByNomOrAlias(filiereDTO.getNom(), filiereDTO.getAlias()))
            throw new AlreadyExistsException();


        Filiere filiere = filiereMapper.createFilier(filiereDTO);

        if(niveauxIds != null && !niveauxIds.isEmpty() && enseignantId != null
        ){

            Enseignant enseignant = enseignantRepository.findById(enseignantId).orElseThrow(NotFoundException::new);
            List<Niveau> niveauList = new ArrayList<>();
            Collection<Accreditation> accreditationCollection = new ArrayList<>();

            Calendar calendar  = Calendar.getInstance();
            Date now = calendar.getTime();

            Accreditation accreditation = new Accreditation();
            accreditation.setCoordinateur(enseignant);

//            filiere.setCreateAt(now);
            accreditation.setDebutAccreditation(filiere.getCreateAt());



            for(Long id : niveauxIds){
                Niveau niveau = niveauRepository.findById(id).
                        orElseThrow(NotFoundException::new);

                niveauList.add(niveau);
            }


            filiere.setNiveaux(niveauList);
            accreditationCollection.add(accreditation);
            filiere.setAccreditations(accreditationCollection);

            accreditationRepository.save(accreditation);



        }
        return filiereMapper.toFiliereDTO(filiereRepository.save(filiere));
    }

    public void update(FiliereDTO filiereDTO) throws NotFoundException {
        Filiere filiere = filiereRepository.findById(filiereDTO.getId()).
                orElseThrow(NotFoundException::new);

        filiereMapper.updateFiliereFromDTO(filiereDTO, filiere);
        filiereRepository.save(filiere);
    }

    public void deleteFiliere(Long id) throws NotFoundException {
        Filiere filiere = filiereRepository.findById(id).
                orElseThrow(NotFoundException::new);

        filiereRepository.delete(filiere);
    }


    public void addCordinnateurToFiliere(Long filiereId, Long enseignantId) throws NotFoundException {

        Filiere filiere = filiereRepository.findById(filiereId).
                orElseThrow(NotFoundException::new);
        Enseignant enseignant = enseignantRepository.findById(enseignantId).
                orElseThrow(NotFoundException::new);

        List<Accreditation> accreditationList = (List<Accreditation>) filiere.getAccreditations();
        Calendar calendar  = Calendar.getInstance();
        Date now = calendar.getTime();

//        Accreditation currentAccreditation = new Accreditation();
        if(!accreditationList.isEmpty()){
            Accreditation currentAccreditation = accreditationList.get(accreditationList.size() - 1);
            currentAccreditation.setFinAccreditation(now);
        }


        Accreditation accreditation = new Accreditation();
        accreditation.setCoordinateur(enseignant);
        accreditation.setDebutAccreditation(now);


        filiere.getAccreditations().add(accreditation);
        accreditationRepository.save(accreditation);
        filiereRepository.save(filiere);
    }


    public void addNiveauToFiliere(Long filiereId, List<Long> niveauList) throws NotFoundException {
        Filiere filiere = filiereRepository.findById(filiereId).
                orElseThrow(NotFoundException::new);

        List<Niveau> niveaux = niveauRepository.findByIdIn(niveauList);
        filiere.getNiveaux().addAll(niveaux);
        filiereRepository.save(filiere);
    }

    public List<NiveauDTO> getRestOfNiveaux(Long id) throws NotFoundException {

        Filiere filiere = filiereRepository.findById(id).orElseThrow(NotFoundException::new);
        List<Niveau> niveauList = niveauRepository.findByIdNotIn(
                filiere.getNiveaux().stream().map(Niveau::getId).collect(Collectors.toList()));
        return niveauList.isEmpty()
                ? niveauRepository.findAll().stream().map(niveauMapper::toNiveauDTO).collect(Collectors.toList())
                : niveauList.stream().map(niveauMapper::toNiveauDTO).collect(Collectors.toList());
    }

    public List<EnseignantDTO> getRestOfEnseignants(Long id) throws NotFoundException {

        Filiere filiere = filiereRepository.findById(id).orElseThrow(NotFoundException::new);

            List<Long> idsList = new ArrayList<>();
            List<Accreditation> accreditationList = (List<Accreditation>) filiere.getAccreditations();

            for (Accreditation acc : accreditationList){
                idsList.add((acc.getCoordinateur().getId()));
            }

            List<Enseignant> enseignantList = enseignantRepository.findByIdNotIn(idsList);



        return enseignantList.isEmpty()
                ? enseignantRepository.findAll().stream().map(enseignantMapper::toEnseignantDTO).collect(Collectors.toList())
                : enseignantList.stream().map(enseignantMapper::toEnseignantDTO).collect(Collectors.toList());
    }

    public EnseignantDTO getCurrentCoordinnateur(Long id) throws NotFoundException {
        Filiere filiere = filiereRepository.findById(id).orElseThrow(NotFoundException::new);
        List<Accreditation> accreditationList = (List<Accreditation>) filiere.getAccreditations();
        if (accreditationList.isEmpty())
                return enseignantMapper.toEnseignantDTO(new Accreditation().getCoordinateur());
            Enseignant enseignant = accreditationList.get(accreditationList.size() - 1).
                    getCoordinateur();


        return enseignantMapper.toEnseignantDTO(enseignant);
    }

    public void removeNiveauFromFilier(Long filierId, Long niveauId) throws NotFoundException {
        Filiere filiere = filiereRepository.findById(filierId).orElseThrow(NotFoundException::new);
        Niveau niveau = niveauRepository.findById(niveauId).orElseThrow(NotFoundException::new);

        filiere.getNiveaux().remove(niveau);
        filiereRepository.save(filiere);



    }



}
