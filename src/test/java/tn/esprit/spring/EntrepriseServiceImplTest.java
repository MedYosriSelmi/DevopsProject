package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEntrepriseService;


@SpringBootTest
@RunWith(SpringRunner.class)
public class EntrepriseServiceImplTest   {

	private static final Logger L = LogManager.getLogger(EntrepriseServiceImplTest.class);

	@Autowired
	IEntrepriseService entreService;
	
	@Autowired
	EntrepriseRepository entreRep;
	
	@Autowired
	DepartementRepository departementRerpository;

	
	
	@Before
	public void init(){
		
	}
	
	@After
	public void clean(){
		
	}
	
	Entreprise entreprise;
	
	@Test
	public void ajouterEntrepriseTest  ()
	{
		Entreprise entrepTest = new Entreprise ("entrepriseTest1","raisonTest1");
		int entreId = entreService.ajouterEntreprise(entrepTest);
		Assert.assertTrue(entreRep.findById(entreId).get()!= null);
		Assert.assertTrue(entreRep.findById(entreId).get().getName().equals("entrepriseTest1"));
		L.info("Entreprise added successfully!");
		entreService.deleteEntrepriseById(entreId);	
	}
	
	
	@Test
	public void affecterDepartementAEntrepriseTest ()
	{
		Entreprise entreprise2 = new Entreprise ("entrepriseTest2","raisonTest2");
		Departement department = new Departement("departmenTest1");
		int entreId=entreService.ajouterEntreprise(entreprise2);
		int depId=entreService.ajouterDepartement(department);
		L.info("Departement added successfully to Entreprise ");
		entreService.affecterDepartementAEntreprise(depId, entreId);
        List<String> result = entreService.getAllDepartementsNamesByEntreprise(entreId);
		assertThat(result).containsExactly("departmenTest1");
		assertThat(result).size().isEqualTo(1);
		entreService.deleteDepartementById(depId);
		entreService.deleteEntrepriseById(entreId);
	   
	}
	
	@Test
	public void deleteEntreprisebyIdTest ()
	{
	    Entreprise entreprise3 = new Entreprise ("entrepriseTest3","raisonTest3");
		int idEntreprise=entreService.ajouterEntreprise(entreprise3);
		Assert.assertTrue(entreRep.findById(idEntreprise).isPresent());
		entreService.deleteEntrepriseById(idEntreprise);
		Assert.assertFalse(entreRep.findById(idEntreprise).isPresent());
	}
	
	@Test
	public void getEntreprisebyIdTest ()
	{
		Entreprise entreprise4 = new Entreprise ("entrepriseTest4","raisonTest4");
		int entreId = entreService.ajouterEntreprise(entreprise4);
		entreService.getEntrepriseById(entreId);
		L.info("Entreprise  geted successfully!");
		assertThat(entreprise).isNull();
		entreService.deleteEntrepriseById(entreId);
	}
	
	@Test
	public void ajouterDepartementTest() {
		Departement depTest = new Departement("production");
		int idDepartement=entreService.ajouterDepartement(depTest);
		Assert.assertTrue(departementRerpository.findById(idDepartement).get()!= null);
		Assert.assertTrue(departementRerpository.findById(idDepartement).get().getName().equals("production"));
		L.info("Departement added successfully!");
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
}