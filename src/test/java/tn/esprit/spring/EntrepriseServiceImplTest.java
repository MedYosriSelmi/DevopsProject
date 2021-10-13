package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;
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

@SpringBootTest
@RunWith(SpringRunner.class)
public class EntrepriseServiceImplTest   {

	private static final Logger L = LogManager.getLogger(EntrepriseServiceImplTest.class);

	@Autowired
	IEntrepriseService entreService;
	
	@Autowired
	EntrepriseRepository entreRep;

	private Entreprise entreprise;
	private Departement department;
	
	
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
	

	
	
	
}