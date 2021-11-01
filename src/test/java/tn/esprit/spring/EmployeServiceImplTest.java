package tn.esprit.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceImplTest {

	@Autowired
	IEmployeService employeS;
	
	@Autowired
	ContratRepository contratRepository;
	
	@Autowired
	EmployeRepository employeRepository;
	
	@Autowired
	IEntrepriseService entrepriseS;
	
	private Employe employe;
	private Contrat contrat;
	
	
	@Before
	public void setUp() throws ParseException {
		this.employe = new Employe();
		this.employe.setPrenom("Selim");
		this.employe.setNom("CHIKH ZAOUALI");
		this.employe.setEmail("selim.chikhzaouali@esprit.tn");
		this.employe.setActif(true);
		this.employe.setRole(Role.INGENIEUR);
		
		this.contrat = new Contrat();
		String dateInString="2021-10-06";
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		Date date=formatter.parse(dateInString);
		this.contrat.setDateDebut(date);
		this.contrat.setSalaire(2000);
	}

// test jenkins
	
	
	@Test
	public void ajouterContratTest() {
		int id=employeS.ajouterEmploye(this.employe);
		this.contrat.setEmploye(this.employe);
		int ref=employeS.ajouterContrat(this.contrat);
		// le service employeS.ajouterContrat(this.contrat) retourne un int qui est la réf
		Optional<Contrat> contratOpt = contratRepository.findById(ref);
		Contrat c=null;
		if (contratOpt.isPresent()) {
			c=contratOpt.get();
		}
		if (c!=null) {
			Assert.assertTrue(c.getReference()> 0);
		}
		employeS.deleteContratById(ref);
		employeS.deleteEmployeById(id);

	}

	@Test
	public void affecterContratAEmployeTest() {
		int id=employeS.ajouterEmploye(this.employe);
		this.contrat.setEmploye(this.employe);
		int ref=employeS.ajouterContrat(this.contrat);
		
		employeS.affecterContratAEmploye(ref, id);
		
		Optional<Contrat> contratOpt = contratRepository.findById(ref);
		Contrat con=null;
		if (contratOpt.isPresent()) {
			con=contratOpt.get();
		}
		
		Optional<Employe> employeOpt = employeRepository.findById(id);
		Employe emp=null;
		if (employeOpt.isPresent()) {
			emp=employeOpt.get();
		}
		int refContrat=0;
		int idContratEmploye =0;
		if ((con!=null)&&(emp!=null)){
			refContrat=con.getReference();
			idContratEmploye = emp.getContrat().getReference();
		}

		// Comparaison pour vérifier si le contrat a bien été affecté
		Assert.assertEquals(idContratEmploye,refContrat);
		employeS.deleteContratById(ref);
		employeS.deleteEmployeById(id);
		
	}

	

	@Test
	public void deleteContratByIdTest() {

		int id=employeS.ajouterEmploye(this.employe);
		this.contrat.setEmploye(this.employe);
		int ref=employeS.ajouterContrat(this.contrat);
		
		employeS.deleteContratById(ref);
		
		// Vérification si le contrat a bien été supprimé
		Optional<Contrat> cont = contratRepository.findById(ref);
		Assert.assertFalse(cont.isPresent());
		employeS.deleteEmployeById(id);
	}
	
	
	

	//test employé  Ahmed Mrabet
	
	
	private static final Logger l = LogManager.getLogger(EmployeServiceImplTest.class);
	@Test
	public void testGetEmployePrenomById() {
		try
		{
			int idE = employeS.ajouterEmploye(
					new Employe("Ahmed", "mrabett", "ahmed.mrabet@spring.tn", true, Role.TECHNICIEN));
		String prenomEmp = employeS.getEmployePrenomById(idE);
		l.info("Prenom de lemploye est : "+prenomEmp);
		assertThat(prenomEmp).isEqualTo("mrabett");
		employeS.deleteEmployeById(idE);
		}catch (Exception e) {
			l.error(String.format("Erreur dans Get EmployePrenom By Id : %s ",e));
			
		}
		
	}

	@Test
	public void testAjouterEmploye() {
		try
		{
		int id = employeS.ajouterEmploye(
				new Employe("mrabet", "ahmed", "mrabet.ahmed@esprit.tn", true, Role.INGENIEUR));
	
		assertThat(id).isGreaterThan(0);
		l.info("Employe added successfully!");
		l.info("verifier la liste des employes");
		List<Employe> employes = employeS.getAllEmployes();

		assertThat(employes).size().isGreaterThan(0);
		l.info("la liste des employer n'est pas null");
		employeS.deleteEmployeById(id);
		
		}catch (Exception e) {
			l.error(String.format("Erreur dans Ajout d'Employe : %s ",e));
		}
	}


	@Test
	public void testMettreAjourEmailByEmployeId() {
		try
		{	
		String email = "bohmid.ahmed@spring.tn";
		int id = employeS.ajouterEmploye(
				new Employe("Test1", "test1", "test1.test1@esprit.tn", true, Role.INGENIEUR));
	
		employeS.mettreAjourEmailByEmployeId(email, id);

		Employe e = employeS.getEmployerById(id);

		assertThat(e.getEmail()).isEqualTo(email);
		employeS.deleteEmployeById(id);
		}catch (Exception e) {
		l.error(String.format("Erreur dans Mettre A jour Email By Employe Id : %s ",e));
	}
	}

	@Test
	public void affecterEmployeADepartement()
	{
		try
		{	
		int idE = employeS.ajouterEmploye(
		new Employe("ahmed", "mrabet", "ahmed.mrabet@spring.tn", true, Role.TECHNICIEN));
		int idD = entrepriseS.ajouterDepartement(
				new Departement("info"));
				
		employeS.affecterEmployeADepartement(idE, idD);
		l.info("Employe added successfully to Departement ");
		l.info("desaffectaion, de lemployer de departement");
		employeS.desaffecterEmployeDuDepartement(idE, idD);
		employeS.deleteEmployeById(idE);
		entrepriseS.deleteDepartementById(idD);
		}catch (Exception e) {
		l.error(String.format("Erreur dans l'affectaion : %s ",e));
	}
		
	}

	
	

	@Test
	public void testDeleteEmployeById()
	{
		
		try
		{	
			int id = employeS.ajouterEmploye(
					new Employe("Ahmed", "Mrabet", "Ahmed.mrabet@esprit.tn", true, Role.INGENIEUR));
		
		employeS.deleteEmployeById(id);
		
		Employe e = employeS.getEmployerById(id);
		
		assertThat(e).isNull();
		l.info("Employe deleted successfully!");
		}catch (Exception e) {
			l.error(String.format("Erreur dans Delete Employe By Id : %s ",e));
		}
	}
	
}
	
	
	

