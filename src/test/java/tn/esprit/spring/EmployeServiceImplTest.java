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
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;


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


	@Test
	public void ajouterEmployeTest(){
		this.employe = new Employe();
		this.employe.setPrenom("Selim");
		this.employe.setNom("CHIKH ZAOUALI");
		this.employe.setEmail("selim.chikhzaouali@esprit.tn");
		this.employe.setActif(true);
		this.employe.setRole(Role.INGENIEUR);
		int id=employeS.ajouterEmploye(this.employe);
		// le service employeS.ajouterEmploye(employe) retourne un int qui est l'id
		
		Assert.assertTrue(id>0);
		employeS.deleteEmployeById(id);
	}

	@Test
	public void ajouterContratTest() {
		int id=employeS.ajouterEmploye(this.employe);
		this.contrat.setEmploye(this.employe);
		int ref=employeS.ajouterContrat(this.contrat);
		// le service employeS.ajouterContrat(this.contrat) retourne un int qui est la réf

		Assert.assertTrue(contratRepository.findById(ref).get().getReference() > 0);
		employeS.deleteContratById(ref);
		employeS.deleteEmployeById(id);

	}

	@Test
	public void affecterContratAEmployeTest() {
		int id=employeS.ajouterEmploye(this.employe);
		this.contrat.setEmploye(this.employe);
		int ref=employeS.ajouterContrat(this.contrat);
		
		employeS.affecterContratAEmploye(ref, id);
		
		// Comparaison pour vérifier si le contrat a bien été affecté
		Assert.assertEquals(employeRepository.findById(id).get().getContrat().getReference(), contratRepository.findById(ref).get().getReference());
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
	
	
	

	//test employer  Ahmed Mrabet
	
	
	private static final Logger l = LogManager.getLogger(EmployeServiceImplTest.class);
	@Test
	public void testGetEmployePrenomById() {
		try
		{
		String prenomEmp = employeS.getEmployePrenomById(2);
		l.info("Prenom de lemploye est :"+prenomEmp);
		assertThat(prenomEmp).isEqualTo("mohamed");
		}catch (Exception e) {
			l.error("Erreur dans Get EmployePrenom By Id : " +e);
		}
		

	}

	@Test
	public void testAjouterEmploye() {
		try
		{
		int id = employeS.ajouterEmploye(
				new Employe("Ahmed", "Mrabet", "Ahmed.mrabet@esprit.tn", true, Role.INGENIEUR));
	
		assertThat(id).isGreaterThan(0);
		l.info("Employe added successfully!");
		}catch (Exception e) {
			l.error("Erreur dans Ajout d'Employe : " +e);
		}
	}

	@Test
	public void testgetAllEmployes() {
		try
		{	
		List<Employe> employes = employeS.getAllEmployes();

		assertThat(employes).size().isGreaterThan(0);
	}catch (Exception e) {
		l.error("Erreur dans get All Employes : " +e);
	}
	}

	@Test
	public void testMettreAjourEmailByEmployeId() {
		try
		{	
		String email = "bohmid.ahmed@spring.tn";

		employeS.mettreAjourEmailByEmployeId(email, 10);

		Employe e = employeS.getEmployerById(10);

		assertThat(e.getEmail()).isEqualTo(email);
		}catch (Exception e) {
		l.error("Erreur dans Mettre A jour Email By Employe Id : " +e);
	}
	}

	@Test
	public void affecterEmployeADepartement()
	{
		try
		{	
		int id = employeS.ajouterEmploye(
		new Employe("mohammed", "Zied", "Zied.hamma@spring.tn", true, Role.TECHNICIEN));
		
		employeS.affecterEmployeADepartement(id, 2);
		l.info("Employe avec id=" + id + " added successfully to Departement avec id=" + 2);
		}catch (Exception e) {
		l.error("Erreur dans affecter Employe A Departement : " +e);
	}
		
	}

	@Test
	public void desaffecterEmployeDuDepartement()
	{
		try
		{	
		employeS.desaffecterEmployeDuDepartement(13, 2);
		}catch (Exception e) {
		l.error("Erreur dans desaffecter Employe Du Departement : " +e);
	}
	}
	

	@Test
	public void testDeleteEmployeById()
	{
		try
		{	
		employeS.deleteEmployeById(13);
		
		Employe e = employeS.getEmployerById(13);
		
		assertThat(e).isNull();
		l.info("Employe deleted successfully!");
		}catch (Exception e) {
			l.error("Erreur dans Delete Employe By Id : " +e);
		}
	}
	
}
	
	
	

