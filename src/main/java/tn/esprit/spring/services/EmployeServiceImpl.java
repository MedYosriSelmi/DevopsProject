package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
		Optional<Employe> employe = employeRepository.findById(employeId);
		Employe emp=null;
		if (employe.isPresent()) {
			emp = employe.get();
		}
		if ((emp!=null)) {
			emp.setEmail(email);
			employeRepository.save(emp);
		}
		

	}

		
	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);

		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		if (depManagedEntity.isPresent() && employeManagedEntity.isPresent())
		{
			Departement departement = depManagedEntity.get();
			
			Employe employe = employeManagedEntity.get();
			
			if((departement.getEmployes() == null)){

				List<Employe> employes = new ArrayList<>();
				employes.add(employe);
				departement.setEmployes(employes);
			}else{

				departement.getEmployes().add(employe);
			}
		}
		
		}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);
		Departement dep=null;
		if (depManagedEntity.isPresent()) {
			dep = depManagedEntity.get();
		
		
		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;//a revoir
			}
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
			LOG.error(String.format("Erreur dans l'ajout du contrat : %s ",e));
		}
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		try
		{
			LOG.info("Affectation du contrat a employe : ");
			LOG.debug("Selection du contrat : ");
			Optional<Contrat> contratManagedEntity = contratRepoistory.findById(contratId);
			Contrat con=null;
			if (contratManagedEntity.isPresent()) {
				con=contratManagedEntity.get();
			}
			LOG.debug("Contrat selectionné : ");
			LOG.debug("Selection de l'employe");
			Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
			Employe emp=null;
			if (employeManagedEntity.isPresent()) {
				emp=employeManagedEntity.get();
			}
			LOG.debug("Employé selectionné : ");

			LOG.debug("Affecter contrat a employe : ");
			
			if ((emp!=null)&&(con!=null)) {
				con.setEmploye(emp);
				contratRepoistory.save(con);
			}
			
			LOG.info("Contrat affecté à employe sans errors : ");

		}catch (Exception e) {
			LOG.error(String.format("Erreur dans l'affectation du contrat : %s ",e));
		}
		
		
	}
//Test pipeline with git
	public String getEmployePrenomById(int employeId) {
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		Employe emp=null;
		if (employeManagedEntity.isPresent()) {
			emp=employeManagedEntity.get();
		}
		
		String prenom="";
		if (emp!=null) {
			prenom=emp.getPrenom();
		}
		
		return prenom;
	}
	
	public void deleteEmployeById(int employeId)
	{
		
		Optional<Employe> employe = employeRepository.findById(employeId);
		Employe emp=null;
		if (employe.isPresent()) 
			emp=employe.get();
		
		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		if (emp != null){
		for(Departement dep : emp.getDepartements()){
			dep.getEmployes().remove(emp);
		}
		}
		if (emp != null){
		employeRepository.delete(emp);
		}
	}
	public void deleteContratById(int contratId) {
		try{
			
			LOG.info("suppression d'un contrat : ");
			LOG.debug("selection du contrat a supprimé : ");
			
			Optional<Contrat> contratManagedEntity = contratRepoistory.findById(contratId);
			Contrat con=null;
			if (contratManagedEntity.isPresent()) {
				con=contratManagedEntity.get();
			}
			
			LOG.debug("suppression du contrat : ");
			if (con!=null) {
				contratRepoistory.delete(con);
			}
			LOG.debug("je viens de supprimer un contrat : ");
			
			LOG.info("suppression without errors : " );
		}catch(Exception e){
			LOG.error(String.format("Erreur dans l'affectation du contrat: %s ",e));
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
			LOG.error(String.format("Erreur dans la suppression de tous les contrats : %s ",e));
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
