package kpaas.cumulonimbus.kpaas_project_service.Service;

import jakarta.persistence.EntityNotFoundException;
import kpaas.cumulonimbus.kpaas_project_service.DAO.ParticipatesRepository;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Participates;
import kpaas.cumulonimbus.kpaas_project_service.Entity.ParticipatesPK;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipatesService {
    private final ParticipatesRepository participatesRepository;
    public ParticipatesService(ParticipatesRepository participatesRepository) {
        this.participatesRepository = participatesRepository;
    }

    public Participates save(Participates participates) {
        return participatesRepository.save(participates);
    }

    public void delete(Participates participates) {
        participatesRepository.delete(participates);
    }

    public Participates newParticipates(Project project, String uid) {
        Participates participates = Participates.builder()
                .uid(uid)
                .pid(project)
                .active(false)
                .build();
        return save(participates);
    }

    public List<Participates> findAllByProject(Project project) {
        return participatesRepository.findAllByPid(project);
    }

    public boolean existsByProjectAndUid(Project project, String uid){
        return participatesRepository.existsByPidAndUid(project, uid);
    }

    public Participates getByProjectAndUid(Project project, String uid){
        Optional<Participates> participatesOptional = participatesRepository.findByPidAndUid(project, uid);
        if(participatesOptional.isPresent()){
            return participatesOptional.get();
        }
        else{
            throw new EntityNotFoundException("Participate info not found");
        }
    }

    @Transactional
    public void setToActive(Participates participates){
        participates.setActive(true);
        participatesRepository.save(participates);
    }
}
