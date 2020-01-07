package com.github.nut077.docker;

import com.github.nut077.docker.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DockerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DockerApplication.class, args);
	}

	@Autowired private SchoolRepository schoolRepository;


	@Override
	@Transactional
	public void run(String... args) throws Exception {

		/*List<Student> list = new ArrayList<>();
		IntStream.rangeClosed(1, 100000).forEach(value -> {
			Student dto = new Student();
			dto.setAge(value);
			dto.setFirstName("name" + value);
			dto.setLastName("lastname" + value);
			list.add(dto);
		});
		School school = new School();
		school.setAddress("eiei");
		school.setName("haha");
		school.setStudents(list);
		schoolRepository.save(school);*/
	}
}
