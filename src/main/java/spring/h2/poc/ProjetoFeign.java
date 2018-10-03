package spring.h2.poc;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import spring.h2.poc.model.Projeto;

@FeignClient(url = "http://localhost:8080/", name="projeto")
public interface ProjetoFeign {
	
	@GetMapping("/consultar")
	List<Projeto> consultar();
}

