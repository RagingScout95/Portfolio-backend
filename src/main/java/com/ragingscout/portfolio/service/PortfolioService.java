package com.ragingscout.portfolio.service;

import com.ragingscout.portfolio.entity.*;
import com.ragingscout.portfolio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PortfolioService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CurrentJobRepository currentJobRepository;

    @Autowired
    private SocialLinkRepository socialLinkRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // Profile methods
    public Profile getProfile() {
        Profile profile = profileRepository.findFirstByOrderByIdAsc();
        if (profile == null) {
            profile = new Profile();
            profile.setName("Your Name");
            profile.setRole("Your Role");
            profile.setTagline("Your Tagline");
            profile.setAbout("About me");
            profile = profileRepository.save(profile);
        }
        return profile;
    }

    public Profile updateProfile(Profile profile) {
        Profile existing = getProfile();
        if (profile.getName() != null) existing.setName(profile.getName());
        if (profile.getRole() != null) existing.setRole(profile.getRole());
        if (profile.getTagline() != null) existing.setTagline(profile.getTagline());
        if (profile.getPhotoUrl() != null) existing.setPhotoUrl(profile.getPhotoUrl());
        if (profile.getAbout() != null) existing.setAbout(profile.getAbout());
        return profileRepository.save(existing);
    }

    // Education methods
    public List<Education> getAllEducations() {
        return educationRepository.findAllByOrderByDisplayOrderAsc();
    }

    public Education createEducation(Education education) {
        if (education.getDisplayOrder() == null) {
            education.setDisplayOrder(educationRepository.count() > 0 ? 
                educationRepository.findAll().size() : 0);
        }
        return educationRepository.save(education);
    }

    public Education updateEducation(Long id, Education education) {
        Education existing = educationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Education not found"));
        if (education.getDegree() != null) existing.setDegree(education.getDegree());
        if (education.getInstitute() != null) existing.setInstitute(education.getInstitute());
        if (education.getYear() != null) existing.setYear(education.getYear());
        if (education.getDisplayOrder() != null) existing.setDisplayOrder(education.getDisplayOrder());
        return educationRepository.save(existing);
    }

    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }

    // Skill methods
    public List<Skill> getAllSkills() {
        return skillRepository.findAllByOrderByDisplayOrderAsc();
    }

    public Skill createSkill(Skill skill) {
        if (skillRepository.findByName(skill.getName()).isPresent()) {
            throw new RuntimeException("Skill already exists");
        }
        if (skill.getDisplayOrder() == null) {
            skill.setDisplayOrder(skillRepository.count() > 0 ? 
                skillRepository.findAll().size() : 0);
        }
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, Skill skill) {
        Skill existing = skillRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Skill not found"));
        if (skill.getName() != null) existing.setName(skill.getName());
        if (skill.getDisplayOrder() != null) existing.setDisplayOrder(skill.getDisplayOrder());
        return skillRepository.save(existing);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

    // CurrentJob methods
    public CurrentJob getCurrentJob() {
        CurrentJob job = currentJobRepository.findFirstByOrderByIdAsc();
        if (job == null) {
            job = new CurrentJob();
            job.setTitle("Your Title");
            job.setCompany("Your Company");
            job.setSince("Date");
            job.setDescription("Description");
            job = currentJobRepository.save(job);
        }
        return job;
    }

    public CurrentJob updateCurrentJob(CurrentJob job) {
        CurrentJob existing = getCurrentJob();
        if (job.getTitle() != null) existing.setTitle(job.getTitle());
        if (job.getCompany() != null) existing.setCompany(job.getCompany());
        if (job.getSince() != null) existing.setSince(job.getSince());
        if (job.getDescription() != null) existing.setDescription(job.getDescription());
        return currentJobRepository.save(existing);
    }

    // SocialLink methods
    public List<SocialLink> getAllSocialLinks() {
        return socialLinkRepository.findAllByOrderByDisplayOrderAsc();
    }

    public SocialLink createSocialLink(SocialLink link) {
        if (link.getDisplayOrder() == null) {
            link.setDisplayOrder(socialLinkRepository.count() > 0 ? 
                socialLinkRepository.findAll().size() : 0);
        }
        return socialLinkRepository.save(link);
    }

    public SocialLink updateSocialLink(Long id, SocialLink link) {
        SocialLink existing = socialLinkRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Social link not found"));
        if (link.getName() != null) existing.setName(link.getName());
        if (link.getUrl() != null) existing.setUrl(link.getUrl());
        if (link.getIcon() != null) existing.setIcon(link.getIcon());
        if (link.getDisplayOrder() != null) existing.setDisplayOrder(link.getDisplayOrder());
        return socialLinkRepository.save(existing);
    }

    public void deleteSocialLink(Long id) {
        socialLinkRepository.deleteById(id);
    }

    // Experience methods
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAllByOrderByDisplayOrderAsc();
    }

    public Experience createExperience(Experience experience) {
        if (experience.getDisplayOrder() == null) {
            experience.setDisplayOrder(experienceRepository.count() > 0 ? 
                experienceRepository.findAll().size() : 0);
        }
        return experienceRepository.save(experience);
    }

    public Experience updateExperience(Long id, Experience experience) {
        Experience existing = experienceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Experience not found"));
        if (experience.getRole() != null) existing.setRole(experience.getRole());
        if (experience.getCompany() != null) existing.setCompany(experience.getCompany());
        if (experience.getFromDate() != null) existing.setFromDate(experience.getFromDate());
        if (experience.getToDate() != null) existing.setToDate(experience.getToDate());
        if (experience.getDescriptions() != null) existing.setDescriptions(experience.getDescriptions());
        if (experience.getDisplayOrder() != null) existing.setDisplayOrder(experience.getDisplayOrder());
        return experienceRepository.save(existing);
    }

    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }

    // Project methods
    public List<Project> getAllProjects() {
        return projectRepository.findAllByOrderByDisplayOrderAsc();
    }

    public Project createProject(Project project) {
        if (project.getDisplayOrder() == null) {
            project.setDisplayOrder(projectRepository.count() > 0 ? 
                projectRepository.findAll().size() : 0);
        }
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project project) {
        Project existing = projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found"));
        if (project.getName() != null) existing.setName(project.getName());
        if (project.getDescription() != null) existing.setDescription(project.getDescription());
        if (project.getTechStack() != null) existing.setTechStack(project.getTechStack());
        if (project.getLiveUrl() != null) existing.setLiveUrl(project.getLiveUrl());
        if (project.getGithubUrl() != null) existing.setGithubUrl(project.getGithubUrl());
        if (project.getDemoUrl() != null) existing.setDemoUrl(project.getDemoUrl());
        if (project.getImageUrl() != null) existing.setImageUrl(project.getImageUrl());
        if (project.getDisplayOrder() != null) existing.setDisplayOrder(project.getDisplayOrder());
        return projectRepository.save(existing);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public void reorderProjects(List<Long> projectIds) {
        for (int i = 0; i < projectIds.size(); i++) {
            Project project = projectRepository.findById(projectIds.get(i))
                .orElseThrow(() -> new RuntimeException("Project not found"));
            project.setDisplayOrder(i);
            projectRepository.save(project);
        }
    }
}

