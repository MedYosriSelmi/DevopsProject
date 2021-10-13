package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	private static final Logger LOG = LogManager.getLogger(EmployeServiceImpl.class);

	
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).get();
		employe.setEmail(email);
		employeRepository.save(employe);

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		if(depManagedEntity.getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);

		}

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Departement dep = deptRepoistory.findById(depId).get();

		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;//a revoir
			}
		}
	}

	public int ajouterContrat(Contrat contrat) {

		try{
			LOG.info("le service ajouterContrat a commencé");
			LOG.debug("je VAIS ajouter un contrat : ");
			
			contratRepoistory.save(contrat);
			
			LOG.debug("je viens  de finir l'ajout d'un contrat : ");
			LOG.info("contrat ajouté sans errors : ");

		}catch (Exception e) {
			LOG.error("Erreur dans l'ajout du contrat : " +e);
		}
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		try
		{
			LOG.info("Affectation du contrat a employe : ");
			LOG.debug("Selection du contrat : ");
			Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
			LOG.debug("Contrat selectionné : ");
			LOG.debug("Selection de l'employe");
			Employe employeManagedEntity = employeRepository.findById(employeId).get();
			LOG.debug("Employé selectionné : ");

			LOG.debug("Affecter contrat a employe : ");
			contratManagedEntity.setEmploye(employeManagedEntity);
			contratRepoistory.save(contratManagedEntity);
			LOG.info("Contrat affecté à employe sans errors : ");

		}catch (Exception e) {
			LOG.error("Erreur dans l'affectation du contrat : " +e);
		}
		
		
	}

	public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		return employeManagedEntity.getPrenom();
	}
	public void deleteEmployeById(int employeId)
	{
		Employe employe = employeRepository.findById(employeId).get();

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}

		employeRepository.delete(employe);
	}

	public void deleteContratById(int contratId) {
		try{
			
			LOG.info("suppression d'un contrat : ");
			LOG.debug("selection du contrat a supprimé : ");
			Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
			LOG.debug("suppression du contrat : ");
			contratRepoistory.delete(contratManagedEntity);
			LOG.debug("je viens de supprimer un contrat : ");
			
			LOG.info("suppression without errors : " );
		}catch(Exception e){
			LOG.error("Erreur dans l'affectation du contrat: "+e);
		}

	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
		try{
			LOG.info("Suppression de tout les contrats : ");
			
			LOG.debug("Je vais supprimer tous les contrats : ");
	        employeRepository.deleteAllContratJPQL();
			LOG.debug("Je viens de supprimer tous les contrats : ");

			LOG.info("Contrats supprimes without errors : ");

		}catch (Exception e) {
			LOG.error("Erreur dans la suppression de tous les contrats : " +e);
		}
	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}
	public Employe getEmployerById(int id)
	{
		return employeRepository.findById(id).orElse(null);
	}

}
