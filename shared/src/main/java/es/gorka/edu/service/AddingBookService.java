package es.gorka.edu.service;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gorka.edu.dto.BookDTO;
import es.gorka.edu.model.Author;
import es.gorka.edu.model.Book;
import es.gorka.edu.repository.BookRepository;

@Service
public class AddingBookService {

	private static final Logger logger = LogManager.getLogger(AddingBookService.class.getName());
	
	@Autowired
	BookRepository bRepo;
	
	public boolean insert(BookDTO bookDTO) {
		bRepo.insert(bookDTO);
		logger.debug("simulando insercion de un Libro");
		return true;
	}

	public List<Book> listAllBooks(Book book) {
		return bRepo.findBooks(book);
	}

}