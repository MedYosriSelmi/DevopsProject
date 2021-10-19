package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;
<<<<<<< HEAD

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEntrepriseService;

=======
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEntrepriseService;

>>>>>>> main
@SpringBootTest
@RunWith(SpringRunner.class)
public class EntrepriseServiceImplTest   {

	private static final Logger L = LogManager.getLogger(EntrepriseServiceImplTest.class);

	@Autowired
	IEntrepriseService entreService;
	
	@Autowired
	EntrepriseRepository entreRep;
<<<<<<< HEAD
	
	@Autowired
	DepartementRepository departementRerpository;
=======
>>>>>>> main

	private Entreprise entreprise;
	private Departement department;
	
<<<<<<< HEAD
	@Before
	public void init(){
		
	}
	@After
	public void clean(){
		
	}
	
=======
>>>>>>> main
	
	@Test
	public void ajouterEntrepriseTest  ()
	{
		this.entreprise = new Entreprise();
		this.entreprise.setName("entrepriseTest");
		this.entreprise.setRaisonSocial("raisonTest");
		int entreId = entreService.ajouterEntreprise(this.entreprise);
		assertThat(entreId).isGreaterThan(0);
		L.info("Entreprise added successfully!");
		entreService.deleteEntrepriseById(entreId);
	}
	
	
	@Test
	public void affecterDepartementAEntrepriseTest ()
	{
		this.entreprise = new Entreprise();
		this.entreprise.setName("entrepriseTest1");
		this.entreprise.setRaisonSocial("raisonTest1");
		
		this.department= new Departement();
		this.department.setName("departmenTest1");
		int entreId=entreService.ajouterEntreprise(this.entreprise);
		int depId=entreService.ajouterDepartement(this.department);
		
		entreService.affecterDepartementAEntreprise(depId, entreId);
	
		L.info("Departement with id=" + depId + " added successfully to Entreprise with id=" + entreId);
		
		entreService.deleteDepartementById(depId);
		entreService.deleteEntrepriseById(entreId);
	   
	}
	
	@Test
	public void deleteEntreprisebyId ()
	{
		this.entreprise = new Entreprise();
		this.entreprise.setName("entrepriseTest");
		this.entreprise.setRaisonSocial("raisonTest");
		int entreId = entreService.ajouterEntreprise(this.entreprise);
		entreService.deleteEntrepriseById(entreId);
		L.info("Entreprise deleted successfully!");
	    Optional<Entreprise> e = entreRep.findById(entreId);
	    Assert.assertFalse(e.isPresent());
		
	}
	
	@Test
	public void getEntreprisebyId ()
	{
		this.entreprise = new Entreprise();
		this.entreprise.setName("entrepriseTest");
		this.entreprise.setRaisonSocial("raisonTest");
		int entreId = entreService.ajouterEntreprise(this.entreprise);
		entreService.getEntrepriseById(entreId);
		assertThat(this.entreprise).isNotNull();
		L.info("Entreprise with id=" + entreId +" geted successfully!");
	}
	
<<<<<<< HEAD
	@Test
	public void ajouterDepartementTest() {
		Departement depTest = new Departement("production");
		int idDepartement=entreService.ajouterDepartement(depTest);
		Assert.assertTrue(departementRerpository.findById(idDepartement).get()!= null);
		Assert.assertTrue(departementRerpository.findById(idDepartement).get().getName().equals("production"));
		L.info("Entreprise added successfully!");
		entreService.deleteDepartementById(idDepartement);
		
		
	}
	
	@Test
	public void suprimerDepartementTest() {
		Departement depTest = new Departement("production");
		int idDepartement=entreService.ajouterDepartement(depTest);
		Assert.assertTrue(departementRerpository.findById(idDepartement).isPresent());
		entreService.deleteDepartementById(idDepartement);
		Assert.assertFalse(departementRerpository.findById(idDepartement).isPresent());
	}
	
	@Test
	public void  getAllDepartementsNamesByEntrepriseTest() {
		this.entreprise = new Entreprise();
		this.entreprise.setName("entreprise To find");
		int entreId=entreService.ajouterEntreprise(this.entreprise);
		Departement department= new Departement();
		department.setName("department Test 1");
		int depId=entreService.ajouterDepartement(department);
		
		Departement department2= new Departement();
		department2.setName("department Test 2");
		int depId2=entreService.ajouterDepartement(department2);
		
		entreService.affecterDepartementAEntreprise(depId, entreId);
		entreService.affecterDepartementAEntreprise(depId2, entreId);
		
		List<String> result = entreService.getAllDepartementsNamesByEntreprise(entreId);
		
		assertThat(result).containsExactly("department Test 1","department Test 2");

		assertThat(result).size().isEqualTo(2);
		entreService.deleteDepartementById(depId);
		entreService.deleteDepartementById(depId2);
		entreService.deleteEntrepriseById(entreId);
		
	}
=======

	
	
	
>>>>>>> main
}