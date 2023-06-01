package com.ensah.eservice.services;

import com.ensah.eservice.dto.filieres.FiliereDTO;
import com.ensah.eservice.dto.modules.ModuleDTO;
import com.ensah.eservice.dto.modules.ModuleMapper;
import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.dto.niveau.NiveauMapper;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Filiere;
import com.ensah.eservice.models.Module;
import com.ensah.eservice.models.Niveau;
import com.ensah.eservice.repositories.FiliereRepository;
import com.ensah.eservice.repositories.ModuleRepository;
import com.ensah.eservice.repositories.NiveauRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NiveauService {

    private final NiveauRepository niveauRepository;
    private final ModuleRepository moduleRepository;
    private final FiliereRepository filiereRepository;

    private final NiveauMapper niveauMapper;
    private final ModuleMapper moduleMapper;



    public NiveauDTO getNiveauById(Long id) throws NotFoundException {
        return niveauMapper.toNiveauDTO(niveauRepository.findById(id)
                .orElseThrow(NotFoundException::new));
    }

    public List<NiveauDTO> getAll(){
        return niveauMapper.listToNiveauDTO(niveauRepository.findAll());
    }

    public NiveauDTO create(NiveauDTO niveauDTO, List<Long> modulesIds) throws AlreadyExistsException, NotFoundException {

        if(niveauRepository.existsByAliasOrTitre(niveauDTO.getAlias(), niveauDTO.getTitre()))
                throw new AlreadyExistsException();

        Niveau niveau = niveauMapper.createNiveau(niveauDTO);

        if(modulesIds != null && !modulesIds.isEmpty()){
            List<Module> moduleList = new ArrayList<>();
            for(Long id : modulesIds){
                Module module = moduleRepository.findById(id).
                        orElseThrow(NotFoundException::new);

                moduleList.add(module);
            }
            niveau.setModules(moduleList);
        }
        return niveauMapper.toNiveauDTO(niveauRepository.save(niveau));
    }

    public void update(NiveauDTO niveauDTO) throws NotFoundException {
        Niveau niveau = niveauRepository.findById(niveauDTO.getId()).
                orElseThrow(NotFoundException::new);

        niveauMapper.updateNiveauFromDTO(niveauDTO, niveau);
       niveauRepository.save(niveau);
    }

    public Page<NiveauDTO> getAll(int page, int size){
        return niveauRepository.findAll(PageRequest.of(page, size)).map(niveauMapper::toNiveauDTO);
    }

    public Page<NiveauDTO> findByAliasContains(int page, int size, String alias){

        Page<Niveau> niveauPage = niveauRepository.
                findByAliasContains(alias, PageRequest.of(page, size));

        return niveauPage.map(niveauMapper::toNiveauDTO);
    }

    public Page<NiveauDTO> getNiveauPage(int page ,int size, String alias){

        return alias.isEmpty() ?
                getAll(page, size) : findByAliasContains(page, size, alias);
    }

    public List<ModuleDTO> niveauOtherModules(Long id) throws NotFoundException {
        Niveau niveau = niveauRepository.findById(id).orElseThrow(NotFoundException::new);

        List<Module> moduleList = (List<Module>) niveau.getModules();
        List<Long> modulesIds = new ArrayList<>();

        for (Module module : moduleList){
            modulesIds.add(module.getId());
        }

        List<Module> modules = moduleRepository.findByIdNotIn(modulesIds);

        return modules.isEmpty() ?
                moduleRepository.findAll().stream().map(moduleMapper::moduleToModuleDTO).
                        collect(Collectors.toList()):
                modules.stream().map(moduleMapper::moduleToModuleDTO).collect(Collectors.toList());

    }

    public void addModulesToNiveau(Long id, List<Long> selectedModulesIds ) throws NotFoundException {

        Niveau niveau = niveauRepository.findById(id).orElseThrow(NotFoundException::new);

        List<Module> moduleList = moduleRepository.findByIdIn(selectedModulesIds);
        niveau.getModules().addAll(moduleList);
        niveauRepository.save(niveau);
    }

    public void removeModuleFromNiveau(Long niveauId, Long moduleId ) throws NotFoundException {
        Niveau niveau = niveauRepository.findById(niveauId).orElseThrow(NotFoundException::new);
        Module module = moduleRepository.findById(moduleId).orElseThrow(NotFoundException::new);

        niveau.getModules().remove(module);
        niveauRepository.save(niveau);
    }

    public void removeNiveau(Long id) throws NotFoundException {
        Niveau niveau = niveauRepository.findById(id).orElseThrow(NotFoundException::new);
        niveauRepository.delete(niveau);
    }

    public List<NiveauDTO> getByFiliere(FiliereDTO filiereDTO) throws NotFoundException {
        Filiere filiere =filiereRepository.findByNomOrAlias(filiereDTO.getNom(),filiereDTO.getAlias())
                .orElseThrow(NotFoundException::new);
        List<Niveau> niveauList= filiere.getNiveaux().stream().toList();
        return niveauMapper.listToNiveauDTO(niveauList);
    }









}
