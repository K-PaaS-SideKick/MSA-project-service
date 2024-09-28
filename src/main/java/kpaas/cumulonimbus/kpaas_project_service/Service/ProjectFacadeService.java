package kpaas.cumulonimbus.kpaas_project_service.Service;

import kpaas.cumulonimbus.kpaas_project_service.DTO.JoinProjectReturnDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.JoinRequestDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Participates;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectFacadeService {
    private final ProjectService projectService;
    private final ParticipatesService participatesService;

    public ProjectFacadeService(ProjectService projectService, ParticipatesService participatesService) {
        this.projectService = projectService;
        this.participatesService = participatesService;
    }

    public JoinProjectReturnDTO joinRequest(Long pid, String uid) {
        // owner of the project can't participate oneself
        if (projectService.isOwnerByPid(pid, uid))
            throw new IllegalArgumentException("You are the owner of this project.");

        Project project = projectService.findById(pid);

        // check if already participating or requested
        List<Participates> participatesList = participatesService.findAllByProject(project);
        for (Participates participate : participatesList) {
            if (participate.getUid().equals(uid)) {
                if (participate.isActive())
                    throw new IllegalArgumentException("You already joined this project.");
                throw new IllegalArgumentException("You already requested to join this project.");
            }
        }

        Participates participates = participatesService.newParticipates(project, uid);

        JoinProjectReturnDTO respDTO = new JoinProjectReturnDTO();
        respDTO.setPid(participates.getPid().getPid());
        respDTO.setUid(participates.getUid());

        return respDTO;
    }

    public List<JoinRequestDTO> getJoinRequests(Long pid, String uid) {
        if (!projectService.isOwnerByPid(pid, uid))
            throw new SecurityException("You are not the owner of this project.");

        Project project = projectService.findById(pid);

        List<Participates> participatesList = participatesService.findAllByProject(project);
        List<JoinRequestDTO> returnList = new ArrayList<>();
        for (Participates participate : participatesList) {
            JoinRequestDTO joinRequestDTO = new JoinRequestDTO();
            joinRequestDTO.setUid(participate.getUid());
            joinRequestDTO.setActive(participate.isActive());
            returnList.add(joinRequestDTO);
        }

        return returnList;
    }

    @Transactional
    public JoinProjectReturnDTO acceptJoinRequest(Long pid, String requester_id, String owner_id) {
        // check if owner_id is the owner of pid
        if (!projectService.isOwnerByPid(pid, owner_id))
            throw new SecurityException("You are not the owner of this project.");

        // check if the request exist
        Project project = projectService.findById(pid);
        Participates participates = participatesService.getByProjectAndUid(project, requester_id);
        if(!participatesService.existsByProjectAndUid(project, requester_id))
            throw new IllegalArgumentException("Request does not exist.");

        // check if the request is already accepted
        if(participates.isActive())
            throw new IllegalArgumentException("Already accepted request.");

        // check Project.max_members
        if(project.getMax_members() == project.getCurrent_members())
            throw new IllegalArgumentException("Cannot accept any more request.");

        // set Participates.active to true
        participatesService.setToActive(participates);

        // set Project.current_members += 1
        projectService.increaseCurrent_members(project);

        JoinProjectReturnDTO returnDTO = new JoinProjectReturnDTO();
        returnDTO.setPid(participates.getPid().getPid());
        returnDTO.setUid(participates.getUid());
        return returnDTO;
    }

    @Transactional
    public void rejectJoinRequest(Long pid, String requester_id, String owner_id) {
        // check if owner_id is the owner of pid
        if (!projectService.isOwnerByPid(pid, owner_id))
            throw new SecurityException("You are not the owner of this project.");

        // check if the request exist
        Project project = projectService.findById(pid);
        Participates participates = participatesService.getByProjectAndUid(project, requester_id);
        if(!participatesService.existsByProjectAndUid(project, requester_id))
            throw new IllegalArgumentException("Request does not exist.");

        // delete Participates
        participatesService.delete(participates);

        // set Project.current_members -= 1
        projectService.decreaseCurrent_members(project);
    }
}
