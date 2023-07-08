package com.sip.ams.controllers;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sip.ams.entities.Article;
import com.sip.ams.entities.Provider;
import com.sip.ams.repositories.ArticleRepository;
import com.sip.ams.repositories.ProviderRepository;

@RestController
@CrossOrigin(origins="*")
@RequestMapping({"/articles"})
public class ArticleController {
	
	private final ArticleRepository articleRepository;
	private final ProviderRepository providerRepository;
	private final Path root = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/uploads");
	
	Article article= new Article();
    
	@Autowired
    public ArticleController(ArticleRepository articleRepository, ProviderRepository providerRepository) {
        this.articleRepository = articleRepository;
        this.providerRepository = providerRepository;
    }
    @GetMapping("/list")
    public List<Article> getAllArticles() {
        return (List<Article>) articleRepository.findAll();
    }
    //// code Amine ///// 
    @PostMapping("/add")
	public Article uplaodImage(
			@RequestParam("imageFile") MultipartFile file,
			@RequestParam("imageName") String imageName, 
			@RequestParam("providerId") String sproviderId,
			@RequestParam("label") String label, 
			@RequestParam("price") String sprice)
					
					throws IOException {
    	Long providerId = Long.parseLong(sproviderId);
    	Long price = Long.parseLong(sprice);
    	
    	Provider provider = providerRepository.findById(providerId) .orElseThrow(()-> new
    			  IllegalArgumentException("Invalid provider Id:" + providerId));
    			  this.article.setProvider(provider);
    			  // 
		String newImageName = getSaltString().concat(file.getOriginalFilename());
		try {
			Files.copy(file.getInputStream(), this.root.resolve(newImageName));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}

		this.article.setLabel(label);
		this.article.setPicture(newImageName);
		this.article.setPrice(price);
		articleRepository.save(this.article);
		
		return this.article;
	}
    
 // rundom string to be used to the image name
 	protected static String getSaltString() {
 		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
 		StringBuilder salt = new StringBuilder();
 		Random rnd = new Random();
 		while (salt.length() < 18) { // length of the random string.
 			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
 			salt.append(SALTCHARS.charAt(index));
 		}
 		String saltStr = salt.toString();
 		return saltStr;

 	}
    
    //// fin code Amine  //////
    
 @PutMapping("/update/{providerId}/{articleId}")
    public Article updateArticle(@PathVariable (value = "providerId") Long providerId,
                                 @PathVariable (value = "articleId") Long articleId,
                                 @Valid @RequestBody Article articleRequest) {
        if(!providerRepository.existsById(providerId)) {
            throw new IllegalArgumentException("ProviderId " + providerId + " not found");
        }

        return articleRepository.findById(articleId).map(article -> {
        	 article.setPrice(articleRequest.getPrice());
             article.setLabel(articleRequest.getLabel()); 
             article.setPicture(articleRequest.getPicture()); 
        return articleRepository.save(article);
        }).orElseThrow(() -> new IllegalArgumentException("ArticleId " + articleId + "not found"));
    }
    // ajout 28 05 //
    @GetMapping("/{articleId}") 

    public Article getArticle(@PathVariable (value = "articleId") Long articleId) {

    	Optional<Article> article = articleRepository.findById(articleId);
    
		System.out.println("Cet article :"+article.get().getProvider().getName());
		return article.get();
}
    //
    
    @DeleteMapping("/delete/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable (value = "articleId") Long articleId) {
        return articleRepository.findById(articleId).map(article -> {
            articleRepository.delete(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException("Article not found with id " + articleId));
    }
}